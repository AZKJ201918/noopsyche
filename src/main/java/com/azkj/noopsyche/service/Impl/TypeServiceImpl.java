package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.service.TypeService;


import org.apache.commons.lang3.StringUtils;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TypeServiceImpl implements TypeService {

        @Autowired
        private SolrServer solrServer;

        public void setSolrServer(SolrServer solrServer) {
            this.solrServer = solrServer;
        }

        @Override
        public Map<String, Object> query(String queryString, String sort, int start, int pageSize) throws Exception {
            Map<String, Object> maps = new HashMap<String, Object>();
            List productList = new ArrayList<>();
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery("name:" + queryString); // *name:queryString*按名称查询
            start = (start - 1) * pageSize;
            solrQuery.setStart(start); //设置起始位置
            solrQuery.setRows(pageSize); // 设置页大小
            solrQuery.setHighlight(true); //启用高亮
            solrQuery.addHighlightField("name"); //设置高亮字段
            solrQuery.addHighlightField("category"); //设置高亮字段
            solrQuery.setHighlightFragsize(200); // 设置高亮内容大小
            solrQuery.setHighlightSimplePre("<em>"); //设置高亮前缀
            solrQuery.setHighlightSimplePost("</em>"); //设置高亮后缀
            sort = StringUtils.isNotEmpty(sort) ? sort : "score";
            solrQuery.addSort(sort, SolrQuery.ORDER.desc); //设置排序 按score降序排序
            QueryResponse queryResponse = solrServer.query(solrQuery);
            int qtime = queryResponse.getQTime();//查询花费时间
            SolrDocumentList solrDocumentList = queryResponse.getResults();// 获取查询结果集
            // 获取高亮内容 第一个Map的键是文档的ID，第二个Map的键是高亮显示的字段名
            Map<String, Map<String, List<String>>> highlightingMaps = queryResponse.getHighlighting();
            long totals = solrDocumentList.getNumFound();// 查询到的总记录数
            if (!solrDocumentList.isEmpty()) {
                Iterator<SolrDocument> it = solrDocumentList.iterator();
                while (it.hasNext()) {
                    SolrDocument solrDocument = it.next();
                    // 获取文档id
                    String id = solrDocument.getFieldValue("id").toString();
                    // 处理高亮
                    Map<String, List<String>> highlightFieldMap = highlightingMaps.get(id);
                    if (!highlightFieldMap.isEmpty()) {
                        List<String> highlightName = highlightFieldMap.get("name");
                        List<String> highlightCategory = highlightFieldMap.get("category");
                        if (highlightName != null && !highlightName.isEmpty()) {
                            String name = highlightName.get(0);
                            // 将文档结果集中的name设置为高亮后的name
                            solrDocument.setField("name", name);
                        }
                        if (highlightCategory != null && !highlightCategory.isEmpty()){
                            String category = highlightCategory.get(0);
                            // 将文档结果集中的category设置为高亮后的category
                            solrDocument.setField("category", category);
                        }
                    }
                    // 调用solrDocument转java bean
                    // 处理图片地址为空或图片地址无效
                }
            }
            maps.put("qtime", qtime);
            maps.put("totals", totals);
            maps.put("results", productList);
            return maps;
        }

}
