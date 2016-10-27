package com.light.project.szafc.common.entity.message;

public class FileInformMessage {
	
	private String startTime;
	
	private String senderId;
	
	private String fileType;
	
	private String fileName;
	
	private String MD5Sum;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMD5Sum() {
		return MD5Sum;
	}

	public void setMD5Sum(String mD5Sum) {
		MD5Sum = mD5Sum;
	}
	
}
