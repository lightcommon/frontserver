package com.light.project.szafc.business.order.entity;

import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class OrderDetail {
	
	private String procSessionId;
	
	private String refResourceId;
	
	private Integer recId;
	
	private String orderNo;
	
	private Timestamp createTime;
	
	private Integer count;
	
	private String station;
	
	private String stationName;
	
	private String equipmentId;
	
	private String modeCode;
	
	private String operationId;
	
	private String businessDay;
	
	private String tickInfoStr;
	
	private List<TickInfo> tickInfoList;

	public String getOrderNo() {
		return orderNo;
	}

	public String getProcSessionId() {
		return procSessionId;
	}

	public void setProcSessionId(String procSessionId) {
		this.procSessionId = procSessionId;
	}

	public String getRefResourceId() {
		return refResourceId;
	}

	public void setRefResourceId(String refResourceId) {
		this.refResourceId = refResourceId;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getBusinessDay() {
		return businessDay;
	}

	public void setBusinessDay(String businessDay) {
		this.businessDay = businessDay;
	}

	public String getTickInfoStr() {
		return tickInfoStr;
	}

	public void setTickInfoStr(String tickInfoStr) {
		this.tickInfoStr = tickInfoStr;
		
		String tmpStr;
		
		if (tickInfoStr != null)
		{
			try
			{
				String innerStr = tickInfoStr.replace(':', '-');
				
//				innerStr.replaceAll(regex, replacement)
				String tmpStr1 = innerStr.replaceAll("=", ":'");
				String tmpStr2 = tmpStr1.replaceAll("}", "'}");
				String tmpStr3 = tmpStr2.replaceAll(",", "',");
				tmpStr = tmpStr3.replaceAll("}',", "},");
				
				JSONArray jArray = JSONArray.fromObject(tmpStr);
				tickInfoList = (List<TickInfo>)JSONArray.toCollection(jArray, TickInfo.class);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		else
		{
			tickInfoList = null;
		}
		
		
		
		
	}

	public List<TickInfo> getTickInfoList() {
		return tickInfoList;
	}

	public void setTickInfoList(List<TickInfo> tickInfoList) {
		this.tickInfoList = tickInfoList;
	}

}
