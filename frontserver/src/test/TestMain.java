package test;

import java.util.LinkedList;

import com.light.common.ftp.DefaultFtpClient;
import com.light.common.ftp.IFtpClient;
import com.light.project.szafc.business.order.entity.OrderDetail;
import com.light.project.szafc.business.order.entity.TickInfo;
import com.light.project.szafc.file.IFileHandler;
import com.light.project.szafc.file.TxnSaleFileHandler;

public class TestMain {
	
	public static void main(String[] argv) throws Exception
	{
//		IFileHandler fileHandler = new TxnSaleFileHandler();
//		
//		OrderDetail order = new OrderDetail();
//		order.setStation("0102");
//		order.setEquipmentId("01020001");
//		order.setTickInfoList(new LinkedList());
//		
//		TickInfo e = new TickInfo();
//		e.setTickSN("123");
//		e.setTickLogicID("0038A11234509123");
//		e.setTAC("6A2B");
//		e.setRWTxnSeqNum("1234");
//		e.setTxnValue("300");
//		e.setTxnFee("0");
//		e.setTickTxnCnt("12");
//		e.setRWValueCnt("13");
//		e.setTickEffTime("2015-09-28 10:00:00");
//		e.setTickExpTime("2015-10-28 10:00:00");
//		e.setSAMId("123400");
//		e.setSAMSeqNum("123.0");
//		e.setTickPhyType("1");
//		e.setTickAppType("1");
//		e.setTestFlag("1");
//		
//		order.getTickInfoList().add(e);
//		
//		order.setTickInfoStr("[{tickSN=234, tickLogicID=1038A112A4509123, TAC=7AC1, RWTxnSeqNum=1239.0, txnValue=200.0, txnFee=0, tickTxnCnt=10.0, RWValueCnt=12.0, tickEffTime=2015-09-28T10:00:00Z, tickExpTime=2015-10-28T10:00:00Z, SAMId=00011512, SAMSeqNum=0.0, tickPhyType=1.0, tickAppType=1.0, testFlag=0.0}]");
//		
//
//		
//		try {
//			fileHandler.addOneData(order);
//			fileHandler.currFileEnd();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		DefaultFtpClient ftpClient = new DefaultFtpClient();
		ftpClient.setHost("127.0.0.1");
		ftpClient.setPort(21);
		ftpClient.setUser("nash");
		ftpClient.setPwd("nash123");
		ftpClient.downloadFile("ftpaudit", "d:/test", "new");
		
	}

}
