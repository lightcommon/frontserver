package com.light.project.szafc.file;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

import com.light.common.file.FileInfo;
import com.light.common.ftp.IFtpClient;
import com.light.common.messagequeue.IMessage;
import com.light.common.messagequeue.Message;
import com.light.common.messagequeue.MessageQueueHandler;
import com.light.common.tools.busiproc.IBusinessHandler;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.file.service.IFileProcInfo;
import com.light.project.szafc.common.BusiTools;
import com.light.project.szafc.common.entity.message.FileInformMessage;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.light.project.szafc.message.tools.PackageTools;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;

public class FileInformHandler implements IBusinessHandler {
	
	private String busiCode = "01";
	
	private IFileProcInfo fileProcHandler;
	
	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public IFileProcInfo getFileProcHandler() {
		return fileProcHandler;
	}

	public void setFileProcHandler(IFileProcInfo fileProcHandler) {
		this.fileProcHandler = fileProcHandler;
	}
	

	private IFtpClient transferFtpClient;


	public IFtpClient getTransferFtpClient() {
		return transferFtpClient;
	}

	public void setTransferFtpClient(IFtpClient transferFtpClient) {
		this.transferFtpClient = transferFtpClient;
	}
	
	
	private String transferRemotePath = "test";
	
	public String getTransferRemotePath() {
		return transferRemotePath;
	}

	public void setTransferRemotePath(String transferRemotePath) {
		this.transferRemotePath = transferRemotePath;
	}

	@Override
	public Object doBusi(Object data) throws Exception {
		
		if (data != null)
		{
			
			FileInfo fileInfo = (FileInfo) data;
			
			CommMessage reqMessage = getFileInformMessage(fileInfo);
			
//			IClientHandler comClient = clientProcessor.getWorkingClient(new InetSocketAddress(reqMessage.getRemoteip(),reqMessage.getRemoteport()));
			
			/*transfer file to another ftp*/
			if (transferFtpClient != null && transferRemotePath != null)
			{
				transferFtpClient.uploadFile(BusiTools.trimFileNameFromPath(fileInfo.getFileUrl(), fileInfo.getFileName()), transferRemotePath, fileInfo.getFileName());
			}
			/*transfer file to another ftp end*/
			
			CommMessage repMessage;
			try {
				IMessage qMessage = new Message();
				qMessage.setMessageBody(reqMessage);
				qMessage.setReplyQueueId("REP_QUEUE");
				System.out.println("send message :");
				MessageQueueHandler.send("OUT_GOING_QUEUE", qMessage);
				IMessage message = MessageQueueHandler.recieve("REP_QUEUE", true);
				
				if (message != null)
				{
					repMessage = (CommMessage)message.getMessageBody();
					System.out.println("message :" + repMessage);
				}
				else
				{
					repMessage = null;
					System.out.println("message : null");
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				throw e;
			}
			
			String repCode = null;
			if (repMessage != null)
			{
				MessageHead mHead =  (MessageHead)((HeadBodyMessage)repMessage.getMessageObject()).getHeadMessage();
				repCode = mHead.getRepCode();
			}
			else
			{
				repCode = "99";
			}
			
			FileProcInfo fileProcInfo = new FileProcInfo();
			fileProcInfo.setFileBusiCode(busiCode);
			fileProcInfo.setFileCurrPath(fileInfo.getFileUrl());
			fileProcInfo.setFileExtRep(repCode);
			fileProcInfo.setFileName(fileInfo.getFileName());
			fileProcInfo.setFileProcStatus("P" + repCode);
			
			
			SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat dateFmt=new SimpleDateFormat("yyyyMMdd");
			Date now = new Date();
//			informMess.setStartTime(myFmt1.format(new Date()));
			fileProcInfo.setFileProcTime(myFmt1.format(now));
			
			fileProcInfo.setFileType("01");
			fileProcInfo.setLastTime(myFmt1.format(now));
			fileProcInfo.setBusinessday(dateFmt.format(now));
			fileProcInfo.setFileProcTimes(0);
			fileProcInfo.setFileDigest(Hex.encodeHexString(fileInfo.getFileDigest()));
			
			
			fileProcHandler.processFileProcInfo(fileProcInfo);
			
		}
		return null;
	}
	
	private CommMessage getFileInformMessage(FileInfo fileInfo) throws Exception
	{
		Map<String, String> oprFlag = new HashMap<String, String>();
		oprFlag.put(PackageTools.OPRFLAG_REQ_REP_FLAG_KEY, PackageTools.OPRFLAG_REQ_REP_FLAG_REQ);
		oprFlag.put(PackageTools.OPRFLAG_MESSAGE_TYPE_KEY, "9000");
		oprFlag.put(PackageTools.OPRFLAG_NEED_SOCKETINFO_KEY, "TRUE");
		
		
		CommMessage message = PackageTools.getBaseMessage(oprFlag);
		
		FileInformMessage informMess = new FileInformMessage();
		// TODO field check
		informMess.setFileName(fileInfo.getFileName());
		informMess.setFileType(fileInfo.getFileType());
		informMess.setMD5Sum(Hex.encodeHexString(fileInfo.getFileDigest()));
		informMess.setSenderId(((MessageHead)((HeadBodyMessage)message.getMessageObject()).getHeadMessage()).getSenderId());
		SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMddHHmmss");
		informMess.setStartTime(myFmt1.format(new Date()));		
		((HeadBodyMessage)message.getMessageObject()).setBodyMessage(informMess);
		
		
		
		return message;
	}

}
