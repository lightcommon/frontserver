package com.light.project.szafc.common.entity.file;

import java.io.IOException;

import org.acplt.oncrpc.OncRpcException;
import org.acplt.oncrpc.XdrAble;
import org.acplt.oncrpc.XdrDecodingStream;
import org.acplt.oncrpc.XdrEncodingStream;

import com.light.common.xdr.entity.TU16;
import com.light.common.xdr.entity.TU32;

public class TSZAFCTime64 implements XdrAble {
	
	private TU32                   Time_HI;
	private TU32                   Time_LO;
	
	public TU32 getTime_HI() {
		return Time_HI;
	}
	public void setTime_HI(TU32 time_HI) {
		Time_HI = time_HI;
	}
	public TU32 getTime_LO() {
		return Time_LO;
	}
	public void setTime_LO(TU32 time_LO) {
		Time_LO = time_LO;
	}
	
	public TSZAFCTime64() {
	}

    public TSZAFCTime64(XdrDecodingStream xdr)
           throws OncRpcException, IOException {
        xdrDecode(xdr);
    }
	
	@Override
	public void xdrDecode(XdrDecodingStream paramXdrDecodingStream)
			throws OncRpcException, IOException {
		
		Time_HI = new TU32(paramXdrDecodingStream);
		
		Time_LO = new TU32(paramXdrDecodingStream);
	}
	@Override
	public void xdrEncode(XdrEncodingStream paramXdrEncodingStream)
			throws OncRpcException, IOException {
		
		Time_HI.xdrEncode(paramXdrEncodingStream);
		Time_LO.xdrEncode(paramXdrEncodingStream);
	}


}
