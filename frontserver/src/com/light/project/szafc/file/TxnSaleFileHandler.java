package com.light.project.szafc.file;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.acplt.oncrpc.OncRpcException;

import com.light.common.CommonTools;
import com.light.common.datatransform.IDataTransformer;
import com.light.common.file.FileInfo;
import com.light.common.xdr.IXdrFileOutput;
import com.light.common.xdr.XdrFileOutput;
import com.light.common.xdr.entity.TU16;
import com.light.common.xdr.entity.TU32;
import com.light.common.xdr.entity.TU8;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.business.order.entity.TickInfo;
import com.light.project.szafc.common.CommonConst;
import com.light.project.szafc.common.BusiTools;
import com.light.project.szafc.common.entity.file.TBusinessFile;
import com.light.project.szafc.common.entity.file.TSLEMessage;
import com.light.project.szafc.common.entity.file.TSLEUDMSG;

public class TxnSaleFileHandler implements IFileHandler {
	
	private List<TSLEUDMSG> singleFileDataList;
	
	private IDataTransformer<TickInfo, TSLEUDMSG> transformer = new TickInfo2TSLEDMSGTransformer();;
	
	private IXdrFileOutput xdrFileOutput = new XdrFileOutput();
	
	private List<OrderDetail> currFileSrcDataList;
	
	private String currStation = null;

	private String fileType = "01";
	
	private String lineId = "02";
	
	private String fileSN;
	private String fileCreateTimeStr;
	
	private String fileDir = "d:/server/test/CCHS";

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public IDataTransformer<TickInfo, TSLEUDMSG> getTransformer() {
		return transformer;
	}

	public void setTransformer(IDataTransformer<TickInfo, TSLEUDMSG> transformer) {
		this.transformer = transformer;
	}

	@Override
	public FileInfo addOneData(Object data) throws Exception {
		if (data instanceof OrderDetail)
		{
			OrderDetail orderData = (OrderDetail) data;
			
			String stationId = orderData.getStation();
			if (stationId == null)
			{
				stationId = "0001";
			}
			
			if (currStation == null)
			{
				initNewFileCtx(stationId);
			}
			
			if (currStation.equals(stationId))
			{
				doAdd(orderData);
			}
			else
			{
				
				FileInfo file = endCurrFile();
				
				initNewFileCtx(stationId);
				doAdd(orderData);
				
				return file;
			}
			
			return null;
		}
		else
		{
			throw new IllegalArgumentException();
		}

	}
	
	private void initNewFileCtx(String newStation)
	{
		if (newStation == null)
		{
			currStation = "0001";
		}
		else
		{
			currStation = newStation;
		}
		singleFileDataList = new LinkedList();
		currFileSrcDataList = new LinkedList();
	}
	
	private void initFileConfig()
	{
		fileSN = (String) BusiTools.getCtxAccessor(BusiTools.GLOBAL_CTX).getData(CommonConst.SeqKey.SEQ_OUTFILE_KEY);
		fileCreateTimeStr = CommonTools.getCurrTimeString("yyyyMMddHHmmss");
	}
	
	private void doAdd(OrderDetail data) throws Exception
	{
		List<TickInfo> tList = data.getTickInfoList();
		
		for(TickInfo tickInfo: tList)
		{
			if (tickInfo == null)
			{
				continue;
			}
			tickInfo.setOrderDetail(data);
			TSLEUDMSG sleUDMessage = transformer.doTransform(tickInfo);
			sleUDMessage.getUDComm().setLineID(new TU8(Byte.valueOf(lineId, 16)));
			sleUDMessage.getUDComm().setStationID(new TU16(Short.parseShort(currStation, 16)));
			singleFileDataList.add(sleUDMessage);
			currFileSrcDataList.add(data);
		}
		
		
	}
	
	private TBusinessFile prepareHead()
	{
		TBusinessFile fileXdr = new TBusinessFile();
		
		fileXdr.setFileHeaderTag(new TU8((byte)1));
		
		fileXdr.setFileCreationTime(BusiTools.getCurrTimeGMT());
		
//		fileXdr.setLineID(new TU8(Byte.valueOf(lineId, 16)));
		fileXdr.setLineID(new TU8((Byte)BusiTools.getNumFromStr(lineId, 16, "byte")));
		
		
//		fileXdr.setStationID(new TU16(Short.parseShort(currStation, 16)));
		fileXdr.setStationID(new TU16((Short)BusiTools.getNumFromStr(currStation, 16, "short")));
		
//		fileXdr.setFileSN(new TU32(Integer.valueOf(fileSN)));
		fileXdr.setFileSN(new TU32((Integer)BusiTools.getNumFromStr(fileSN, null, "int")));
		
		fileXdr.setFileCloseTime(fileXdr.getFileCreationTime());
		
		fileXdr.setSleMsg(new TSLEMessage());
		
		return fileXdr;
	}
	
	private String getFilePath()
	{
		return getFileDir() + "/" + getFileName();
	}
	
	private String getFileName()
	{
		return "S" + currStation + "0000." + fileCreateTimeStr + "." + String.format("%05d", Integer.valueOf(fileSN));
	}
	
	private FileInfo endCurrFile() throws OncRpcException, IOException
	{
		if (currStation == null)
		{
			return null;
		}
		
		FileInfo file = new FileInfo();
		
		initFileConfig();
		
		Object[] dataArraySrc = singleFileDataList.toArray();
		TSLEUDMSG[] dataArray = new TSLEUDMSG[singleFileDataList.size()];
		System.arraycopy(dataArraySrc, 0, dataArray, 0, singleFileDataList.size());
		
		TBusinessFile fileXdr = prepareHead();
		fileXdr.getSleMsg().setTransactionTag(1);
		fileXdr.getSleMsg().setUD(dataArray);
		
		String filePath = getFilePath();
		file.setFileUrl(filePath);
		
		file.setFileType(this.fileType);
		
		
		
		xdrFileOutput.startProcess(filePath);
		xdrFileOutput.processXdrAble(fileXdr);
		xdrFileOutput.endProcess();
		
		file.setFileDigest(xdrFileOutput.getSecurityDigestInfo());
		
		file.setFileName(getFileName());
		
		List fileDataSrcList = new LinkedList();
		for(OrderDetail cell : currFileSrcDataList)
		{
//			cell.setRefResourceId(file.getFileName());
			OrderDetail srcData = new OrderDetail();
			srcData.setRefResourceId(file.getFileName());
			srcData.setProcSessionId(cell.getProcSessionId());
			srcData.setRecId(cell.getRecId());
			fileDataSrcList.add(srcData);
		}
		
		file.setDataSrcList(fileDataSrcList);
		
		
		
		
		
		return file;
	}

	@Override
	public FileInfo currFileEnd() throws Exception {
		FileInfo file = endCurrFile();
		return file;
	}

}
