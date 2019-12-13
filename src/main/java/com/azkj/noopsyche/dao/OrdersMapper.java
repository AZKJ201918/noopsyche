package com.azkj.noopsyche.dao;

import com.azkj.noopsyche.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    List<Orders> selectOrderByToken(Orders orders);
    @Update("update orders set status=0 where orderid=#{orderId}")
    int updateOrder(String orderId);
    @Update("update orders set status=4 where id=#{id}")
    int updateOrderToSign(Integer id);
}