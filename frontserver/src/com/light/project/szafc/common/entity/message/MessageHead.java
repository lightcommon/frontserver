package com.light.project.szafc.common.entity.message;

import com.light.messageframework.exchange.entity.ExchangeHeadInfo;

public class MessageHead extends ExchangeHeadInfo {
	
	private String repCode;
	
	private Boolean reqFlag;
	
	public String getRepCode() {
		return repCode;
	}

	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}

	public String getReqFlagString() {
		
		if (reqFlag == null)
		{
			return null;
		}
		else if (reqFlag)
		{
			return "00";
		}
		else
		{
			return "01";
		}
		
	}
	
	public String getReqFlag() {
		
		return getReqFlagString();
		
	}
	
	public Boolean isReqFlag() {
		return reqFlag;
	}

	public void setReqFlagBool(Boolean reqFlag) {
		this.reqFlag = reqFlag;
	}

	public void setReqFlag(String reqFlag) {
		if (reqFlag.equals("1") || reqFlag.equals("01"))
		{
			this.reqFlag = false;
		}
		else if (reqFlag.equals("0") || reqFlag.equals("00"))
		{
			this.reqFlag = true;
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}

}
