package com.light.project.szafc.file;

import java.util.LinkedList;
import java.util.List;

import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Filter.Data;
import com.huateng.ibatis.util.vo.Page;
import com.light.common.datatransform.CollectionTrans;
import com.light.common.tools.ctxdata.ICtxAccessor;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.business.order.service.IOrderInfo;
import com.light.project.szafc.common.CommonConst;
import com.light.project.szafc.common.BusiTools;
import com.light.project.szafc.common.entity.file.DataPrepareParam;

public class TxnSaleFileDataSrc implements IFileDataSrc {
	
	private String pocessSessionId;
	
	private IOrderInfo orderInfoService;
	
	public IOrderInfo getOrderInfoService() {
		return orderInfoService;
	}


	public void setOrderInfoService(IOrderInfo orderInfoService) {
		this.orderInfoService = orderInfoService;
	}


	private Page currPage;
	
	private Integer maxSeq;

	public String getPocessSessionId() {
		return pocessSessionId;
	}


	public void setPocessSessionId(String pocessSessionId) {
		this.pocessSessionId = pocessSessionId;
	}


	@Override
	public List<Object> fetchSrcData() throws Exception {
		
//		int maxSeq = 0;
		int currSeq = 0;
		
		ICtxAccessor ctxAccessor = BusiTools.getCtxAccessor(BusiTools.GLOBAL_CTX);
		
		if (currPage == null)
		{
			currPage = new Page();
			
			currPage.setDir("ASC");
			
			currPage.setLimit("1000");
			currPage.setStart("0");
			
			currPage.setSort("station");
			
			
			DataPrepareParam param = new DataPrepareParam();
			
			param.setProcSessionId(pocessSessionId);
			
			
			String startSeq = (String)ctxAccessor.getData(CommonConst.BusiConfig.TXNFILE_CURRSEQ_ORDERDETAIL_KEY);
			if (startSeq == null)
			{
				startSeq = "0";
			}
			param.setStartSeq(Integer.valueOf(startSeq));
			
			orderInfoService.prepareOrderData(param);
			
		}
		
		
		List<Filter> filters = new LinkedList<Filter>();
		Filter filter = new Filter();
		filter.setField("procSessionId");
		Data data = new Data();
		data.setComparison(Filter.Comparison.EQ);
		data.setType(Filter.STRING);
		data.setValue(pocessSessionId);
		filter.setData(data);
		filters.add(filter);
		
		ExtData<OrderDetail> retData =  orderInfoService.findOrderDetailToProc(filters, currPage);
		List<OrderDetail> dataList = retData.getRoot();
		
		if (dataList != null && dataList.size() > 0)
		{
		
			List retList = new LinkedList(dataList);
			
			Integer s = Integer.valueOf(currPage.getStart());
			Integer l = Integer.valueOf(currPage.getLimit());
			Integer n = s + l;
			
			currPage.setStart(n.toString());
			
			{
				
				for (OrderDetail cell: dataList)
				{
					currSeq = cell.getRecId();
					if (maxSeq == null)
					{
						maxSeq = 0;
					}
					
					if (currSeq > maxSeq)
					{
						maxSeq = currSeq;
					}
				}
			}
			
			
			return retList;
		}
		else
		{
			if (maxSeq != null)
			{
				ctxAccessor.setData(CommonConst.BusiConfig.TXNFILE_CURRSEQ_ORDERDETAIL_KEY, Integer.valueOf(maxSeq).toString());
			}
			
			return null;
		}
		
	}


	@Override
	public void doDataFeedback(List<Object> data) throws Exception {
		
		CollectionTrans cTrans = new CollectionTrans<Object, OrderDetail>();
		
		orderInfoService.updateOrderDetailProcBatch((List<OrderDetail>)cTrans.doTransform(data));
	}

}
