package com.light.project.szafc.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

import com.huateng.ibatis.util.vo.ExtData;
import com.huateng.ibatis.util.vo.Filter;
import com.huateng.ibatis.util.vo.Page;
import com.huateng.ibatis.util.vo.Filter.Data;
import com.light.common.file.FileInfo;
import com.light.common.tools.busiproc.IBusinessHandler;
import com.light.common.tools.busiproc.IBusinessProcessor;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.file.service.IFileProcInfo;
import com.light.project.szafc.common.BusiTools;

public class FileWindUp implements IBusinessProcessor {
	
	private String fileBackFolder;
	
	private IFileProcInfo fileProcHandler;
	
	private IBusinessHandler fileInformHandler;
	
	private Integer waitDays = 0;

	private Integer watchDays = 2;
	
	public String getFileBackFolder() {
		return fileBackFolder;
	}

	public void setFileBackFolder(String fileBackFolder) {
		this.fileBackFolder = fileBackFolder;
	}

	public IFileProcInfo getFileProcHandler() {
		return fileProcHandler;
	}

	public void setFileProcHandler(IFileProcInfo fileProcHandler) {
		this.fileProcHandler = fileProcHandler;
	}
	
	public IBusinessHandler getFileInformHandler() {
		return fileInformHandler;
	}

	public void setFileInformHandler(IBusinessHandler fileInformHandler) {
		this.fileInformHandler = fileInformHandler;
	}

	public Integer getWaitDays() {
		return waitDays;
	}

	public void setWaitDays(Integer waitDays) {
		this.waitDays = waitDays;
	}

	public Integer getWatchDays() {
		return watchDays;
	}

	public void setWatchDays(Integer watchDays) {
		this.watchDays = watchDays;
	}

	private String getNewPath(String oldPath)
	{
		String[] oldPathArray = oldPath.split("/");
		return fileBackFolder + "/" + oldPathArray[oldPathArray.length - 2] + "/" +  oldPathArray[oldPathArray.length - 1];
	}
	
	@Override
	public Object doBusi() throws Exception {
		
		List<String> watchDateList = new ArrayList();
		for(int i = 0; i < watchDays; i ++)
		{
			watchDateList.add(BusiTools.dateCal(null,null,0 - i -1, Calendar.DATE));
		}
		
		
		List<Filter> filters = new LinkedList<Filter>();
		Filter filter = new Filter();
		filter.setField("businessday");
		Data data = new Data();
//		data.setComparison(Filter.Comparison.EQ);
		data.setType(Filter.STRINGLIST);
		data.setValue(watchDateList);
		filter.setData(data);
		filters.add(filter);
		
		Page currPage = new Page();
//		currPage.setDir("ASC");
		currPage.setLimit("1000");
		currPage.setStart("0");
//		currPage.setSort("station");
		
		ExtData<FileProcInfo> retData = fileProcHandler.findFileProcInfo(filters, currPage);
		List<FileProcInfo> dataList = retData.getRoot();
		
		for(FileProcInfo cell : dataList)
		{
			if (!cell.getFileProcStatus().substring(0,1).equalsIgnoreCase("M"))
			{
				if (cell.getFileProcStatus().equalsIgnoreCase("C00"))
				{
					try
					{
						String oldPath = cell.getFileCurrPath();
						String newPath = getNewPath(oldPath);
						
						File file = new File(oldPath);
						File backFile = new File(newPath);
						FileUtils.copyFile(file, backFile);
						file.delete();
						
						FileProcInfo procInfo =(FileProcInfo) BeanUtils.cloneBean(cell);
						
						procInfo.setFileProcStatus("M00");
						procInfo.setFileCurrPath(newPath);
						procInfo.setFileProcTimes(null);
						fileProcHandler.updateFileProcInfo(procInfo);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
				else if (cell.getFileProcStatus().substring(0,1).equalsIgnoreCase("P"))
				{
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileDigest(Hex.decodeHex(cell.getFileDigest().toCharArray()));
					fileInfo.setFileName(cell.getFileName());
					fileInfo.setFileUrl(cell.getFileCurrPath());
					fileInfo.setFileType(cell.getFileType());
					
					fileInformHandler.doBusi(fileInfo);
				}
			}
		}
		
		return null;
	}

}
