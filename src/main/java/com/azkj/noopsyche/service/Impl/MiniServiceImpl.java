package com.azkj.noopsyche.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.pay.PayUtil;
import com.azkj.noopsyche.common.utils.DateUtil;
import com.azkj.noopsyche.dao.MiniMapper;
import com.azkj.noopsyche.dao.MiniOrderMapper;
import com.azkj.noopsyche.dao.WxUserMapper;
import com.azkj.noopsyche.entity.Mini;
import com.azkj.noopsyche.entity.MiniOrder;
import com.azkj.noopsyche.entity.WxUser;
import com.azkj.noopsyche.service.MiniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("miniServiceImpl")
public class MiniServiceImpl implements MiniService {

    @Autowired
    private MiniMapper miniMapper;

    @Autowired
    private PayUtil payUtil;

    @Autowired
    private MiniOrderMapper miniOrderMapper;


    @Autowired
    private WxUserMapper wxUserMapper;



    @Override
    public List<Mini> SelectMini() throws NoopsycheException {
        List<Mini> miniList=miniMapper.SelectMiniAll();
        if(CollectionUtils.isEmpty(miniList)){
            throw  new NoopsycheException(300,"没有小程序");
        }
        return miniList;
    }

    @Override
    public Mini Minidetails(Integer id) {
        return miniMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, String> purchaseMini(MiniOrder miniOrder) throws Exception {
        Mini mini=this.Minidetails(miniOrder.getMid());
        WxUser wxUser=wxUserMapper.selectByPrimaryKey(miniOrder.getToken());
        miniOrder.setPrice(mini.getPrice());
        miniOrder.setPreferential(mini.getPrice());
        String terminal_trace= DateUtil.getOrderIdByTime();
        miniOrder.setOrderid(terminal_trace);
        miniOrder.setCreatetime(new Date());
        miniOrder.setEndtime(DateUtil.plusDay2(1));
        miniOrderMapper.insertSelective(miniOrder);
        Map<String,String> map= payUtil.pay(wxUser.getOpenid(),"",mini.getPrice(),terminal_trace);
        return map;
    }

    @Override
    public void mininotifyurl(String notityXml) throws Exception {
        if(notityXml==null) {
          throw new Exception();
        }
        JSONObject jsonObject=JSON.parseObject(notityXml);
        if (!jsonObject.isEmpty()){
            String orderId = jsonObject.getString("terminal_trace");
            if(orderId!=null){
               MiniOrder miniOrder= miniOrderMapper.SelectOrderId(orderId);
               miniOrder.setStatus(2);
               miniOrderMapper.updateByPrimaryKeySelective(miniOrder);
            }
        }else{
            throw  new Exception();
        }

    }
}
