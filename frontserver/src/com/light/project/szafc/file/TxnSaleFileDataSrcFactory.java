package com.light.project.szafc.file;

import com.light.common.CommonTools;
import com.light.common.tools.ICommonOprFlags;
import com.light.common.tools.IFactory;
import com.light.project.szafc.business.order.service.IOrderInfo;

public class TxnSaleFileDataSrcFactory implements IFactory {
	
	private IOrderInfo orderInfoService;
	
	public IOrderInfo getOrderInfoService() {
		return orderInfoService;
	}


	public void setOrderInfoService(IOrderInfo orderInfoService) {
		this.orderInfoService = orderInfoService;
	}

	@Override
	public Object getObject() throws Exception {
		
		return getObject(null);
	}

	@Override
	public Object getObject(ICommonOprFlags oprFlags) throws Exception {
		
		TxnSaleFileDataSrc dataSrc = new TxnSaleFileDataSrc();
		dataSrc.setOrderInfoService(orderInfoService);
		dataSrc.setPocessSessionId(CommonTools.getUniqueId("TSF", null));
		
		return dataSrc;
	}

	@Override
	public Class getObjectType() {
		return TxnSaleFileDataSrc.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
