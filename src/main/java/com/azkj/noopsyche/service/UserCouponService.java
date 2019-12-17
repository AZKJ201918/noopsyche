package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.UserCoupon;

import java.util.List;

public interface UserCouponService {
    List<UserCoupon> findAllCoupon(String token) throws NoopsycheException;
}
