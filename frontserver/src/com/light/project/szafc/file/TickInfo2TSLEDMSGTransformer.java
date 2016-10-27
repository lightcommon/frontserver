package com.light.project.szafc.file;

import java.util.Date;

import com.light.common.CommonTools;
import com.light.common.datatransform.AbstractDefaultTransformer;
import com.light.common.datatransform.IDataTransformer;
import com.light.common.tools.CommonUtils;
import com.light.common.tools.ICommonOprFlags;
import com.light.common.xdr.entity.TU16;
import com.light.common.xdr.entity.TU32;
import com.light.common.xdr.entity.TU64;
import com.light.common.xdr.entity.TU8;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.business.order.entity.TickInfo;
import com.light.project.szafc.common.BusiTools;
import com.light.project.szafc.common.entity.file.TSJTSale;
import com.light.project.szafc.common.entity.file.TSLESJTUD;
import com.light.project.szafc.common.entity.file.TSLEUDMSG;
import com.light.project.szafc.common.entity.file.TSZAFCTime64;
import com.light.project.szafc.common.entity.file.TTicketComm;
import com.light.project.szafc.common.entity.file.TUDComm;

public class TickInfo2TSLEDMSGTransformer extends AbstractDefaultTransformer<TickInfo, TSLEUDMSG> {

	

	@Override
	public TSLEUDMSG doTransform(TickInfo inData, ICommonOprFlags oprFlags)
			throws Exception {
		TSLEUDMSG sleData = new TSLEUDMSG();
		
		sleData.setSJTUDData(new TSLESJTUD());
		sleData.getSJTUDData().setSJTSale(new TSJTSale());
		sleData.getSJTUDData().setTicketComm(new TTicketComm());
		
		sleData.setUDComm(new TUDComm());
		
		doTransform(inData, sleData, oprFlags);
		
		return sleData;
	}

