package com.dripop.solr;

import com.dripop.constant.SolrConstant;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * solr服务
 *
 * @author dq
 * @date 2018/6/7 16:12
 */
public class SolrjServer {
    private  static final  Logger logger = LoggerFactory.getLogger(SolrjServer.class);

    private static HttpSolrServer solrServer = null;


    /**
     * 删除所有索引
     */
    public static void deleteAllIndex() {
        try {
            HttpSolrServer server = getSolrServer();
            server.deleteByQuery("*:*");
            server.commit();
        } catch (SolrServerException | IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 删除指定索引
     * @param ids
     */
    public static void deleteIndex(List<String> ids) {
        if (ids != null && ids.size() > 0) {
            try {
                HttpSolrServer server = getSolrServer();
                server.deleteById(ids);
                server.commit();
            } catch (SolrServerException e) {
                logger.error("", e);
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    /**
     *  新建索引
     */
    public static void buildIndex(List<SolrInputDocument> list) {
        if (!CollectionUtils.isEmpty(list)) {
            try {
                HttpSolrServer server = getSolrServer();
                server.add(list);
                server.commit();
            } catch (SolrServerException e) {
                logger.error("", e);
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

    // solrServer是线程安全的，所以在使用时需要使用单例的模式，减少资源的消耗
    public static HttpSolrServer getSolrServer() {
        if (solrServer == null) {
            solrServer = new HttpSolrServer(SolrConstant.SOLR_URL);
        }
        return solrServer;
    }

}
