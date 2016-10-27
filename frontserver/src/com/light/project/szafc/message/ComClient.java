package com.light.project.szafc.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;

import com.light.common.tools.ICommonOprFlags;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.server.client.IClientHandler;
import com.lightcomm.common.constdef.LightConstDef.ComCfg;
import com.lightcomm.common.constdef.LightConstDef.ComCfg.DefaultValue;
import com.lightcomm.common.constdef.LightConstDef.ComCfg.Keys;
import com.lightcomm.common.constdef.LightConstDef.ErrorCode.ComErrorCode;
import com.lightcomm.common.exception.LightCommonException;

public class ComClient implements IClientHandler {
	
	private ReadFuture readFuture;
	
	public ComClient(IoSession session) {
		this.session = session;
		
		this.comConfig = new HashMap();
	}
	
	public ComClient(IoSession session, Map comConfig) {
		this.session = session;
		if (comConfig == null)
		{
			this.comConfig = new HashMap();
		}
		else
		{
			this.comConfig = comConfig;
		}
		
	}

	private IoSession session;
	
	private Map comConfig;
	
	public Object getComConf(String key) {
		if (comConfig != null)
		{
			return comConfig.get(key);
		}
		return null;
	}
	
	public void setComConf(String key, Object value) {
		if (comConfig != null)
		{
			comConfig.put(key,value);
		}
	}

	@Override
	public CommMessage synSend(CommMessage sendmesg) throws Exception {
		WriteFuture writeFuture = asynSend(sendmesg);
		CommMessage replyMessage= asynRecieve(true);
		return replyMessage;
	}

	@Override
	public WriteFuture asynSend(CommMessage sendmesg) {
//		CommMessage recvMessage = null;
		
		return session.write(sendmesg);
//		ReadFuture readFuture = session.read();
//		
//		while(recvMessage == null)
//		{
//			if (!readFuture.isClosed())
//			{
//				readFuture.awaitUninterruptibly();
//				//TODO should process exception event
//				recvMessage = (CommMessage)readFuture.getMessage();
//			}
//			else
//			{
//				session.close(true);
//				return null;
//			}
//		}
//		
//		return recvMessage;

	}

	@Override
	public CommMessage asynRecieve(boolean blockFlag) throws Exception {
		CommMessage recvMessage = null;
		boolean hasWaited = false;
		
		String readTimeOut = null;
		int readTimeOutInt = 0;
		try {
			readTimeOut = (String)getComConf(Keys.CFG_CONN_TIMEOUT);
			if (readTimeOut != null)
			{
				readTimeOutInt = Integer.valueOf(readTimeOut);
			}
			else {
				
				readTimeOutInt = Integer.valueOf(DefaultValue.CFG_READ_TIMEOUT_DVALUE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		session.write(sendmesg);
		
		if (readFuture == null)
		{
			readFuture = session.read();
		}
		
		try
		{
			while(recvMessage == null)
			{
				if (!readFuture.isClosed())
				{
					
					Throwable e = readFuture.getException();
					
					recvMessage = (CommMessage)readFuture.getMessage();
					
					if (recvMessage != null)
					{
	//					recvMessage = (CommMessage)readFuture.getMessage();
					}	
					else if(blockFlag && !hasWaited)
					{
						
						readFuture.awaitUninterruptibly(readTimeOutInt);
						
						hasWaited = true;
					}
					else 
					{
						break;
					}
									
					
					
				}
				else
				{
					session.close(true);
					throw new LightCommonException(ComErrorCode.COM_CONNECTION_FORCECLOSED);
				}
			}
		}
		finally
		{
			if (recvMessage != null)
			{
				readFuture = null;
			}
		}
		
		return recvMessage;
	}

	@Override
	public Object getClientStatus(String key) {
		
		if (key.equalsIgnoreCase(IClientHandler.ClientConst.SESSION_CONNECTION_STATUS))
		{
			if (session.isConnected())
			{
				return IClientHandler.ClientConst.SESSION_CONNECTION_STATUS_CONNECTED;
			}
			else
			{
				return IClientHandler.ClientConst.SESSION_CONNECTION_STATUS_CLOSED;
			}
		}
		else
		{
			return null;
		}
	}

	

}
