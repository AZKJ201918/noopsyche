package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.DateUtil;
import com.azkj.noopsyche.dao.*;
import com.azkj.noopsyche.entity.*;
import com.azkj.noopsyche.service.PuzzleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
@Service
public class PuzzleServiceImpl implements PuzzleService {
    @Autowired
    private AssembleMapper assembleMapper;
    @Autowired
    private GroupsMapper groupsMapper;
    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private WxUserMapper wxUserMapper;

    @Override
    public void addPuzzle(String token, Integer assembleId) throws NoopsycheException, ParseException {
         Assemble assemble=assembleMapper.selectByPrimaryKey(assembleId);
         if (assemble==null){
             throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"此商品不参与拼团");
         }
        Groups groups=new Groups();
        groups.setStarttime(new Date());
        groups.setEndtime(DateUtil.plusDay2(1));
        groups.setOuttime(DateUtil.plusHour(36));
        groups.setSpuid(assemble.getSpuid());
        groups.setAid(assemble.getId());
        groupsMapper.insertSelective(groups);
        People people=new People();
        people.setGid(groups.getId());
        people.setToken(token);
        people.setStatus(0);
        peopleMapper.insertSelective(people);
    }

    @Override
    public void JoinGroup(Integer gid, String token) throws NoopsycheException {
        int j=groupsMapper.selectSizeByGid(gid);//拼团需要的人数
        int i=peopleMapper.selectCount(gid);//参加拼团的人数
        if(i>=j){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"拼团人数已经满");
        }
        People people=peopleMapper.selectPeople(gid,token);
        if (people!=null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"你已经在这个拼团里面");
        }
        people=new People();
        people.setToken(token);
        people.setGid(gid);
        peopleMapper.insertSelective(people);
        int i1=peopleMapper.selectCount(gid);//参加拼团的人数
        if(i1==j){
            groupsMapper.updateGtypeByGid(gid);
        }
    }

    @Override
    public PageInfo<People> findGroup(String token, Integer page, Integer limit, Integer status) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<People> peopleList=peopleMapper.selectByToken(token);
        if (peopleList==null||peopleList.size()==0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"暂无您的拼团信息");
        }
        for (People people:peopleList){
            Groups groups = groupsMapper.selectByPrimaryKey(people.getGid());
            people.setGroups(groups);
            Commodity commodity = commodityMapper.selectByPrimaryKey(groups.getSpuid());
            people.setCommodity(commodity);
            List<People> myPeopleList=peopleMapper.selectPeopleByGid(people.getGid());
            for (People myPeople:myPeopleList){
                WxUser wxUser = wxUserMapper.selectNicknameAndImgByToken(myPeople.getToken());
                myPeople.setWxUser(wxUser);
            }
            people.setMyPeopleList(myPeopleList);
        }
        PageInfo<People> pageInfo = new PageInfo<>(peopleList);
        return pageInfo;
    }

    @Override
    public PageInfo<Groups> findSpuGroup(Integer spuid, Integer page, Integer limit) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<Groups> groups=groupsMapper.selectBySpuid(spuid);
        if (groups==null||groups.size()==0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"暂无拼团信息");
        }
        for (Groups group:groups){
            List<People> peopleList = peopleMapper.selectPeopleByGid(group.getId());
            Assemble assemble = assembleMapper.selectByPrimaryKey(group.getAid());
            for (People people:peopleList){
                WxUser wxUser = wxUserMapper.selectNicknameAndImgByToken(people.getToken());
                people.setWxUser(wxUser);
            }
            group.setPeopleList(peopleList);
            group.setNum(assemble.getSize()-peopleList.size());
        }
        PageInfo<Groups> pageInfo = new PageInfo<>(groups);
        return pageInfo;
    }

    @Override
    public Groups findPuzzleByGid(Integer gid) {
        Groups groups = groupsMapper.selectByPrimaryKey(gid);
        Assemble assemble= assembleMapper.selectByPrimaryKey(groups.getAid());
        List<People> peopleList = peopleMapper.selectPeopleByGid(gid);
        for (People people:peopleList){
            WxUser wxUser = wxUserMapper.selectNicknameAndImgByToken(people.getToken());
            people.setWxUser(wxUser);
        }
        groups.setNum(assemble.getSize()-peopleList.size());
        return groups;
    }

    @Override
    public List<Assemble> findAssembleBySpuid(Integer spuid) throws NoopsycheException {
        List<Assemble> assembleList = assembleMapper.selectAssembleBySpuid(spuid);
        if (assembleList==null||assembleList.size()==0){
            throw new NoopsycheException("没有该商品拼团信息");
        }
        return assembleList;
    }
}
