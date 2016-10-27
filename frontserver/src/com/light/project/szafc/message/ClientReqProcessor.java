package com.light.project.szafc.message;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.light.common.messagequeue.IMessage;
import com.light.common.messagequeue.MessageQueueHandler;
import com.light.common.tools.busiproc.IBusinessHandler;
import com.light.common.tools.busiproc.IBusinessProcessor;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.light.project.szafc.common.entity.message.ProcResultData;
import com.light.project.szafc.file.TxnFileProcessor;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;
import com.lightcomm.comm.server.client.IClientHandler;

public class ClientReqProcessor implements IBusinessProcessor {
	
	public static Boolean runingSwitch = false;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientReqProcessor.class);

	private Map<String, IBusinessHandler> handlerMap = new HashMap();
	
	public Map<String, IBusinessHandler> getHandlerMap() {
		return handlerMap;
	}

	public void setHandlerMap(Map<String, IBusinessHandler> handlerMap) {
		this.handlerMap = handlerMap;
	}

	@Override
	public Object doBusi() throws Exception {
		
		try
		{
			synchronized (runingSwitch) {
				
				if (runingSwitch == false)
				{
					runingSwitch = true;
				}
				else
				{
					return null;
				}
			}
		
			while(true)
			{
				IMessage qMessage = MessageQueueHandler.recieve("REQ_PROC_QUEUE", true);
				CommMessage commMess = (CommMessage) qMessage.getMessageBody();
				
				MessageHead messHead = (MessageHead)((HeadBodyMessage)commMess.getMessageObject()).getHeadMessage();
				
				
				IBusinessHandler handler = handlerMap.get(messHead.getMessageType());
				ProcResultData ret = null;
				if (handler != null)
				{
					ret = (ProcResultData)handler.doBusi(commMess);
				}
				else
				{
					LOGGER.info("recieve unuse message, message type is ["+ messHead.getMessageType() +"]");
				}
				
				if (ret != null)
				{
					CommMessage retMess = new CommMessage();
					HeadBodyMessage data = new HeadBodyMessage();
					retMess.setMessageObject(data);
					data.setHeadMessage(messHead);
					messHead.setRepCode(ret.getRepCode());
					messHead.setReqFlag("01");
					
					data.setBodyMessage(ret.getRepBodyObj());
					
					MessageQueueHandler.send("OUT_GOING_QUEUE", retMess);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			runingSwitch = false;
		}
		
		return null;
	}

}
