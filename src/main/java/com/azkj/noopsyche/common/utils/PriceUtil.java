package com.azkj.noopsyche.common.utils;

import com.azkj.noopsyche.dao.CouponMapper;
import com.azkj.noopsyche.entity.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

public class PriceUtil {

    public static BigDecimal countPrice(BigDecimal totalPrice, Coupon coupon){
        BigDecimal finalPrice = new BigDecimal("0.00");
        if ((coupon!=null&&coupon.getUse()==1)&&(coupon.getOuttime()==null||coupon.getOuttime().getTime()>new Date().getTime())){
            String type = coupon.getType().trim();
            if (type.equals("subtract")){
                finalPrice=totalPrice.subtract(coupon.getSubtract());
            }
            if (type.equals("discount")){
                finalPrice=totalPrice.multiply(coupon.getDiscount());
            }
            if (type.equals("fullsubtract")){
                if (totalPrice.compareTo(coupon.getFulls())>=0){//总价大于满减金额
                    finalPrice=totalPrice.subtract(coupon.getFullsubtract());
                }else {
                    finalPrice=totalPrice;
                }
            }
            if (type.equals("fulldiscount")){
                if (totalPrice.compareTo(coupon.getFulld())>=0){//总价大于满折金额
                    finalPrice=totalPrice.multiply(coupon.getFulldiscount());
                }else {
                    finalPrice=totalPrice;
                }
            }
        }else {
            finalPrice=totalPrice;
        }
        //finalPrice=totalPrice;
        return finalPrice;
    }
}
