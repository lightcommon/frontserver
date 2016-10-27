package com.light.project.szafc.file;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.acplt.oncrpc.XdrAble;
import org.apache.commons.codec.binary.Hex;






//import com.huateng.bomap.common.beans.ItreeBean;
import com.light.common.datatransform.IDataTransformer;
import com.light.common.file.FileInfo;
import com.light.common.messagequeue.IMessage;
import com.light.common.messagequeue.Message;
import com.light.common.messagequeue.MessageQueueHandler;
import com.light.common.tools.CommonOprFlags;
import com.light.common.tools.ICommonOprFlags;
import com.light.common.tools.IFactory;
import com.light.common.tools.busiproc.IBusinessHandler;
import com.light.common.tools.busiproc.IBusinessProcessor;
import com.light.common.xdr.IXdrFileOutput;
import com.light.common.xdr.XdrFileOutput;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.file.service.IFileProcInfo;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.common.entity.file.TSLEMessage;
import com.light.project.szafc.common.entity.message.FileInformMessage;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.light.project.szafc.message.tools.PackageTools;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;
import com.lightcomm.comm.server.client.ClientProcessor;
import com.lightcomm.comm.server.client.IClientHandler;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxnFileProcessor implements IBusinessProcessor {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TxnFileProcessor.class);
	
//	private IFileDataSrc dataSrc;
	
	private IFactory dataSrcFactory;
	
	private IFactory fileHandlerFactory;
	
	private IBusinessHandler fileInformHandler;
	
//	private ClientProcessor clientProcessor;
//
//	public ClientProcessor getClientProcessor() {
//		return clientProcessor;
//	}
//
//	public void setClientProcessor(ClientProcessor clientProcessor) {
//		this.clientProcessor = clientProcessor;
//	}

	public IFactory getFileHandlerFactory() {
		return fileHandlerFactory;
	}

	public void setFileHandlerFactory(IFactory fileHandlerFactory) {
		this.fileHandlerFactory = fileHandlerFactory;
	}

//	private IDataTransformer<OrderDetail, Object> transformer;
//	
//	private IXdrFileOutput xdrFileOutput = new XdrFileOutput();
	
	public IBusinessHandler getFileInformHandler() {
		return fileInformHandler;
	}

	public void setFileInformHandler(IBusinessHandler fileInformHandler) {
		this.fileInformHandler = fileInformHandler;
	}

	private ICommonOprFlags fileProcFlag = new CommonOprFlags();
	
//	protected ThreadLocal<List<Object>> singleFileDataList;
	
//	private IFileDataTools dataTools;

//	public IFileDataSrc getDataSrc() {
//		return dataSrc;
//	}
//
//	public void setDataSrc(IFileDataSrc dataSrc) {
//		this.dataSrc = dataSrc;
//	}

	public IFactory getDataSrcFactory() {
		return dataSrcFactory;
	}

	public void setDataSrcFactory(IFactory dataSrcFactory) {
		this.dataSrcFactory = dataSrcFactory;
	}

//	public IFileDataTools getDataTools() {
//		return dataTools;
//	}
//
//	public void setDataTools(IFileDataTools dataTools) {
//		this.dataTools = dataTools;
//	}

//	public IDataTransformer<OrderDetail, Object> getTransformer() {
//		return transformer;
//	}
//
//	public void setTransformer(
//			IDataTransformer<OrderDetail, Object> transformer) {
//		this.transformer = transformer;
//	}
//
//	public IXdrFileOutput getXdrFileOutput() {
//		return xdrFileOutput;
//	}
//
//	public void setXdrFileOutput(IXdrFileOutput xdrFileOutput) {
//		this.xdrFileOutput = xdrFileOutput;
//	}

	public ICommonOprFlags getFileProcFlag() {
		return fileProcFlag;
	}

	public void setFileProcFlag(ICommonOprFlags fileProcFlag) {
		this.fileProcFlag = fileProcFlag;
	}
	
	public void setFileProcFlag(Map fileProcFlag) {
		((CommonOprFlags)this.fileProcFlag).setFlagMap(fileProcFlag);;
	}

	@Override
	public Object doBusi() throws Exception {
		
		FileInfo fileInfo;
		
//		ICommonOprFlags dataFlag = new CommonOprFlags();
//		
//		dataFlag.putFlag(IFileDataSrc.DATA_SRC_KEY, fileProcFlag.getFlag(IFileDataSrc.DATA_SRC_KEY));
//		
		IFileDataSrc dataSrc = (IFileDataSrc)dataSrcFactory.getObject(fileProcFlag);
		
		IFileHandler fileHandler = (IFileHandler)fileHandlerFactory.getObject(fileProcFlag);
		
//		List tmpList = new LinkedList();
		
		while(true)
		{
			List dataList = dataSrc.fetchSrcData();
			
			
			
			if (dataList != null && dataList.size() > 0)
			{
			
				for (Object dataCell:dataList)
				{
					
					fileInfo = fileHandler.addOneData(dataCell);
					
					if (fileInfo != null)
					{
						dataSrc.doDataFeedback(fileInfo.getDataSrcList());
						fileInformHandler.doBusi(fileInfo);
					}
					
				}
			}
			else
			{
				break;
			}
		}
		fileInfo = fileHandler.currFileEnd();
		if (fileInfo != null)
		{
			dataSrc.doDataFeedback(fileInfo.getDataSrcList());
			fileInformHandler.doBusi(fileInfo);
		}
		
		return null;
	}
	
	
	
	
}
