package com.light.project.szafc.file;

import java.io.FileInputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.light.common.file.FileInfo;
import com.light.common.tools.busiproc.IBusinessHandler;
import com.light.common.xml.IXMLHandler;
import com.light.common.xml.XMLHandler;
import com.light.project.szafc.business.file.entity.FileProcInfo;
import com.light.project.szafc.business.file.service.IFileProcInfo;

public class FtpAuditHandler implements IBusinessHandler {
	
	private IXMLHandler xmlHandler = new XMLHandler();

	private IFileProcInfo fileProcInfoHandler;
	
	public IXMLHandler getXmlHandler() {
		return xmlHandler;
	}

	public void setXmlHandler(IXMLHandler xmlHandler) {
		this.xmlHandler = xmlHandler;
	}

	public IFileProcInfo getFileProcInfoHandler() {
		return fileProcInfoHandler;
	}

	public void setFileProcInfoHandler(IFileProcInfo fileProcInfoHandler) {
		this.fileProcInfoHandler = fileProcInfoHandler;
	}

	@Override
	public Object doBusi(Object data) throws Exception {
		FileInfo file = (FileInfo)data;
		FileInputStream fileStream = new FileInputStream(file.getFileUrl());
		Document doc = xmlHandler.parse(fileStream);
		
		Element root = doc.getRootElement();
		
		Element audit = root.getChild("AuditDetail");
		Element sjtud = audit.getChild("SJTUD");
		List<Element> succFileList = sjtud.getChildren("FileInfo");
		for(Element cell : succFileList)
		{
			FileProcInfo fileProc = new FileProcInfo();
			fileProc.setFileBusiCode("01");
			fileProc.setFileName(cell.getAttributeValue("FileName"));
			fileProc.setFileProcStatus("C00");
			fileProcInfoHandler.updateFileProcInfo(fileProc);
		}
		
		Element errFile = audit.getChild("ErrFile");
		List<Element> errFileList = errFile.getChildren("FileInfo");
		for(Element cell : errFileList)
		{
			FileProcInfo fileProc = new FileProcInfo();
			fileProc.setFileBusiCode("01");
			fileProc.setFileName(cell.getAttributeValue("FileName"));
			fileProc.setFileProcStatus("C09");
			fileProcInfoHandler.updateFileProcInfo(fileProc);
		}
		
		return null;
	}

}