	@Override
	public void doTransform(TickInfo src, TSLEUDMSG dst,
			ICommonOprFlags oprFlags) throws Exception {
		
//		dst.setUDSN(new TU32(Integer.valueOf(src.getRWTxnSeqNum().split("\\.")[0])));
		dst.setUDSN(new TU32((Integer)BusiTools.getNumFromStr(src.getRWTxnSeqNum().split("\\.")[0], null, "int")));
		
//		dst.setTAC(new TU32(Integer.valueOf(src.getTAC(), 16)));
		dst.setTAC(new TU32((Integer)BusiTools.getNumFromStr(src.getTAC(), 16, "int")));
		
		dst.setBusinessDay(new TU16((short)CommonTools.getDayDiff("1970-01-01", src.getOrderDetail().getBusinessDay(), "yyyy-MM-dd")));
		
		
		dst.getSJTUDData().setTransactionType(new TU8((byte)1));
		
		dst.getSJTUDData().getTicketComm().setTicketType(new TU8((byte)1));
		
		TU64 logicId = new TU64();
		String logicIdStr = CommonUtils.padString(src.getTickLogicID(), "FIXLEN", "L", "0", (long)16, true);;
//		logicId.setHI(new TU32(Integer.valueOf(src.getTickLogicID().substring(0,8), 16)));
//		logicId.setLO(new TU32(Integer.valueOf(src.getTickLogicID().substring(8,16), 16)));
		logicId.setHI(new TU32((Integer)BusiTools.getNumFromStr(logicIdStr.substring(0,8), 16, "int")));
		logicId.setLO(new TU32((Integer)BusiTools.getNumFromStr(logicIdStr.substring(8,16), 16, "int")));
		dst.getSJTUDData().getTicketComm().setTicketlogicalID(logicId);
		
		TU64 phyId = new TU64();
		String phyIdStr = CommonUtils.padString(src.getTickSN(), "FIXLEN", "L", "0", (long)16, true);;
		phyId.setHI(new TU32((Integer)BusiTools.getNumFromStr(phyIdStr.substring(0,8), 16, "int")));
		phyId.setLO(new TU32((Integer)BusiTools.getNumFromStr(phyIdStr.substring(8,16), 16, "int")));
		dst.getSJTUDData().getTicketComm().setTicketPhyID(phyId);
		
//		dst.getSJTUDData().getTicketComm().setTestFlag(new TU8(Byte.valueOf(src.getTestFlag().split("\\.")[0])));
		dst.getSJTUDData().getTicketComm().setTestFlag(new TU8((Byte)BusiTools.getNumFromStr(src.getTestFlag().split("\\.")[0], null, "byte")));
		
//		dst.getSJTUDData().getTicketComm().setTicketSAMID(new TU32(Integer.valueOf(src.getSAMId(), 16)));
		dst.getSJTUDData().getTicketComm().setTicketSAMID(new TU32((Integer)BusiTools.getNumFromStr(src.getSAMId(), 16, "int")));
		
		dst.getSJTUDData().getTicketComm().setTicketSN(new TU32((Integer)BusiTools.getNumFromStr(src.getTickTxnCnt(), 10, "int")));
		
		dst.getSJTUDData().getTicketComm().setePurseSN(new TU16((short)0));
		
		short startValidDate = (short)CommonTools.getDayDiff("1970-01-01", src.getTickEffTime(), "yyyy-MM-dd");
		dst.getSJTUDData().getTicketComm().setStartValidDate(new TU16(startValidDate));
		
		short endValidDate = (short)CommonTools.getDayDiff("1970-01-01", src.getTickExpTime(), "yyyy-MM-dd");
		dst.getSJTUDData().getTicketComm().setEndValidDate(new TU16(endValidDate));
		
		
		dst.getSJTUDData().getSJTSale().setOperatorID(new TU32((Integer)BusiTools.getNumFromStr(src.getOrderDetail().getOperationId(), 16, "int")));
		
		dst.getSJTUDData().getSJTSale().setBOMShiftID(new TU32(0));
		
//		dst.getSJTUDData().getSJTSale().setTransactionValue(new TU32(Integer.valueOf(src.getTxnValue())));
		dst.getSJTUDData().getSJTSale().setTransactionValue(new TU32((Integer)BusiTools.getNumFromStr(src.getTxnValue(), null, null)));
		
		dst.getSJTUDData().getSJTSale().setMediaType(new TU32((Integer)BusiTools.getNumFromStr(src.getMediatype(), 16, "int")));
		
		dst.getSJTUDData().getSJTSale().setPaymentmeans(new TU8((byte)1));
		
		dst.getSJTUDData().getSJTSale().setDuration(new TU16((short)(dst.getSJTUDData().getTicketComm().getEndValidDate().getValue() - dst.getSJTUDData().getTicketComm().getStartValidDate().getValue())));
		
		
		
		
		
		dst.getUDComm().setTransactionType(new TU8((byte)1));
		
		
		
		dst.getUDComm().setTransactionDateTime(BusiTools.getDateGMT(src.getTickEffTime(),"yyyy-MM-dd HH-mm-ss"));
		int gmtOffset = 0;
		/*test only*/
		gmtOffset =  8 * 3600;
		TSZAFCTime64 time = dst.getUDComm().getTransactionDateTime();
		time.setTime_LO(new TU32(time.getTime_LO().getValue() + gmtOffset));
		/**/
		
		
//		dst.getUDComm().setTACSAMID(new TU32(Integer.valueOf(src.getSAMId(),16)));
		dst.getUDComm().setTACSAMID(new TU32((Integer)BusiTools.getNumFromStr(src.getSAMId(), 16, "int")));
		
//		dst.getUDComm().
		
//		dst.getUDComm().是
		
//		dst.getUDComm().setDeviceID(new TU32(Integer.valueOf(src.getOrderDetail().getEquipmentId(),16)));
		dst.getUDComm().setDeviceID(new TU32((Integer)BusiTools.getNumFromStr(src.getOrderDetail().getEquipmentId(), 16, "int")));
		
		dst.getUDComm().setModeCode(new TU8((Byte)BusiTools.getNumFromStr(src.getOrderDetail().getModeCode(), 16, "byte")));
		
//		dst.getUDComm().setSAMUDSN(new TU32(Integer.valueOf(src.getSAMSeqNum())));
		dst.getUDComm().setSAMUDSN(new TU32((Integer)BusiTools.getNumFromStr(src.getSAMSeqNum(), null, null)));
		
	}

	

}
