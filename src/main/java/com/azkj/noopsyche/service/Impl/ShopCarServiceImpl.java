package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.dao.CommodityMapper;
import com.azkj.noopsyche.dao.ShopCarMapper;
import com.azkj.noopsyche.dao.SkuMapper;
import com.azkj.noopsyche.entity.Commodity;
import com.azkj.noopsyche.entity.ShopCar;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.ShopCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ShopCarServiceImpl implements ShopCarService{
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ShopCarMapper shopCarMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private CommodityMapper commodityMapper;
    @Override
    public void addShopCar(String token,String skuid,Integer num) throws NoopsycheException {
        Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuid);
        Sku sku=null;
        if (repertory==null){
            sku=shopCarMapper.selectSkuBySkuid(skuid);
            if (sku==null){
                throw new NoopsycheException("没有该商品");
            }
            repertory=sku.getRepertory();
            if (repertory==null){
               repertory=0;
            }
        }
        if (repertory==0){//库存为0，下架商品
            if (sku==null){
                sku=shopCarMapper.selectSkuBySkuid(skuid);
            }
            shopCarMapper.updateCommodityStatus(sku.getSpuid());
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"商品库存为0，无法加入购物车");
        }
        if (repertory-num<0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"商品库存不够，您最多可以加入购物车的数量是"+repertory);
        }
        Integer carNum= (Integer) redisUtil.getHashObjectLong("shopCar:" + token,skuid);
        if (carNum==null){
            redisUtil.setHashObjectLong("shopCar:"+token,skuid,num);
        }else {
            num+=carNum;
            redisUtil.setHashObjectLong("shopCar:"+token,skuid,num);
        }
    }

    @Override
    public void modifyShopCar(String token, String skuid, Integer num) throws NoopsycheException {
        Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuid);
        Sku sku = null;
        if (repertory == null) {
            sku = shopCarMapper.selectSkuBySkuid(skuid);
            if (sku==null){
                throw new NoopsycheException("没有该商品");
            }
            repertory = sku.getRepertory();
            if (repertory == null) {
                repertory = 0;
            }
        }
        if (repertory == 0) {
            if (sku==null){
                sku = shopCarMapper.selectSkuBySkuid(skuid);
            }
            shopCarMapper.updateCommodityStatus(sku.getSpuid()); //下架商品
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"商品库存为0，无法加入购物车");
        }
        if (repertory - num < 0) {
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST, "库存不足，您最多添加购物车的数量是" + repertory);
        }
        redisUtil.setHashObjectLong("shopCar:" + token, skuid, num);
    }

    @Override
    public List<Sku> findShopCar(String token) throws NoopsycheException {
        Map<String,Object> map = (Map<String, Object>) redisUtil.getHashEntriesLong("shopCar:" + token);
        if (map==null||map.size()==0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"您暂时没有购物车信息");
        }
        Set<String> set = map.keySet();
        if (set==null||set.size()==0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"您暂时没有购物车信息");
        }
        ArrayList<Sku> skuList = new ArrayList<>();
        for (String id:set){
            Sku sku = skuMapper.selectByPrimaryKey(Integer.parseInt(id));
            Commodity commodity = commodityMapper.selectByPrimaryKey(sku.getSpuid());
            Integer carNum = (Integer) map.get(id);
            sku.setCarNum(carNum);
            sku.setCommodity(commodity);
            skuList.add(sku);
        }
        return skuList;
    }

    @Override
    public void removeShopCar(String token, String id) {
        redisUtil.deleteHashObjectLong("shopCar:"+token,id);
    }
}
