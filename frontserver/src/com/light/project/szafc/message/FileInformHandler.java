package com.light.project.szafc.message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.light.common.file.FileInfo;
import com.light.common.ftp.IFtpClient;
import com.light.common.tools.busiproc.IBusinessHandler;
import com.light.project.szafc.common.entity.message.FileInformMessage;
import com.light.project.szafc.common.entity.message.ProcResultData;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;

public class FileInformHandler implements IBusinessHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileInformHandler.class);
	
	private Map<String, IBusinessHandler> handlerMap = new HashMap();
	
	private Map<String, String> pathMap = new HashMap();
	
	public Map<String, IBusinessHandler> getHandlerMap() {
		return handlerMap;
	}

	public void setHandlerMap(Map<String, IBusinessHandler> handlerMap) {
		this.handlerMap = handlerMap;
	}
	
	public Map<String, String> getPathMap() {
		return pathMap;
	}

	public void setPathMap(Map<String, String> pathMap) {
		this.pathMap = pathMap;
	}

	public IFtpClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(IFtpClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	private IFtpClient ftpClient;

	@Override
	public Object doBusi(Object data) throws Exception {
		CommMessage commMess = (CommMessage) data;
		
		FileInformMessage fileInform = (FileInformMessage)((HeadBodyMessage)commMess.getMessageObject()).getBodyMessage();
		
		/*test only*/
		if (fileInform.getSenderId().equalsIgnoreCase("00000100"))
		{
			ProcResultData ret = new ProcResultData();
			ret.setRepCode("00");
			return ret;
		}
		/**/
		
		String path = pathMap.get(fileInform.getFileType());
		if (path != null)
		{
			String[] pathArray = path.split(",");
			String localDir = pathArray[0];
			String remoteDir = pathArray[1];
//			String remotePath = remoteDir + "/" + fileInform.getFileName();
			ftpClient.downloadFile(remoteDir, localDir, fileInform.getFileName());
			
			FileInfo file = new FileInfo();
			file.setFileUrl(localDir + "/" + fileInform.getFileName());
			List srcList = new LinkedList();
			srcList.add(commMess);
			file.setFileName(fileInform.getFileName());
			file.setDataSrcList(srcList);
			
			IBusinessHandler handler = handlerMap.get(fileInform.getFileType());
			if (handler != null)
			{
				return handler.doBusi(file);
			}
			else
			{
				LOGGER.info("unsurpport file inform type, path is ["+ path +"]" + ", file type is [" + fileInform.getFileType() + "]");
				return null;
			}
			
		}
		else
		{
			LOGGER.info("file path is not correct configed, path is ["+ fileInform.getFileType() +"]");
			return null;
		}
		
	}

}
