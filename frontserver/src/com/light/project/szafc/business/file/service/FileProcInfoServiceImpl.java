package com.light.project.szafc.business.file.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.huateng.ibatis.util.vo.Filter.Data;
import com.light.project.szafc.business.file.dao.IFileProcInfoDao;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.order.entity.OrderDetail;

public class FileProcInfoServiceImpl implements IFileProcInfo {
	
	private IFileProcInfoDao dao;

	public IFileProcInfoDao getDao() {
		return dao;
	}

	public void setDao(IFileProcInfoDao dao) {
		this.dao = dao;
	}

	@Override
	public void insertFileProcInfo(FileProcInfo fileInfo) {
		
		dao.insertFileProcInfo(fileInfo);
		
	}

	@Override
	public int updateFileProcInfo(FileProcInfo fileInfo) {
		return dao.updateFileProcInfo(fileInfo);
		
	}
	
	@Override
	public ExtData<FileProcInfo> findFileProcInfo(List<Filter> Filters, Page page) throws SQLException {
		
		return dao.findFileProcInfo(Filters, page);
		
	}

	@Override
	public void processFileProcInfo(FileProcInfo fileInfo) throws SQLException {
		
//		List<Filter> filters = new LinkedList<Filter>();
//		Filter filter = new Filter();
//		filter.setField("fileBusiCode");
//		Data data = new Data();
//		data.setComparison(Filter.Comparison.EQ);
//		data.setType(Filter.STRING);
//		data.setValue(fileInfo.getFileBusiCode());
//		filter.setData(data);
//		filters.add(filter);
//		
//		filter = new Filter();
//		filter.setField("fileName");
//		data = new Data();
//		data.setComparison(Filter.Comparison.EQ);
//		data.setType(Filter.STRING);
//		data.setValue(fileInfo.getFileName());
//		filter.setData(data);
//		filters.add(filter);
//		
//		Page currPage = new Page();
//		currPage.setLimit("1000");
//		currPage.setStart("0");
//		
//		ExtData<FileProcInfo> retData  = findFileProcInfo(filters, currPage);
//		
//		List<FileProcInfo> fileList = retData.getRoot();
		
		int updateCnt = updateFileProcInfo(fileInfo);
		
		if (updateCnt <= 0)
		{
			insertFileProcInfo(fileInfo);
		}
	}

}
