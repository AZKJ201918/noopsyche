package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.CouponMapper;
import com.azkj.noopsyche.dao.UserCouponMapper;
import com.azkj.noopsyche.entity.Coupon;
import com.azkj.noopsyche.entity.UserCoupon;
import com.azkj.noopsyche.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCouponServiceImpl implements UserCouponService {
    @Autowired
    private UserCouponMapper userCouponMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Override
    public List<UserCoupon> findAllCoupon(String token) throws NoopsycheException {
        List<UserCoupon> userCoupons = userCouponMapper.selectByToken(token);
        if (userCoupons==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有用户优惠券信息");
        }
        for (UserCoupon userCoupon:userCoupons){
            Coupon coupon = couponMapper.selectByPrimaryKey(userCoupon.getCouponid());
            userCoupon.setCoupon(coupon);
        }
        return userCoupons;
    }
}
