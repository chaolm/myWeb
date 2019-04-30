package com.dripop.helpFeedback.service.impl;

import com.bean.LogicDelete;
import com.dripop.core.bean.Pageable;
import com.dripop.core.bean.Pagination;
import com.dripop.core.exception.ServiceException;
import com.dripop.core.util.StringUtil;
import com.dripop.dao.FeedbackDao;
import com.dripop.dao.HelpMainDao;
import com.dripop.dao.HelpSpecificDao;
import com.dripop.entity.THelpMain;
import com.dripop.entity.THelpSpecific;
import com.dripop.helpFeedback.dto.*;
import com.dripop.helpFeedback.service.HelpFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 帮助与反馈 service实现
 *
 * @author dq
 * @date 2018/5/31 11:46
 */

@Service
public class HelpFeedbackServiceImpl implements HelpFeedbackService{
    @Autowired
    private HelpMainDao helpMainDao;
    @Autowired
    private HelpSpecificDao helpSpecificDao;
    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    @Transactional
    public void addMain(String mainTitle, Integer helpChannel, Integer applicationType, Long id, String remark) {
        THelpMain main = null;
        if (id==null) {
            String sql = "SELECT id FROM t_help_main WHERE is_delete = 0 AND application_type = :applicationType AND help_channel = :helpChannel LIMIT 1";
            Long mainId = helpMainDao.findOneBySql(sql, Long.class, applicationType, helpChannel);
            if (mainId!=null){
                throw  new ServiceException("展示页不能配置多个主标题");
            }
            main = new THelpMain();
            main.setHelpChannel(helpChannel);
            main.setMainTitle(mainTitle);
            main.setApplicationType(applicationType);
            main.setRemark(remark);
            main.setCreateTime(new Date());
            main.setUpdateTime(main.getCreateTime());
            main.setIsDelete(LogicDelete.NOT_DELETE.getValue());
            helpMainDao.insert(main);
        }else {
            main = helpMainDao.get(id);
            main.setMainTitle(mainTitle);
            main.setRemark(remark);
            main.setUpdateTime(new Date());
            helpMainDao.update(main);
        }
    }

    @Override
    public HelpMainDto mainDetail(Long id) {
        String sql = "SELECT id mainId, main_title ,application_type, remark FROM t_help_main WHERE id = :id ";
        return  helpMainDao.findOneBySql(sql,HelpMainDto.class,id);
    }

    @Override
    @Transactional
    public void deleteMain(Long id) {
      helpSpecificDao.executeBySql("UPDATE t_help_specific SET is_delete = 1 WHERE is_delete = 0 and main_id = :mainId",id);
      helpMainDao.executeBySql("UPDATE t_help_main SET is_delete = 1 WHERE  id = :mainId",id);
    }

    @Override
    public List<HelpMainDto> mainList(Integer helpChannel) {
        String sql = "SELECT id mainId , main_title,application_type, remark  FROM t_help_main WHERE is_delete = :isDelete AND help_channel = :helpChannel ";
     return  helpMainDao.findManyBySql(sql,HelpMainDto.class,LogicDelete.NOT_DELETE.getValue(),helpChannel);
    }

    @Override
    @Transactional
    public void addSpecific(Long id, Long mainId, String subtitle, String content) {
        THelpSpecific specific = null;
        if (id==null){
            specific = new THelpSpecific();
            specific.setMainId(mainId);
            specific.setSubtitle(subtitle);
            specific.setContent(content);
            specific.setResolved(0);
            specific.setUnsolved(0);
            specific.setCreateTime(new Date());
            specific.setUpdateTime(specific.getCreateTime());
            specific.setIsDelete(LogicDelete.NOT_DELETE.getValue());
            helpSpecificDao.insert(specific);
        }else {
            specific = helpSpecificDao.get(id);
            specific.setSubtitle(subtitle);
            specific.setContent(content);
            specific.setUpdateTime(new Date());
            helpSpecificDao.update(specific);
        }
    }

    @Override
    @Transactional
    public void deleteSpecific(Long id) {
        helpSpecificDao.executeBySql("UPDATE t_help_specific SET is_delete = 1 WHERE id = :id",id);
    }

    @Override
    public HelpSpecificDto specificDetail(Long id) {
        String sql = "SELECT ths.id ,thm.id mainId , thm.main_title , ths.subtitle , ths.content \n" +
                "FROM t_help_specific ths LEFT JOIN t_help_main thm ON ths.main_id = thm.id WHERE ths.id = :id ";
        return  helpMainDao.findOneBySql(sql,HelpSpecificDto.class,id);
    }

    @Override
    public Pagination<HelpAllDto> helpList(HelpPageReq helpPageReq, Pageable pageable) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ths.id ,thm.id mainId , thm.main_title , ths.subtitle , ths.content ,ths.update_time ,ths.resolved ,ths.unsolved\n" +
                "FROM t_help_specific ths LEFT JOIN t_help_main thm ON ths.main_id = thm.id \n" +
                "WHERE ths.is_delete = 0 AND thm.is_delete = 0 ");
       if (helpPageReq.getHelpChannel()!=null){
           sb.append("and thm.help_channel = :helpChannel ");
           params.add(helpPageReq.getHelpChannel());
       }
       if (StringUtil.isNotBlank(helpPageReq.getSubtitle())){
           sb.append("and ths.subtitle like :subtitle ");
           params.add("%"+ helpPageReq.getSubtitle()+ "%");
       }
       if (helpPageReq.getMainId()!=null){
           sb.append("and ths.main_id = :mainId ");
           params.add(helpPageReq.getMainId());
       }
        sb.append(" ORDER BY ths.update_time DESC ");
        return  helpSpecificDao.findPageBySql(sb.toString(),pageable,HelpAllDto.class,params.toArray());
    }

    @Override
    public Pagination<FeedbackDto> feedbackList(FeedbackListReq feedbackListReq, Pageable pageable) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT tf.id , tf.create_time , tf.type , tf.reserve1 , tf.reserve2,tf.reserve3 ,tf.content ,tf.channel,tc.phone_no\n" +
                " FROM t_feedback tf LEFT JOIN t_customer tc ON tf.cust_id = tc.id WHERE tf.is_delete = 0 ");
        if (feedbackListReq.getChannel()!=null){
            sb.append("and tf.channel = :channel ");
            params.add(feedbackListReq.getChannel());
        }
        if (feedbackListReq.getType()!=null){
            sb.append("and tf.type = :type ");
            params.add(feedbackListReq.getType());
        }
        if (feedbackListReq.getStartTime() != null){
            sb.append("and tf.create_time >= :startTime ");
            params.add(feedbackListReq.getStartTime());
        }
        if (feedbackListReq.getEndTime() != null){
            sb.append("and tf.create_time <= :endTime ");
            params.add(feedbackListReq.getEndTime());
        }
        sb.append(" ORDER BY tf.create_time DESC ");
      return feedbackDao.findPageBySql(sb.toString(),pageable,FeedbackDto.class,params.toArray());
    }

    @Override
    public FeedbackDto feedbackDetail(Long id) {
        String sql = "SELECT tf.id , tf.type , tf.reserve1 , tf.reserve2,tf.reserve3 ,tf.content ,tf.channel,tc.phone_no\n" +
                " FROM t_feedback tf LEFT JOIN t_customer tc ON tf.cust_id = tc.id WHERE tf.is_delete = 0 AND tf.id = :id";
       return feedbackDao.findOneBySql(sql,FeedbackDto.class,id);
    }
}
