package com.dripop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liyou on 2018/2/18.
 */
@Entity
@Table(name = "t_order_cancel_submit")
public class TOrderCancelSubmit implements Serializable {

	/**
	 * 退款、退货单审核状态
	 */
	public interface OP_STATUS_TYPE{
		public final static int SUBMIT = 1;//待审核
		public final static int SUCCESS = 2;//审核通过
		public final static int REFUSE = 3;//审核拒绝
		public final static int CANCEL = 4;//申请取消
		public final static int SUCCESS_AFTER_SALE = 5;//售后成功
		public final static int REFUSE_AFTER_SALE = 6;//售后拒绝

	}

	public interface SERVICE_TYPE{
		public final static int BACK = 1;//退货
		public final static int CHANGE = 2;//换货
		public final static int FIX = 3;//维修
		public final static int REFUND = 4;//退款
		public final static int DEPOSIT = 5;//退定金
	}

	/**
	 * 退款、退货单审核状态
	 */
	public interface OP_STATUS_REFUND_TYPE{
		public final static int SUBMIT = 1;//待审核
		public final static int SUCCESS = 2;//审核通过
		public final static int REFUSE = 3;//审核拒绝
		public final static int CANCEL = 4;//申请取消

	}

	/**
	 * APP端退款单状态
	 */
	public interface APP_REFOUND_STATUS {
		public final static int APPLY = 1; //申请退款
		public final static int AGREE = 2; //同意退款
		public final static int REFUSE = 3; //拒绝退款
		public final static int SUCCESS = 4; //退款成功

	}

	/**
	 * 新增服务类型
	 */
	public interface HANDLE_STATUS_TYPE{
		public final static int AGREE_REQUEST = 0;//可再次申请售后服务
		public final static int REFUSE_REQUEST = 1;//不能进行申请
	}

	@Id
	@GeneratedValue
	@Column(name = "cancel_order_id")
	private Long id;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "cust_id")
	private Long custId;

	private Integer status;

	@Column(name = "cancel_reason")
	private String cancelReason;

	@Column(name = "cancel_desc")
	private String cancelDesc;

	@Column(name = "contact_phone")
	private String contactPhone;

	@Column(name = "cancel_time")
	private Date cancelTime;

	@Column(name = "audit_time")
	private Date auditTime;

	@Column(name = "refuse_reason")
	private String refuseReason;

	@Column(name = "audit_oper_id")
	private Long auditOperId;

	@Column(name = "total_refund")
	private Integer totalRefund;

	@Column(name = "reserve1")
	private String reserve1;

	@Column(name = "reserve2")
	private String reserve2;

	@Column(name = "reserve3")
	private String reserve3;

	@Column(name = "reserve4")
	private String reserve4;

	@Column(name = "reserve5")
	private String reserve5;

	@Column(name = "reserve6")
	private String reserve6;

	@Column(name = "service_type")
	private Integer serviceType;

	@Column(name = "handle_status")
	private Integer handleStatus;

	@Column(name = "order_detail_id")
	private Long orderDetailId;

	@Column(name = "cancel_order_no")
	private String cancelOrderNo;

	@Column(name = "request_cancel_time")
	private Date requestCancelTime;

	@Column(name = "op_audit_time")
	private Date opAuditTime;//OP后台审核时间

	@Column(name = "op_audit_oper_id")
	private Long opAuditOperId;//OP后台审核人

	@Column(name = "op_status")
	private Integer opStatus;//OP后台审核状态 1 待审核 2 审核通过 3 审核拒绝 4取消申请

	@Column(name = "is_open")
	private Integer isOpen;

	@Column(name = "org_id")
	private Long orgId;

	public Long getOrgId() { return orgId; }

	public void setOrgId(Long orgId) { this.orgId = orgId; }

	public Integer getIsOpen() { return isOpen; }

	public void setIsOpen(Integer isOpen) { this.isOpen = isOpen; }

	public Integer getIsCheck() { return isCheck; }

	public void setIsCheck(Integer isCheck) { this.isCheck = isCheck; }

	@Column(name = "is_check")
	private Integer isCheck;

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelDesc() {
		return cancelDesc;
	}

	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public Long getAuditOperId() {
		return auditOperId;
	}

	public void setAuditOperId(Long auditOperId) {
		this.auditOperId = auditOperId;
	}

	public Integer getTotalRefund() {
		return totalRefund;
	}

	public void setTotalRefund(Integer totalRefund) {
		this.totalRefund = totalRefund;
	}

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(Integer handleStatus) {
		this.handleStatus = handleStatus;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getCancelOrderNo() {
		return cancelOrderNo;
	}

	public void setCancelOrderNo(String cancelOrderNo) {
		this.cancelOrderNo = cancelOrderNo;
	}

	public Date getRequestCancelTime() {
		return requestCancelTime;
	}

	public void setRequestCancelTime(Date requestCancelTime) {
		this.requestCancelTime = requestCancelTime;
	}

	public Date getOpAuditTime() {
		return opAuditTime;
	}

	public void setOpAuditTime(Date opAuditTime) {
		this.opAuditTime = opAuditTime;
	}

	public Long getOpAuditOperId() {
		return opAuditOperId;
	}

	public void setOpAuditOperId(Long opAuditOperId) {
		this.opAuditOperId = opAuditOperId;
	}

	public Integer getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}
}