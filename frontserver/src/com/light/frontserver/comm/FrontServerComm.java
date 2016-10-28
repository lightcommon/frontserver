package com.light.frontserver.comm;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.light.common.tools.busiproc.IBusinessHandler;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.server.client.IClientHandler;
import com.lightcomm.comm.server.client.IClientProcessor;

public class FrontServerComm extends IoHandlerAdapter {
	
//	private ExchangeConnectorListener exchangeListener = null;
//	
//	private DataTransformer dataTransformer = 
//			new TransformCommonProcess(new Exchange2CommTransformer(), CommMessage.class, ExchangeData.class);
//	
	
	private String defaultDestIp;
	
	private int defaultDestPort;
	
	private InetSocketAddress defaultAddress;
	
	public InetSocketAddress getDefaultAddress() {
		
		if (defaultAddress == null)
		{
			defaultAddress = new InetSocketAddress(defaultDestIp, defaultDestPort);
		}
		
		return defaultAddress;
	}


	private IClientProcessor clientProcessor;
	
	private IBusinessHandler businessHandler;
	

	public String getDefaultDestIp() {
		return defaultDestIp;
	}


	public void setDefaultDestIp(String defaultDestIp) {
		this.defaultDestIp = defaultDestIp;
	}


	public int getDefaultDestPort() {
		return defaultDestPort;
	}


	public void setDefaultDestPort(int defaultDestPort) {
		this.defaultDestPort = defaultDestPort;
	}


	public IClientProcessor getClientProcessor() {
		return clientProcessor;
	}


	public void setClientProcessor(IClientProcessor clientProcessor) {
		this.clientProcessor = clientProcessor;
	}


	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		List dataList = new LinkedList();
		
		if (message instanceof CommMessage)
		{
			CommMessage commMessage = (CommMessage)message;
			dataList.add(commMessage.getMessageObject());
			
			IClientHandler comClient = clientProcessor.getWorkingClient(getDefaultAddress());
			CommMessage retMessage = comClient.synSend(commMessage);
			dataList.add(retMessage.getMessageObject());
			
			session.write(retMessage);
			
			businessHandler.doBusi(dataList);
		}
		else
		{
			throw new Exception("Invalid data type, excpect CommMessage, which is ["+ message.getClass().getName() +"]");
		}
	}

//	public ExchangeConnectorListener getExchangeListener() {
//		return exchangeListener;
//	}
//
//	public void setExchangeListener(ExchangeConnectorListener exchangeListener) {
//		this.exchangeListener = exchangeListener;
//	}
	
	
	
	

}
