package com.azkj.noopsyche.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.azkj.noopsyche.entity.Sku;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class SearchUtils {

    @Autowired
    private JestHttpClient jestHttpClient;
    @Autowired
    private RedisUtil redisUtil;


    public Sku SearchSku(String search) throws IOException {
        Sku sku=new Sku();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.matchPhrasePrefixQuery("search",search)
        );
        Search searchs=new Search.Builder(searchSourceBuilder.toString())
				.addIndex("sku").addType("doc").build();
        SearchResult result=jestHttpClient.execute(searchs);
		String array= JSON.parseObject(result.getJsonString())
				.getJSONObject("hits")
				.getJSONArray("hits")
				.toJSONString();
        List<Map<String,Object>> mapList = (List<Map<String,Object>>) JSONArray.parse(array);
        		mapList.stream().forEach(
				parm->{
					Map<String,Object> objectMap= (Map<String, Object>) parm.get("_source");
                    if (objectMap!=null){
                        sku.setImgurl((String) objectMap.get("imgurl"));
                        sku.setId((Integer) objectMap.get("id"));
                        sku.setSpuid((Integer) objectMap.get("spuid"));
                        sku.setSkuname((String) objectMap.get("skuname"));
                        sku.setSkuprice((BigDecimal) objectMap.get("skuprice"));
                        sku.setUse((Integer) objectMap.get("use"));
                        sku.setRepertory((Integer) redisUtil.getObject("repertory:"+sku.getId()));//库存返回给前端
                    }
					System.out.println(objectMap.toString());
				}
		);
        return sku;
    }
}
