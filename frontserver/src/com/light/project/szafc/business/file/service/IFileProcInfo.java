package com.light.project.szafc.business.file.service;

import java.sql.SQLException;
import java.util.List;

import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.order.entity.OrderDetail;

public interface IFileProcInfo {
	
	public void insertFileProcInfo(FileProcInfo fileInfo);
	
	public int updateFileProcInfo(FileProcInfo fileInfo);
	
	public void processFileProcInfo(FileProcInfo fileInfo) throws SQLException;
	
	public ExtData<FileProcInfo> findFileProcInfo(List<Filter> filters, Page page) throws SQLException;

}
