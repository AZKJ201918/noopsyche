package com.azkj.noopsyche.common.constants;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Constants {

    public static final int RESP_STATUS_OK = 200;

    public static final int RESP_STATUS_NOAUTH = 401;

    public static final int RESP_STATUS_INTERNAL_ERROR = 500;

    public static final int RESP_STATUS_BADREQUEST = 400;


    //微信公众号APPID
    public static final String WEXING_STATUS_APPID="wxb1724deaa7673ef5";

    //微信公众号SECRET
    public static  final  String WEIXING_STATUS_APPSECRET="aa1351547837cc1907b8117ef02e985a";

   //微信商户号
    public static final String MCH_ID = "1511800301";

    //微信商户号
    public static final String SUB_MCH_ID = "1546859831";

   //微信商户支付api密钥
    public static final String KEY = "AZWST2019azhenkejiwangluoAZ2018z";
    //公众号支付
    public static final String TRADE_TYPE_JSAPI = "JSAPI";

    public static final String TRADE_TYPE_NATIVE = "NATIVE";
    //web
    public static final String WEB = "WEB";
    //成功返回的字符串
    public static final String RETURN_SUCCESS = "SUCCESS";
    //异步回调
    public static final String NOTIFY_URL = "http://wxpay.chazhanyuan.cn/";
    //微信统一下单URL
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String TRANSFERS_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers"; // 企业付款



    /***七牛keys start****/
    public static final String QINIU_ACCESS_KEY="GLt1Gr-awMVuVmnSntaXKplLEhIIe6BnzCTtyXzF";

    public static final String QINIU_SECRET_KEY="eIsMWbGo3VxfG5wtlfmcnj-f8xEQ3ZI53bVvfcFc";

    public static final String QINIU_HEAD_IMG_BUCKET_NAME="whazkj";

    public static final String QINIU_HEAD_IMG_BUCKET_URL="http://njl.azwst.co/";

    /***七牛keys end****/


    /***秒滴keys end****/
    public static final String BASE_URL = "https://openapi.miaodiyun.com/distributor/sendSMS";

    public static final String ACCOUNT_SID = "e781734be145420bb4c23fe2b3c89649";

    public static final String AUTH_TOKEN = "10460aea159848a9a7c688940995aaa1";

    public static final String RESP_DATA_TYPE = "JSON";
    /***秒滴keys end****/


    /**百度云推送 start**/
    public static final String BAIDU_YUN_PUSH_API_KEY="eqoZtUW4ZgeTPMiAwiaF6u9Z";

    public static final String BAIDU_YUN_PUSH_SECRET_KEY="t5w5u3VFpprpnPy9qAnBjkyGVzAZzrE2";

    public static final String CHANNEL_REST_URL = "api.push.baidu.com";
    /**百度云推送end**/



    /**用户token**/
    public static final String REQUEST_TOKEN_HEADER = "x-auth-token";
    /**用户session***/
    public static final String REQUEST_USER_SESSION = "current-user";
    /**客户端版本**/
    public static final String REQUEST_VERSION_KEY = "version";


    /**用户注册分布式锁路径***/
    public static final String USER_REGISTER_DISTRIBUTE_LOCK_PATH = "/user_reg";

    public static final String url = "https://api.weixin.qq.com/sns/jscode2session";
    public static final String appId ="wxf74c67f67043acf4";
    public static final String appSecret ="100d3ea626d739417d3289c9c112e06e";
    public static final String grant_type ="authorization_code";


    public static final String MDSMS_ACCOUNT_SID = "e781734be145420bb4c23fe2b3c89649";

    public static final String MDSMS_AUTH_TOKEN = "10460aea159848a9a7c688940995aaa1";

    public static final String MDSMS_REST_URL = "https://api.miaodiyun.com/20150822";

    public static final String MDSMS_VERCODE_TPLID = "1547212199";


    public static final String CACHE_PRODUCT_CATEGORY = "product:category";
    public static final String CACHE_PRODUCT_DETAIL = "product:detail";
    public static final String CACHE_PRODUCT_COMMODITY = "product:commodity";



    //get请求执行url后将返回的数据转换成json
    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "utf-8");
            jsonObject = JSONObject.parseObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    public static void message(String url) throws IOException {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String result = EntityUtils.toString(entity, "utf-8");
        }
        httpGet.releaseConnection();
    }
}
