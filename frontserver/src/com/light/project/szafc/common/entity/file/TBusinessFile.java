package com.light.project.szafc.common.entity.file;

import java.io.IOException;

import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.XdrAble;
import org.acplt.oncrpc.XdrDecodingStream;
import org.acplt.oncrpc.XdrEncodingStream;

import com.light.common.xdr.entity.TMD5;
import com.light.common.xdr.entity.TU16;
import com.light.common.xdr.entity.TU32;
import com.light.common.xdr.entity.TU8;

public class TBusinessFile implements XdrAble {
	
	private TU8 fileHeaderTag;
	private TSZAFCTime64 fileCreationTime;
	private TU8 lineID;
	private TU16 stationID;
	private TU32 fileSN;
	private TSZAFCTime64 fileCloseTime;
//	private TU8 transactionTag;
	private TSLEMessage sleMsg;
//	private TMD5 md5;

    public TU8 getFileHeaderTag() {
		return fileHeaderTag;
	}

	public void setFileHeaderTag(TU8 fileHeaderTag) {
		this.fileHeaderTag = fileHeaderTag;
	}

	public TSZAFCTime64 getFileCreationTime() {
		return fileCreationTime;
	}

	public void setFileCreationTime(TSZAFCTime64 fileCreationTime) {
		this.fileCreationTime = fileCreationTime;
	}

	public TU8 getLineID() {
		return lineID;
	}

	public void setLineID(TU8 lineID) {
		this.lineID = lineID;
	}

	public TU16 getStationID() {
		return stationID;
	}

	public void setStationID(TU16 stationID) {
		this.stationID = stationID;
	}

	public TU32 getFileSN() {
		return fileSN;
	}

	public void setFileSN(TU32 fileSN) {
		this.fileSN = fileSN;
	}

	public TSZAFCTime64 getFileCloseTime() {
		return fileCloseTime;
	}

	public void setFileCloseTime(TSZAFCTime64 fileCloseTime) {
		this.fileCloseTime = fileCloseTime;
	}

	public TSLEMessage getSleMsg() {
		return sleMsg;
	}

	public void setSleMsg(TSLEMessage sleMsg) {
		this.sleMsg = sleMsg;
	}

//	public TMD5 getMd5() {
//		return md5;
//	}
//
//	public void setMd5(TMD5 md5) {
//		this.md5 = md5;
//	}

	public TBusinessFile() {
    }

    public TBusinessFile(XdrDecodingStream xdr)
           throws OncRpcException, IOException {
        xdrDecode(xdr);
    }

    public void xdrEncode(XdrEncodingStream xdr)
           throws OncRpcException, IOException {
//        xdr.xdrEncodeInt(fileType);
    	fileHeaderTag.xdrEncode(xdr);
    	
    	fileCreationTime.xdrEncode(xdr);
    	
    	lineID.xdrEncode(xdr);
    	
    	stationID.xdrEncode(xdr);
    	
    	fileSN.xdrEncode(xdr);
    	
    	fileCloseTime.xdrEncode(xdr);
    	
//    	transactionTag.xdrEncode(xdr);
    	
        sleMsg.xdrEncode(xdr);
        
//        md5.xdrEncode(xdr);
    }

    public void xdrDecode(XdrDecodingStream xdr)
           throws OncRpcException, IOException {
    	fileHeaderTag = new TU8(xdr);
    	
    	fileCreationTime = new TSZAFCTime64(xdr);
    	
    	lineID = new TU8(xdr);
    	
    	stationID = new TU16(xdr);
    	
    	fileSN = new TU32(xdr);
    	
    	fileCloseTime = new TSZAFCTime64(xdr);
    	
//    	transactionTag = new TU8(xdr);
    	
        sleMsg = new TSLEMessage(xdr);
        
//        md5 = new TMD5(xdr);
    }

}
