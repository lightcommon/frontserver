package com.light.project.szafc.file;

import java.util.List;

import com.light.common.tools.ICommonOprFlags;

public interface IFileDataSrc {
	
	public static final String DATA_SRC_KEY = "DATA_SRC_KEY";
	
	public static final String DATA_SRC_NEW = "DATA_SRC_NEW";
	
	public static final String DATA_SRC_UNSUCCESS = "DATA_SRC_UNSUCCESS";
	
	public List<Object> fetchSrcData() throws Exception;
	
	public void doDataFeedback(List<Object> data) throws Exception;
	
//	public Integer remainCnt();

}
