package test;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.light.common.messagequeue.MessageQueueHandler;
import com.light.common.tools.busiproc.IBusinessProcessor;
import com.light.project.szafc.common.entity.message.FileInformMessage;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.light.project.szafc.message.tools.PackageTools;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;


import com.lightcomm.comm.server.client.IClientHandler;
import com.lightcomm.comm.server.client.IClientProcessor;
import com.lightcomm.common.HuatengServerHandler;
public class SpringMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	/**/
		ConfigurableApplicationContext ctx = getApplicationContext();
	        System.out.println("Begin ...");
	    
	        IBusinessProcessor proccessor = (IBusinessProcessor)ctx.getBean("txnSaleFileProcessor");
	        
	        List<String> idList = new ArrayList();
	              
	        idList.add("REQ_PROC_QUEUE");
	        idList.add("OUT_GOING_QUEUE");
	        idList.add("REP_QUEUE");
	        
	        MessageQueueHandler.initQueue(idList);
	        
	        proccessor.doBusi();
	        
	        
//	        IClientProcessor client = (IClientProcessor)ctx.getBean("clientProcessor");
//	        
//	        IClientHandler comClient =  client.getWorkingClient(new InetSocketAddress("127.0.0.1",60000));
//	        
//	        
//	        Map<String, String> oprFlag = new HashMap<String, String>();
//			oprFlag.put(PackageTools.OPRFLAG_REQ_REP_FLAG_KEY, PackageTools.OPRFLAG_REQ_REP_FLAG_REQ);
//			oprFlag.put(PackageTools.OPRFLAG_MESSAGE_TYPE_KEY, "9000");
//			oprFlag.put(PackageTools.OPRFLAG_NEED_SOCKETINFO_KEY, "TRUE");
//			CommMessage message = PackageTools.getBaseMessage(oprFlag);
//			
//			FileInformMessage informMess = new FileInformMessage();
//			// TODO field check
//			informMess.setFileName("aaa");
//			informMess.setFileType("01");
//			informMess.setMD5Sum("1234567890abcdef1234567890abcdef");
//			informMess.setSenderId(((MessageHead)((HeadBodyMessage)message.getMessageObject()).getHeadMessage()).getReceiverId());
//			SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMddHHmmss");
//			informMess.setStartTime(myFmt1.format(new Date()));		
//			((HeadBodyMessage)message.getMessageObject()).setBodyMessage(informMess);
			
	        
//	        CommMessage message = new CommMessage();
//	        byte[] messagbody = new byte[2];
//	        messagbody[0] = 0x30;
//	        messagbody[1] = 0x39;
//	        message.setMessagbody(messagbody);
//	        message.setLength(2);
//			comClient.asynSend(message);
//			
//			while(true)
//			{
//				
//				CommMessage mess = comClient.asynRecieve(false);
//				if (mess != null)
//				{
//					System.out.println("!!!!!!!!!!!recieve!!!!!!!!!!!!");
//				}
//				else
//				{
//					System.out.println("not recieve");
//				}
//				
//				Thread.sleep(3000);
//			}
    }

    public static ConfigurableApplicationContext getApplicationContext() {
    	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
    	ctx.getEnvironment().setActiveProfiles("production");
    	ctx.load("applicationContext.xml");
    	ctx.refresh();
    	
//    	ctx = new GenericXmlApplicationContext();
    	
        return ctx;
    }
}