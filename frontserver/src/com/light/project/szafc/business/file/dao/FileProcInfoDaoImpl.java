package com.light.project.szafc.business.file.dao;

import java.sql.SQLException;
import java.util.List;

import com.huateng.ibatis.util.ExtendSqlMapClientDaoSupport;
import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.order.entity.OrderDetail;

public class FileProcInfoDaoImpl extends ExtendSqlMapClientDaoSupport  implements IFileProcInfoDao{

	private static final String FILE_INFO_PROC_SELECT = "common.file_info_proc_select";
	private static final String FILE_INFO_PROC_INSERT = "common.file_info_proc_insert";
	private static final String FILE_INFO_PROC_UPDATE = "common.file_info_proc_update";
	
	@Override
	public void insertFileProcInfo(FileProcInfo fileInfo) {
		
		getExtendSqlMapClientTemplate().insert(FILE_INFO_PROC_INSERT, fileInfo);
	}

	@Override
	public int updateFileProcInfo(FileProcInfo fileInfo) {
		
		return getExtendSqlMapClientTemplate().update(FILE_INFO_PROC_UPDATE, fileInfo);
	}

	@Override
	public ExtData<FileProcInfo> findFileProcInfo(List<Filter> filters, Page page) throws SQLException {
		
		return getExtendSqlMapClientTemplate().queryExtendForExtData(FILE_INFO_PROC_SELECT, filters, page);
	}

}
