package com.dripop.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by shery on 2017/8/22.
 */
@javax.persistence.Entity
@Table(name = "t_review_goods")
public class TReviewGoods implements java.io.Serializable{
    public interface SUBMIT_TYPE{
        public static final int CUSTOMER = 1;//客户提交
        public static final int SYSTEM = 2;//系统提交
    }

    /**
     * 是否有效
     */
    public interface IS_USED{
        public static final int ON = 1;//有效
        public static final int OFF = 0;//无效
    }

    /**
     * 评论状态
     */
    public interface STATUS{
        public static final int READY_CHECK = 1;//待审核
        public static final int PASS_CHECK = 2;//已通过
        public static final int REFUSE_CHECK = 3;//已拒绝
    }

    public interface FROM_TYPE{
        public static final int PC = 1;    //pc
        public static final int TOCAPP = 2;//toc-app
        public static final int WAP = 3;   //手机wap
        public static final int IMPORT = 4;//导入
        public static final int SYSTEM = 5;   //系统定时任务
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "is_used")
    private Integer isUsed;

    @Column(name = "status")
    private Integer status;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_detail_id")
    private Long orderDetailId;

    @Column(name = "goods_online_id")
    private Long goodsOnlineId;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "model_id")
    private Long modelId;

    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "modify_time")
    private Date modifyTime;

    @Column(name = "goods_star_level")
    private Integer goodsStarLevel;

    @Column(name = "serve_star_level")
    private Integer serveStarLevel;

    @Column(name = "submit_type")
    private Integer submitType;

    @Column(name = "from_type")
    private Integer fromType;

    @Column(name = "review_time")
    private Date reviewTime;

    @Column(name = "cust_head_url")
    private String custHeadUrl;

    @Column(name = "cust_name")
    private String custName;

    @Column(name = "purchase_model")
    private String purchaseModel;

    @Column(name = "purchase_time")
    private Date purchaseTime;

    @Column(name = "agree_num")
    private Integer agreeNum;

    @Column(name = "content")
    private String content;

    @Column(name = "picture_addr")
    private String pictureAddr;

    public String getPictureAddr() {
        return pictureAddr;
    }

    public void setPictureAddr(String pictureAddr) {
        this.pictureAddr = pictureAddr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getGoodsOnlineId() {
        return goodsOnlineId;
    }

    public void setGoodsOnlineId(Long goodsOnlineId) {
        this.goodsOnlineId = goodsOnlineId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getGoodsStarLevel() {
        return goodsStarLevel;
    }

    public void setGoodsStarLevel(Integer goodsStarLevel) {
        this.goodsStarLevel = goodsStarLevel;
    }

    public Integer getServeStarLevel() {
        return serveStarLevel;
    }

    public void setServeStarLevel(Integer serveStarLevel) {
        this.serveStarLevel = serveStarLevel;
    }

    public Integer getSubmitType() {
        return submitType;
    }

    public void setSubmitType(Integer submitType) {
        this.submitType = submitType;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getCustHeadUrl() {
        return custHeadUrl;
    }

    public void setCustHeadUrl(String custHeadUrl) {
        this.custHeadUrl = custHeadUrl;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPurchaseModel() {
        return purchaseModel;
    }

    public void setPurchaseModel(String purchaseModel) {
        this.purchaseModel = purchaseModel;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Integer getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(Integer agreeNum) {
        this.agreeNum = agreeNum;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
