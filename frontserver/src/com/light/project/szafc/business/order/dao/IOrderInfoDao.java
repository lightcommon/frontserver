package com.light.project.szafc.business.order.dao;

import java.util.List;

import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.common.entity.file.DataPrepareParam;

public interface IOrderInfoDao {
	public ExtData<OrderDetail> findOrderDetailToProc(List<Filter> Filters, Page page) throws Exception;
	
	public void prepareOrderData(DataPrepareParam param)
			throws Exception;

	public void updateOrderDetailProcBatch(List<OrderDetail> data) throws Exception;

}
