package com.light.project.szafc.message;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.light.common.messagequeue.MessageQueueHandler;
import com.lightcomm.comm.server.client.IClientProcessor;
import com.lightcomm.common.constdef.LightConstDef.ErrorCode.ComErrorCode;
import com.lightcomm.common.exception.LightCommonException;

public class ComClientHandler extends IoHandlerAdapter{
	
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		
		MessageQueueHandler.send("REP_QUEUE", message);
	}

	private IClientProcessor clientProcessor = null;

//	@Override
//	public void sessionIdle(IoSession session, IdleStatus status)
//			throws Exception {
//		
//		super.sessionIdle(session, status);
//		
//		if (status.equals(IdleStatus.READER_IDLE))
//		{
//			throw new LightCommonException(ComErrorCode.COM_READ_TIMEOUT);
//		}
//		
//		
//	}
	
	
	public IClientProcessor getClientProcessor() {
		return clientProcessor;
	}

	public void setClientProcessor(IClientProcessor clientProcessor) {
		this.clientProcessor = clientProcessor;
	}

	public void sessionClosed(IoSession session) throws Exception {
		clientProcessor.removeDeadSession((InetSocketAddress)session.getRemoteAddress());
	}
	
	
	
	

}
