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
import java.util.List;
import java.util.Map;

@Component
public class SearchUtils {

    @Autowired
    private JestHttpClient jestHttpClient;


    public Sku SearchSku(String skuname) throws IOException {
        Sku sku=new Sku();
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.boolQuery().must(
                        QueryBuilders.matchQuery("skuname",skuname)
                )
        );
        Search search=new Search.Builder(searchSourceBuilder.toString())
				.addIndex("sku").addType("doc").build();
        SearchResult result=jestHttpClient.execute(search);
		String array= JSON.parseObject(result.getJsonString())
				.getJSONObject("hits")
				.getJSONArray("hits")
				.toJSONString();
        List<Map<String,Object>> mapList = (List<Map<String,Object>>) JSONArray.parse(array);
        		mapList.forEach(
				parm->{
					 Map<String,Object> objectMap= (Map<String, Object>) parm.get("_source");
					System.out.println(objectMap.toString());
				}
		);


        return sku;
    }
}
