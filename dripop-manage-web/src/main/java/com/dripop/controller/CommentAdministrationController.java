package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.comment.dto.CommentSearchReq;
import com.dripop.comment.dto.GetAllComment;
import com.dripop.comment.dto.GetAllCommentCount;
import com.dripop.comment.dto.GetAllCommentList;
import com.dripop.comment.service.CommentService;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("commentAdministration")
public class CommentAdministrationController extends BaseController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取评论列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getAllComment")
    @ResponseBody
    public ResultInfo getAllComment(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        CommentSearchReq commentSearchReq = JsonUtil.fromJson(reqJson, CommentSearchReq.class);
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable page  = new Pageable(pageNo);
        Pagination<GetAllComment> result = commentService.getAllComment(commentSearchReq,page);
        return returnSuccess(result);
    }

    /**
     * 评论统计
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("getCount")
    @ResponseBody
    public ResultInfo getCount(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        GetAllCommentCount result = commentService.getCount();
        return returnSuccess(result);
    }

    /**
     * 更新评论状态
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("updateCommentStatus")
    @ResponseBody
    public ResultInfo updateCommentStatus(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("orderId");
        Integer status = reqJson.getInteger("status");
        commentService.updateCommentStatus(id, status);
        return returnSuccess();
    }


}
