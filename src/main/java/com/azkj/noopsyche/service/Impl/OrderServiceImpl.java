package com.azkj.noopsyche.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.jms.SmsProcessor;
import com.azkj.noopsyche.common.pay.PayUtil;
import com.azkj.noopsyche.common.utils.DateUtil;
import com.azkj.noopsyche.common.utils.PriceUtil;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.dao.*;
import com.azkj.noopsyche.entity.Coupon;
import com.azkj.noopsyche.entity.OrderCommodity;
import com.azkj.noopsyche.entity.Orders;
import com.azkj.noopsyche.entity.Sku;
import com.azkj.noopsyche.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Destination;
import java.math.BigDecimal;
import java.util.*;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ShopCarMapper shopCarMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderCommodityMapper orderCommodityMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private UserCouponMapper userCouponMapper;
    @Autowired
    private SmsProcessor smsProcessor;
    @Autowired
    private PayUtil payUtil;
    @Autowired
    private WxUserMapper wxUserMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> addOrders(Map<String, Object> dataMap) throws Exception {
        String token= (String) dataMap.get("token");
        Integer skuId= (Integer) dataMap.get("skuId");
        List<Integer> skuIds= (List<Integer>) dataMap.get("skuIds");
        Integer addressId= (Integer) dataMap.get("addressId");
        Integer number= (Integer) dataMap.get("number");
        Integer couponId = (Integer) dataMap.get("couponId");
        Integer userCouponId = (Integer) dataMap.get("userCouponId");//记得把用户的优惠劵主键传过来
        String remark= (String) dataMap.get("remark");
        Integer gid = (Integer) dataMap.get("gid");//拼团的团号
        String orderId = DateUtil.getOrderIdByTime();
        Orders orders = new Orders();
        orders.setAddressid(addressId);
        orders.setCreatetime(new Date());
        orders.setStatus(1);
        orders.setCouponid(couponId);
        orders.setToken(token);
        orders.setOuttime(DateUtil.plusDay2(1));
        orders.setRemark(remark);
        Map<String, String> stringMap = new HashMap<>();
        String s=null;
        BigDecimal finalPrice=null;
        Destination destination = new ActiveMQQueue("aaa");//通过消息队列扣除库存
        if (skuId!=null&&number!=null){//单个商品生成订单
            Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuId);
            Sku  sku = shopCarMapper.selectSkuWithPriceBySkuid(String.valueOf(skuId));
            if (repertory==null){
                repertory=sku.getRepertory()==null?0:sku.getRepertory();
            }
            if (sku==null){
                throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有商品相关信息");
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
            Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
            finalPrice = PriceUtil.countPrice(totalPrice, coupon);
            if (gid!=null){

            }
            orders.setPrice(totalPrice);
            orders.setFinalprice(finalPrice);
            OrderCommodity orderCommodity = new OrderCommodity();
            orderCommodity.setOrderid(orderId);
            orderCommodity.setSkuid(skuId);
            orderCommodity.setNum(number);
            orderCommodityMapper.insertSelective(orderCommodity);
            s="[{\"skuId\":"+skuId+",\"num\":"+number+"}]";
        }
        if (skuIds!=null){//购物车生成订单
           BigDecimal totalPrice=new BigDecimal("0");
           BigDecimal skuprice=null;
           for (Integer skuid:skuIds){
               Integer carNum = (Integer) redisUtil.getHashObjectLong("shopCar:" + token, String.valueOf(skuid));
               Integer repertory = (Integer) redisUtil.getObject("repertory:" + skuid);
               if (carNum==null){
                   throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"skuid为"+skuid+"的商品购物车不存在");
               }
               Sku sku = shopCarMapper.selectSkuWithPriceBySkuid(String.valueOf(skuid));
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
               orderCommodity.setSkuid(skuid);
               orderCommodity.setNum(carNum);
               orderCommodityMapper.insertSelective(orderCommodity);
               skuprice= sku.getSkuprice();
               BigDecimal multiply = skuprice.multiply(BigDecimal.valueOf(Long.valueOf(carNum)));
               totalPrice=totalPrice.add(multiply);
           }
           Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
           finalPrice = PriceUtil.countPrice(totalPrice, coupon);
           orders.setPrice(totalPrice);
           orders.setFinalprice(finalPrice);
           HashMap<String,Object> map =null;
           ArrayList<Object> list = new ArrayList<>();
           for (Integer skuid:skuIds){
              map= new HashMap<>();
              Integer carNum = (Integer) redisUtil.getHashObjectLong("shopCar:" + token, String.valueOf(skuid));
              if (carNum==null){
                  continue;
              }
              map.put("skuId",skuid);
              map.put("num",carNum);
              list.add(map);
           }
           s = JSONArray.toJSONString(list);
        }
        ordersMapper.insertSelective(orders);
        if (userCouponId!=null){
            userCouponMapper.updateStatus(userCouponId);//删除用户优惠劵信息
        }
        smsProcessor.sendSmsToQueue(destination,s);
        String openid=ordersMapper.selectOpenidByToken(token);
        BigDecimal v = finalPrice.multiply(new BigDecimal(100));
        stringMap=payUtil.pay(openid,"",1000L,orderId);
        return stringMap;
    }

    @Override
    public PageInfo<Orders> findAllOrder(Integer page, Integer limit,String token,Integer status) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Orders> ordersList=ordersMapper.selectOrderByToken(token,status);
        if (ordersList==null||ordersList.size()==0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"未找到订单相关的信息");
        }
        PageInfo<Orders> pageInfo = new PageInfo<>(ordersList);
        return pageInfo;
    }

    @Override
    public void modifyOrder(String orderId) {
        //把库存加上去
        List<OrderCommodity> orderCommodityList = orderCommodityMapper.selectByOrderId(orderId);
        for (OrderCommodity orderCommodity:orderCommodityList){
            Integer repertory = (Integer) redisUtil.getObject("repertory:" + orderCommodity.getSkuid());
        }
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

    @Override
    public Orders findOneOrderDetail(String orderId, Integer id) throws NoopsycheException {
        Orders orders = ordersMapper.selectByPrimaryKey(id);
        if (orders==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有订单信息");
        }
        Coupon coupon = couponMapper.selectByPrimaryKey(orders.getCouponid());
        orders.setCoupon(coupon);
        List<OrderCommodity> orderCommodityList=orderCommodityMapper.selectByOrderId(orderId);
        List<Sku> skuList = new ArrayList<>();
        for (OrderCommodity orderCommodity:orderCommodityList){
            Sku sku = skuMapper.selectByPrimaryKey(orderCommodity.getSkuid());
            sku.setNum(orderCommodity.getNum());
            skuList.add(sku);
        }
        orders.setSkuList(skuList);
        return orders;
    }
    @Override
    public void notifyurl(String notityXml) throws Exception {
        if(notityXml==null) {
            throw new Exception();
        }
        JSONObject jsonObject= JSON.parseObject(notityXml);
        if (!jsonObject.isEmpty()){
            String orderId = jsonObject.getString("terminal_trace");
            if(orderId!=null){
                ordersMapper.updateByOrderId(orderId);
                Orders orders=ordersMapper.selectTokenByOrderId(orderId);
                wxUserMapper.updateConsumptionByToken(orders.getToken() ,orders.getFinalprice());
            }
        }else{
            throw  new Exception();
        }

    }
}
