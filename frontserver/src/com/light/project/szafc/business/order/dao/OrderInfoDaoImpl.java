package com.light.project.szafc.business.order.dao;

import java.util.List;

import com.huateng.ibatis.util.ExtendSqlMapClientDaoSupport;
import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.common.entity.file.DataPrepareParam;

public class OrderInfoDaoImpl extends ExtendSqlMapClientDaoSupport implements IOrderInfoDao {
	

	private static final String ORDER_INFO_DETAIL = "businessinfo.order_info_detail_toproc";
	
	private static final String ORDER_INFO_DETAIL_UPDATE = "businessinfo.order_info_detail_toproc_update";
	
	private static final String ORDER_DATA_PREPARE = "businessinfo.order_info_detail_prepare";

	@Override
	public ExtData<OrderDetail> findOrderDetailToProc(List<Filter> filters, Page page)
			throws Exception {
		
		return  getExtendSqlMapClientTemplate().queryExtendForExtData(ORDER_INFO_DETAIL, filters, page);
	}

	@Override
	public void prepareOrderData(DataPrepareParam param) throws Exception {
		
		getExtendSqlMapClientTemplate().insert(ORDER_DATA_PREPARE, param);

	}
	
	@Override
	public void updateOrderDetailProcBatch(List<OrderDetail> data) throws Exception {
		
		SqlMapClient sqlMap = getExtendSqlMapClientTemplate().getSqlMapClient();
		sqlMap.startBatch();
		for (OrderDetail cell : data) {
			sqlMap.update(ORDER_INFO_DETAIL_UPDATE, cell);
		}
		sqlMap.executeBatch();
		
//		getExtendSqlMapClientTemplate().update(ORDER_INFO_DETAIL_UPDATE, data);

	}

}
