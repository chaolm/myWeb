package com.dripop.comment.service.impl;

import com.dripop.comment.dto.CommentSearchReq;
import com.dripop.comment.dto.GetAllComment;
import com.dripop.comment.dto.GetAllCommentCount;
import com.dripop.comment.dto.GetAllCommentList;
import com.dripop.comment.service.CommentService;
import com.dripop.core.bean.Filter;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.QLBuilder;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.ReviewGoodsDao;
import com.dripop.entity.TReviewGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ReviewGoodsDao reviewGoodsDao;

    @Override
    public GetAllCommentCount getCount() {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1)  allCount, \n" +
                "count(case when trgg.status = 1 then '1' else null end) waitCount ,\n" +
                "count(case when trgg.status = 2 then '1' else null end) passCount,\n" +
                "count(case when trgg.status = 3 then '1' else null end) refuseCount\n" +
                "FROM " +
                "(SELECT  DISTINCT trg.order_id ,trg.goods_star_level, trg.picture_addr, trg.purchase_time,trg.cust_name,trg.cust_id,trg.`status` ,trg.content\n" +
                "from t_review_goods trg) trgg");
        GetAllCommentCount getAllCommentCount = reviewGoodsDao.findOneBySql(sb.toString(), GetAllCommentCount.class);
        return getAllCommentCount;
    }

    @Override
    public Pagination<GetAllComment> getAllComment(CommentSearchReq commentSearchReq, Pageable page) {

        StringBuffer sb = new StringBuffer();
        List<Object> params = new ArrayList<>();
        sb.append("SELECT * FROM (SELECT DISTINCT trg.order_id ,trg.review_time,trg.goods_star_level,tc.phone_no, trg.picture_addr, trg.purchase_time,trg.cust_name,trg.cust_id,trg.`status` ,trg.content\n" +
                "from t_review_goods trg LEFT JOIN t_customer tc on trg.cust_id = tc.id ) tt where 1 = 1 ");
        if (StringUtil.isNotBlank(commentSearchReq.getPhoneNo())) {
            sb.append(" and tt.phone_no = :phoneNo");
            params.add(commentSearchReq.getPhoneNo());
        }
        if (commentSearchReq.getStatus() != null) {
            sb.append(" and tt.status = :status");
            params.add(commentSearchReq.getStatus());
        }
        sb.append(" ORDER BY tt.review_time DESC");
        Pagination<GetAllComment> pages = reviewGoodsDao.findPageBySql(sb.toString(),page,GetAllComment.class,params.toArray());
        return pages;
    }
    @Transactional
    @Override
    public void updateCommentStatus(Long id, Integer status) {
        StringBuffer sb = new StringBuffer();
        List<Object> param = new ArrayList<>();
        sb.append("update t_review_goods set `status` = :status  where order_id = :id");
        reviewGoodsDao.executeBySql(sb.toString(),status,id);
    }
}
