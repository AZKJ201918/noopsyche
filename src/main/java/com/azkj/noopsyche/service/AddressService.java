package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Address;
import com.github.pagehelper.PageInfo;

public interface AddressService {
    void addAddress(Address address);

    void modifyAddress(Address address);

    PageInfo<Address> findAddress(Integer page, Integer limit, String token) throws NoopsycheException;

    void removeAddress(Integer id);
}
