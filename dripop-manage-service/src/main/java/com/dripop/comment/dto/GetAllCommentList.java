package com.dripop.comment.dto;

import com.dripop.core.bean.Pagination;

import java.io.Serializable;
import java.util.List;

public class GetAllCommentList implements Serializable {
    private GetAllCommentCount getAllCommentCount;
    private Pagination<GetAllComment> getAllComments;

    public GetAllCommentCount getGetAllCommentCount() {
        return getAllCommentCount;
    }

    public void setGetAllCommentCount(GetAllCommentCount getAllCommentCount) {
        this.getAllCommentCount = getAllCommentCount;
    }

    public Pagination<GetAllComment> getGetAllComments() {
        return getAllComments;
    }

    public void setGetAllComments(Pagination<GetAllComment> getAllComments) {
        this.getAllComments = getAllComments;
    }
}
