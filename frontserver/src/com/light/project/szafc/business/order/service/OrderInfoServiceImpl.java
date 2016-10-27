package com.light.project.szafc.business.order.service;

import java.util.List;

import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.light.project.szafc.business.order.dao.IOrderInfoDao;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.common.entity.file.DataPrepareParam;

public class OrderInfoServiceImpl implements IOrderInfo {
	
	IOrderInfoDao orderInfoDao;
	
	

	public IOrderInfoDao getOrderInfoDao() {
		return orderInfoDao;
	}



	public void setOrderInfoDao(IOrderInfoDao orderInfoDao) {
		this.orderInfoDao = orderInfoDao;
	}



	@Override
	public ExtData<OrderDetail> findOrderDetailToProc(List<Filter> Filters, Page page)
			throws Exception {
		
		return orderInfoDao.findOrderDetailToProc(Filters, page);
		
	}



	@Override
	public void prepareOrderData(DataPrepareParam param) throws Exception {
		orderInfoDao.prepareOrderData(param);
		
	}



	@Override
	public void updateOrderDetailProcBatch(List<OrderDetail> data) throws Exception {
		orderInfoDao.updateOrderDetailProcBatch(data);
	}

}
