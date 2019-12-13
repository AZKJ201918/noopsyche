package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.DateUtil;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.dao.OrderCommodityMapper;
import com.azkj.noopsyche.dao.OrdersMapper;
import com.azkj.noopsyche.dao.ShopCarMapper;
import com.azkj.noopsyche.entity.OrderCommodity;
import com.azkj.noopsyche.entity.Orders;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ShopCarMapper shopCarMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderCommodityMapper orderCommodityMapper;
    @Override
    public void addOrders(Map<String, Object> dataMap) throws NoopsycheException, ParseException {
        String token= (String) dataMap.get("token");
        String skuId= (String) dataMap.get("skuId");
        List<String> skuIds= (List<String>) dataMap.get("skuIds");
        Integer addressId= (Integer) dataMap.get("addressId");
        Integer number= (Integer) dataMap.get("number");
        String remark= (String) dataMap.get("remark");
        if (skuId!=null&&number!=null){//单个商品生成订单
            Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuId);
            Sku  sku = shopCarMapper.selectSkuWithPriceBySkuid(skuId);
            if (repertory==null){
                repertory=sku.getRepertory()==null?0:sku.getRepertory();
            }
            if (repertory==0){//库存为0，下架商品
                shopCarMapper.updateCommodityStatus(sku.getSpuid());
                throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"skuid为"+skuId+"商品库存为0，无法购买");
            }
            if (repertory-number<0){//库存小于购买数量
                throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"skuid为"+skuId+"的商品库存不够，您最多可以购买的数量"+repertory);
            }
            BigDecimal skuprice = sku.getSkuprice();
            BigDecimal number1 = BigDecimal.valueOf(Long.valueOf(number));
            BigDecimal totalPrice= skuprice.multiply(number1);
            Orders orders = new Orders();
            String orderId = DateUtil.getOrderIdByTime();
            orders.setAddressid(addressId);
            orders.setCreatetime(new Date());
            orders.setToken(token);
            orders.setFinalprice(totalPrice);
            orders.setOrderid(orderId);
            orders.setOuttime(DateUtil.plusDay2(1));
            orders.setRemark(remark);
            ordersMapper.insertSelective(orders);
            OrderCommodity orderCommodity = new OrderCommodity();
            orderCommodity.setOrderid(orderId);
            orderCommodity.setSkuid(Integer.parseInt(skuId));
            orderCommodity.setNum(number);
            orderCommodityMapper.insertSelective(orderCommodity);
        }
        if (skuIds!=null){//购物车生成订单
           String orderId = DateUtil.getOrderIdByTime();
           BigDecimal totalPrice=new BigDecimal("0");
           for (String skuid:skuIds){
               Integer carNum = (Integer) redisUtil.getHashObjectLong("shopCar:" + token, skuid);
               Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuid);
               if (carNum==null){
                   throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"skuid为"+skuid+"的商品购物车不存在");
               }
               Sku sku = shopCarMapper.selectSkuWithPriceBySkuid(skuId);
               if (repertory==null){
                   repertory=sku.getRepertory()==null?0:sku.getRepertory();
               }
               if (repertory==0){//库存为0，下架商品
                   shopCarMapper.updateCommodityStatus(sku.getSpuid());
                   throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"skuid为"+skuid+"商品库存为0，无法购买");
               }
               if (repertory-carNum<0){//库存小于购买数量
                   throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"skuid为"+skuid+"库存不够，您最多可以购买的数量"+repertory);
               }
               OrderCommodity orderCommodity = new OrderCommodity();
               orderCommodity.setOrderid(orderId);
               orderCommodity.setSkuid(Integer.parseInt(skuId));
               orderCommodity.setNum(number);
               orderCommodityMapper.insertSelective(orderCommodity);
               BigDecimal skuprice = sku.getSkuprice();
               totalPrice.add(skuprice.multiply(BigDecimal.valueOf(Long.valueOf(carNum))));
           }
           //BigDecimal number1 = BigDecimal.valueOf(Long.valueOf(number));
           Orders orders = new Orders();
           orders.setAddressid(addressId);
           orders.setCreatetime(new Date());
           orders.setToken(token);
           orders.setFinalprice(totalPrice);
           orders.setOrderid(orderId);
           orders.setOuttime(DateUtil.plusDay2(1));
           orders.setRemark(remark);
           ordersMapper.insertSelective(orders);
        }
    }

    @Override
    public PageInfo<Orders> findAllOrder(Integer page, Integer limit, Orders orders) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Orders> ordersList=ordersMapper.selectOrderByToken(orders);
        if (ordersList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"未找到订单相关的信息");
        }
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        return pageInfo;
    }

    @Override
    public void modifyOrder(String orderId) {
        ordersMapper.updateOrder(orderId);
    }

    @Override
    public void removeOrder(Integer id) {
        ordersMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyOrderToSign(Integer id) {
        ordersMapper.updateOrderToSign(id);
    }
}
