package com.dripop.helpFeedback.service;

import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.helpFeedback.dto.*;

import java.util.List;

/**
 * 帮助与反馈 service
 *
 * @author dq
 * @date 2018/5/31 11:45
 */

public interface HelpFeedbackService {

    /**
     * 新增/编辑 主标题
     * @param mainTitle
     * @param helpChannel
     * @param applicationType
     * @param id
     * @param remark
     */
    void addMain(String mainTitle, Integer helpChannel, Integer applicationType, Long id, String remark);

    /**
     * 主标题详情
     * @param id
     * @return
     */
    HelpMainDto mainDetail(Long id);

    /**
     * 主标题删除
     * @param id
     */
    void deleteMain(Long id);

    /**
     * 主标题列表
     * @param helpChannel
     * @return
     */
    List<HelpMainDto> mainList(Integer helpChannel);

    /**
     * 内容新增编辑
     * @param id
     * @param mainId
     * @param subtitle
     * @param content
     */
    void addSpecific(Long id, Long mainId, String subtitle, String content);

    /**
     * 内容删除
     * @param id
     */
    void deleteSpecific(Long id);

    /**
     * 内容详情
     * @param id
     * @return
     */
    HelpSpecificDto specificDetail(Long id);

    /**
     * 帮助中心列表
     * @param helpPageReq
     * @param pageable
     * @return
     */
    Pagination<HelpAllDto> helpList(HelpPageReq helpPageReq, Pageable pageable);

    /**
     * 用户反馈列表
     * @param feedbackListReq
     * @param pageable
     * @return
     */
    Pagination<FeedbackDto> feedbackList(FeedbackListReq feedbackListReq, Pageable pageable);

    /**
     * 用户反馈详情
     * @param id
     * @return
     */
    FeedbackDto feedbackDetail(Long id);
}
