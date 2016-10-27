package com.light.common.com;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.light.common.messagequeue.IMessage;
import com.light.common.messagequeue.MessageQueueHandler;
import com.light.common.tools.busiproc.IBusinessProcessor;
//import com.light.project.szafc.common.entity.message.MessageHead;
//import com.light.project.szafc.message.ClientReqProcessor;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;
import com.lightcomm.comm.server.client.ClientProcessor;
import com.lightcomm.comm.server.client.IClientHandler;

public class ClientCenter implements IBusinessProcessor {
	

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientCenter.class);
	
	public static Boolean runingSwitch = false;
	
	private String outGoingQueueKey = "OUT_GOING_QUEUE";  
	
	public String getOutGoingQueueKey() {
		return outGoingQueueKey;
	}

	public void setOutGoingQueueKey(String outGoingQueueKey) {
		this.outGoingQueueKey = outGoingQueueKey;
	}


	public interface QueKeyExtractor
	{
		String getKey(Object inData);
		
		void prepareCtx(Object inData);
	}
	
	private QueKeyExtractor keyExtractor;
	
	public QueKeyExtractor getKeyExtractor() {
		return keyExtractor;
	}

	public void setKeyExtractor(QueKeyExtractor keyExtractor) {
		this.keyExtractor = keyExtractor;
	}

	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private int port;
	
	private ClientProcessor clientProcessor;


	public ClientProcessor getClientProcessor() {
		return this.clientProcessor;
	}

	public void setClientProcessor(ClientProcessor clientProcessor) {
		this.clientProcessor = clientProcessor;
	}

	
	@Override
	public Object doBusi() throws Exception {
		
		Boolean quitFlag = false;
		
		try
		{
			synchronized (runingSwitch) {
				
				if (runingSwitch == false)
				{
					runingSwitch = true;
					LOGGER.info("get job running right, thread id: " + Thread.currentThread().getId());
				}
				else
				{
					LOGGER.info("one job is still running quit this one, thread id: " + Thread.currentThread().getId());
					quitFlag = true;
					return null;
				}
			}
		
			while(true)
			{
				IClientHandler comClient = clientProcessor.getWorkingClient(new InetSocketAddress(ip,port));
			
			
				while(true)
				{
					try
					{
						String connectionStatus = (String)comClient.getClientStatus(IClientHandler.ClientConst.SESSION_CONNECTION_STATUS);
						if (connectionStatus != null && connectionStatus.equalsIgnoreCase(IClientHandler.ClientConst.SESSION_CONNECTION_STATUS_CONNECTED))
						{
							CommMessage mess = comClient.asynRecieve(false);
							if (mess != null)
							{
								String key = keyExtractor.getKey(mess);
								if (key != null)
								{
									MessageQueueHandler.send(key, mess);
								}
								
							}
							
							IMessage message = MessageQueueHandler.recieve(outGoingQueueKey);
							
							if (message != null)
							{
								comClient.asynSend((CommMessage)message.getMessageBody());
								keyExtractor.prepareCtx(message);
							}
						}
						else
						{
							LOGGER.info("connection fail, prepare to reconnect thread id: " + Thread.currentThread().getId());
							Thread.sleep(10000);
							break;
						}
						
						Thread.sleep(100);
					}
					catch (Exception e)
					{
						e.printStackTrace();
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (!quitFlag)
			{
				runingSwitch = false;
			}
		}
		
		return null;
	}

}
