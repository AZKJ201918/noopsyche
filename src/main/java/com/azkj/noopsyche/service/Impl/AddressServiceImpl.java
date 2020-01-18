package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.AddressMapper;
import com.azkj.noopsyche.entity.Address;
import com.azkj.noopsyche.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void addAddress(Address address) {
          Address myAddress=addressMapper.selectStatusByToken(address.getToken());
          if (myAddress==null){
              address.setStatus(0);
          }else {
              address.setStatus(1);
          }
          addressMapper.insertSelective(address);
    }

    @Override
    public void modifyAddress(Address address) {
        if (address!=null&&address.getStatus()==0){
           addressMapper.updateToFeiMoren(address.getToken());
        }
        addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public PageInfo<Address> findAddress(Integer page, Integer limit, String token) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Address> addressList=addressMapper.selectByToken(token);
        if (addressList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"地址信息不存在");
        }
        PageInfo<Address> pageInfo = new PageInfo<>(addressList);
        return pageInfo;
    }

    @Override
    public void removeAddress(Integer id) {
        addressMapper.deleteByPrimaryKey(id);
    }
}
