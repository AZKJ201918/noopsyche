package com.azkj.noopsyche.common.pay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.utils.MD5;
import com.azkj.noopsyche.common.utils.sm.HttpUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class PayUtil {

    public Map<String, String> pay(String openid, String notifyurl, Long price,String terminal_trace) throws Exception {
        Date date = new Date();
        //时间戳
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String sdfdate = simpleDateFormat.format(date);
        Integer zprice= Math.toIntExact(price);
        String string = "pay_ver=100&pay_type=010&service_id=015&merchant_no="+Constants.WEIXING_STATUS_MCH_ID+"&terminal_id="+Constants.WEIXING_STATUS_TERMINAL+"&terminal_trace=" + terminal_trace +"&terminal_time=" + sdfdate + "&total_fee=" + zprice +"&access_token="+ Constants.token;
        Map<String,Object> map=new HashMap();
        map.put("pay_ver","100");
        map.put("pay_type","010");
        map.put("service_id","015");
        map.put("merchant_no",Constants.WEIXING_STATUS_MCH_ID);
        map.put("terminal_id",Constants.WEIXING_STATUS_TERMINAL);
        map.put("terminal_trace",terminal_trace);
        map.put("terminal_time",sdfdate);
        map.put("total_fee",zprice);
        map.put("open_id",openid);
        map.put("sub_appid",Constants.WEXING_STATUS_APPID);
        map.put("notify_url",notifyurl);
        map.put("key_sign",MD5.sign(string,"UTF-8"));
        String Param = new Gson().toJson(map);
        String xmlText= HttpUtil.tojson("https://pay.lcsw.cn/lcsw/pay/100/minipay",Param);
        JSONObject jsonObject= (JSONObject) JSON.parse(xmlText);
        Map<String,String> stringMap=new HashMap<>();
        stringMap.put("timeStamp",jsonObject.getString("timeStamp"));
        stringMap.put("nonceStr",jsonObject.getString("nonceStr"));
        stringMap.put("package",jsonObject.getString("package_str"));
        stringMap.put("signType",jsonObject.getString("signType"));
        stringMap.put("paySign",jsonObject.getString("paySign"));
        return stringMap;
    }




}
