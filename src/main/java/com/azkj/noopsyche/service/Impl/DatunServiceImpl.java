package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.QiniuFileUploadUtil;
import com.azkj.noopsyche.dao.DatumMapper;
import com.azkj.noopsyche.entity.Datum;
import com.azkj.noopsyche.service.DatunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service("datunServiceImpl")
public class DatunServiceImpl implements DatunService{

   private final static Integer CHECK_STATE_REVIVE=1;//审核中

    private final static Integer CHECK_STATE_NOPASS=2;//审核不通过

    private final static Integer CHECK_STATE_TRANSIT =3;//审核通过



    @Autowired
    private DatumMapper datumMapper;

    @Override
    public void InstrDatun(Datum datum) throws NoopsycheException {
        datum.setCreatetime(new Date());
        datum.setStatus(CHECK_STATE_REVIVE);
        datumMapper.insertSelective(datum);
    }

    @Override
    public List<Datum> SelectDatun(String token) {
        List<Datum> datumList=datumMapper.SelectDatun(token);
        return datumList;
    }

    @Override
    public String Uploading(MultipartFile file) throws Exception {
        return QiniuFileUploadUtil.uploadHeadImg(file);
    }
}
