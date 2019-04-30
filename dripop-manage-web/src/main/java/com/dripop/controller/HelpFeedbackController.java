package com.dripop.controller;

import com.alibaba.fastjson.JSONObject;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.bean.ResultInfo;
import com.dripop.core.controller.BaseController;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.JsonUtil;
import com.dripop.core.util.StringUtil;
import com.dripop.helpFeedback.dto.*;
import com.dripop.helpFeedback.service.HelpFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帮助与反馈
 *
 * @author dq
 * @date 2018/5/31 11:42
 */
@Controller
public class HelpFeedbackController  extends BaseController {

    @Autowired
    private HelpFeedbackService helpFeedbackService;

    /**
     * 新增/编辑 主标题
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/main/add")
    @ResponseBody
    public ResultInfo addMain(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        String mainTitle = reqJson.getString("mainTitle");
        Integer helpChannel = reqJson.getInteger("helpChannel");
        Integer applicationType = reqJson.getInteger("applicationType");
        String remark = reqJson.getString("remark");
        Long id = reqJson.getLong("mainId");
        if(StringUtil.isBlank(mainTitle) || StringUtil.isBlank(remark)){
            throw  new ServiceException("参数错误");
        }
        if (mainTitle.length()>6){
            throw  new ServiceException("主标题上限为6个字");
        }
        helpFeedbackService.addMain(mainTitle,helpChannel,applicationType,id,remark);
        return returnSuccess();
    }

    /**
     * 主标题详情
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/main/detail")
    @ResponseBody
    public ResultInfo mainDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {

        Long id = reqJson.getLong("mainId");
        if(id ==null){
            throw  new ServiceException("参数错误");
        }
        HelpMainDto helpMainDto =  helpFeedbackService.mainDetail(id);
        return returnSuccess(helpMainDto);
    }

    /**
     * 主标题删除
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/main/delete")
    @ResponseBody
    public ResultInfo deleteMain(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("mainId");
        if(id ==null){
            throw  new ServiceException("参数错误");
        }
        helpFeedbackService.deleteMain(id);
        return returnSuccess();
    }

    /**
     * 主标题列表
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/main/list")
    @ResponseBody
    public ResultInfo mainList(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer helpChannel = reqJson.getInteger("helpChannel");
        if(helpChannel ==null){
            throw  new ServiceException("参数错误");
        }
        List<HelpMainDto> list = helpFeedbackService.mainList(helpChannel);
        return returnSuccess(list);
    }


    /**
     * 内容新增/编辑
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/specific/add")
    @ResponseBody
    public ResultInfo addSpecific(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        Long mainId = reqJson.getLong("mainId");
        String subtitle = reqJson.getString("subtitle");
        String content = reqJson.getString("content");
        helpFeedbackService.addSpecific(id,mainId,subtitle,content);
        return returnSuccess();
    }

    /**
     * 内容删除
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/specific/delete")
    @ResponseBody
    public ResultInfo deleteSpecific(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        if(id ==null){
            throw  new ServiceException("参数错误");
        }
        helpFeedbackService.deleteSpecific(id);
        return returnSuccess();
    }

    /**
     * 问题详情
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/specific/detail")
    @ResponseBody
    public ResultInfo specificDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {

        Long id = reqJson.getLong("id");
        if(id ==null){
            throw  new ServiceException("参数错误");
        }
        HelpSpecificDto helpSpecificDto =  helpFeedbackService.specificDetail(id);
        return returnSuccess(helpSpecificDto);
    }


    /**
     * 帮助中心列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("help/list")
    @ResponseBody
    public ResultInfo helpList(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        HelpPageReq helpPageReq = JsonUtil.fromJson(reqJson, HelpPageReq.class);
        Pagination<HelpAllDto> page = helpFeedbackService.helpList(helpPageReq, pageable);
        return returnSuccess(page);
    }

    /**
     * 用户反馈列表
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("feedback/list")
    @ResponseBody
    public ResultInfo feedbackList(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Integer pageNo = reqJson.getInteger("pageNo");
        Pageable pageable = new Pageable(pageNo);
        FeedbackListReq feedbackListReq = JsonUtil.fromJson(reqJson, FeedbackListReq.class);
        Pagination<FeedbackDto> page = helpFeedbackService.feedbackList(feedbackListReq, pageable);
        return returnSuccess(page);
    }

    /**
     *  用户反馈详情
     *
     * @param request
     * @param reqJson
     * @return
     */
    @PostMapping("feedback/detail")
    @ResponseBody
    public ResultInfo feedbackDetail(HttpServletRequest request, @RequestBody JSONObject reqJson) {
        Long id = reqJson.getLong("id");
        FeedbackDto feedbackDetail = helpFeedbackService.feedbackDetail(id);
        return returnSuccess(feedbackDetail);
    }


}
