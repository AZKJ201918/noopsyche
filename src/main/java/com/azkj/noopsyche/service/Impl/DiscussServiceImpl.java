package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.OrdersMapper;
import com.azkj.noopsyche.dao.WxUserMapper;
import com.azkj.noopsyche.entity.Discuss;
import com.azkj.noopsyche.entity.WxUser;
import com.azkj.noopsyche.service.DiscussService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class DiscussServiceImpl implements DiscussService {

    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private OrdersMapper ordersMapper;
    @Override
    public void addDiscuss(List<Discuss> discussList, String token) {
        WxUser wxUser=wxUserMapper.selectNicknameAndImgByToken(token);
        String orderId=null;
        for (Discuss discuss:discussList){
             discuss.setWximg(wxUser.getHeadimgurl());
             discuss.setWxname(wxUser.getNickname());
             orderId=discuss.getOrderId();
             discuss.setCreatetime(new Date());
             mongoTemplate.save(discuss);
        }
        ordersMapper.updateStatus(orderId);//把订单改为已评价状态
    }

    @Override
    public HashMap<String, Object> findDiscuss(Integer id, Integer page, Integer evaluate) throws NoopsycheException {
        Query query = new Query();
        query.addCriteria(Criteria.where("cid").is(id));
        if (evaluate!=null){
            query.addCriteria(Criteria.where("evaluate").is(evaluate));
        }
        long count = mongoTemplate.count(query, Discuss.class);
        query.skip((page-1)*10).limit(10);
        List<Discuss> discusses = mongoTemplate.find(query, Discuss.class);
        if (discusses==null||discusses.size()==0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"暂无评论信息");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("discusses",discusses);
        map.put("count",count);
        return map;
    }

    @Override
    public void plusDiscuss(List<Discuss> discussList) throws NoopsycheException {
        for (Discuss discuss:discussList){
            Query query = new Query();
            query.addCriteria(Criteria.where("cid").is(discuss.getCid()));
            query.addCriteria(Criteria.where("token").is(discuss.getToken()));
            query.addCriteria(Criteria.where("orderId").is(discuss.getOrderId()));
            Update update = Update.update("plusDetails", discuss.getPlusDetails());
            UpdateResult upsert = mongoTemplate.updateFirst(query,update,Discuss.class);
            if (upsert.getMatchedCount()<=0){
                throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"追评失败");
            }
        }
    }
}
