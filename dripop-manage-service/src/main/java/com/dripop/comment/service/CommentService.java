package com.dripop.comment.service;

import com.dripop.comment.dto.CommentSearchReq;
import com.dripop.comment.dto.GetAllComment;
import com.dripop.comment.dto.GetAllCommentCount;
import com.dripop.comment.dto.GetAllCommentList;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;


public interface CommentService {

    /**
     * 获取评论列表
     * @param commentSearchReq
     * @return
     */

    Pagination<GetAllComment> getAllComment(CommentSearchReq commentSearchReq, Pageable pageable);

    /**
     * 更新评论状态
     * @param id
     * @param status
     */
    void updateCommentStatus(Long id, Integer status);

    /**
     * 评论统计
     * @return
     */
    GetAllCommentCount getCount();
}
