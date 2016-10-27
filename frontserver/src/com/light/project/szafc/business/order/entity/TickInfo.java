package com.light.project.szafc.business.order.entity;

public class TickInfo {
	
	private String tickSN;
	
	private String tickLogicID;
	
	private String TAC;
	
	private String RWTxnSeqNum;
	
	private String txnValue;

	private String txnFee;
	
	private String tickTxnCnt;
	
	private String RWValueCnt;
	
	private String tickEffTime;
	
	private String tickExpTime;
	
	private String SAMId;
	
	private String SAMSeqNum;
	
	private String tickPhyType;
	
	private String tickAppType;

	private String testFlag;
	
	private String mediatype;
	
	private OrderDetail orderDetail;

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getTickSN() {
		return tickSN;
	}

	public void setTickSN(String tickSN) {
		this.tickSN = tickSN;
	}

	public String getTickLogicID() {
		return tickLogicID;
	}

	public void setTickLogicID(String tickLogicID) {
		this.tickLogicID = tickLogicID;
	}

	public String getTAC() {
		return TAC;
	}

	public void setTAC(String tAC) {
		TAC = tAC;
	}

	public String getRWTxnSeqNum() {
		return RWTxnSeqNum;
	}

	public void setRWTxnSeqNum(String rWTxnSeqNum) {
		RWTxnSeqNum = rWTxnSeqNum;
	}

	public String getTxnValue() {
		return txnValue;
	}

	public void setTxnValue(String txnValue) {
		this.txnValue = txnValue;
	}

	public String getTxnFee() {
		return txnFee;
	}

	public void setTxnFee(String txnFee) {
		this.txnFee = txnFee;
	}

	public String getTickTxnCnt() {
		return tickTxnCnt;
	}

	public void setTickTxnCnt(String tickTxnCnt) {
		this.tickTxnCnt = tickTxnCnt;
	}

	public String getRWValueCnt() {
		return RWValueCnt;
	}

	public void setRWValueCnt(String rWValueCnt) {
		RWValueCnt = rWValueCnt;
	}

	public String getTickEffTime() {
		return tickEffTime;
	}

	public void setTickEffTime(String tickEffTime) {
		String tmp = tickEffTime.replace('T', ' ');
		this.tickEffTime = tmp.replace('Z', ' ');
	}

	public String getTickExpTime() {
		return tickExpTime;
	}

	public void setTickExpTime(String tickExpTime) {
		String tmp = tickExpTime.replace('T', ' ');
		this.tickExpTime = tmp.replace('Z', ' ');
	}

	public String getSAMId() {
		return SAMId;
	}

	public void setSAMId(String sAMId) {
		SAMId = sAMId;
	}

	public String getSAMSeqNum() {
		return SAMSeqNum;
	}

	public void setSAMSeqNum(String sAMSeqNum) {
		SAMSeqNum = sAMSeqNum;
	}

	public String getTickPhyType() {
		return tickPhyType;
	}

	public void setTickPhyType(String tickPhyType) {
		this.tickPhyType = tickPhyType;
	}

	public String getTickAppType() {
		return tickAppType;
	}

	public void setTickAppType(String tickAppType) {
		this.tickAppType = tickAppType;
	}

	public String getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}

	public String getMediatype() {
		return mediatype;
	}

	public void setMediatype(String mediatype) {
		this.mediatype = mediatype;
	}

}
