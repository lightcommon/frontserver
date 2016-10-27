package com.light.project.szafc.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;

import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.server.client.IClientHandler;
import com.lightcomm.comm.server.client.IClientProcessor;
import com.lightcomm.common.constdef.LightConstDef.ComCfg.Keys;

public class ClientProcessor implements IClientProcessor {
	
	private final Logger logger = LoggerFactory.getLogger(ClientProcessor.class);
	

	private IoConnector connector = null;
	
	private ConcurrentHashMap<InetSocketAddress, IoSession> sessionMap = new ConcurrentHashMap<InetSocketAddress, IoSession>();
	
	private ConcurrentHashMap<InetSocketAddress, InetSocketAddress> connectInitSynTool = new ConcurrentHashMap<InetSocketAddress, InetSocketAddress>();
	// when it's not short connection, there are two things the user should notice
	// 1.this synclient object should coordinate with a dead session processor which could be a filter or a handler
	// 2.user should better set synProcessData to be true when you are concurrent processing on one session, otherwise it may cause reply package race between threads
	private boolean isShortConnection;
	// may be useful when you are using a long connection, see comment on isShortConnection;
	private boolean synProcessData;
	
	private Map comConfig;
	

	public Map getComConfig() {
		return comConfig;
	}


	public void setComConfig(Map comConfig) {
		this.comConfig = comConfig;
	}
	
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


	public boolean isShortConnection() {
		return isShortConnection;
	}


	public boolean isSynProcessData() {
		return synProcessData;
	}


	public IoConnector getConnector() {
		return connector;
	}


	public void setConnector(IoConnector connector) throws Exception {
		if (this.connector == null)
		{
			if (connector == null)
			{
				// TODO should use specified exception type
				throw new Exception("Can not set a null connector");
			}
			connector.getSessionConfig().setUseReadOperation(true);
			this.connector = connector;
		}
		else
			// TODO should use specified exception type
			throw new Exception("Can not reset the connector");
	}
	
	public ClientProcessor(boolean isShortConnection) {
	
		this(isShortConnection, true);
	}
	
	public ClientProcessor(boolean isShortConnection, boolean synProcessDataTrans) {
		this.isShortConnection = isShortConnection;
		this.synProcessData = synProcessDataTrans;
	}

	public void removeDeadSession(InetSocketAddress remoteAddress)
	{
		if (!isShortConnection)
		{
			sessionMap.remove(remoteAddress);
			connectInitSynTool.remove(remoteAddress);
		}
	}
	
	public IClientHandler getWorkingClient(InetSocketAddress remoteAddress)
	{
		IoSession session = getWorkingSession(remoteAddress);
		
		Map comCfg = comConfig;
		
		return new ComClient(session, comCfg);
	}
	
	
	
	public IoSession getWorkingSession(InetSocketAddress remoteAddress)
	{
		IoSession session = null;
	
		if (isShortConnection)
		{
			session = getWorkingSessionShortConn(remoteAddress);
		}
		else
		{
			session = getWorkingSessionNotShortConn(remoteAddress);
		}
		
		return session;
	}
	
	protected IoSession getWorkingSessionShortConn(InetSocketAddress remoteAddress)
	{
		ConnectFuture connectfuture;
		IoSession session = null;
		
		String connTimeOut = null;
		int connTimeOutInt = 0;
		try {
			connTimeOut = (String)getComConf(Keys.CFG_CONN_TIMEOUT);
			if (connTimeOut != null)
			{
				connTimeOutInt = Integer.valueOf(connTimeOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (connTimeOutInt != 0)
		{
			connector.setConnectTimeoutMillis(connTimeOutInt);
		}
		
		connectfuture = connector.connect(remoteAddress);
		connectfuture.awaitUninterruptibly();
		session = connectfuture.getSession();
		logger.info("Connected to remote, ip:{}, port:{}", remoteAddress.getAddress().getHostAddress(), remoteAddress.getPort());
		
		return session;
	}
	
	protected IoSession getWorkingSessionNotShortConn(InetSocketAddress remoteAddress)
	{
		InetSocketAddress synAddress = null;
		IoSession session = null;
		
		synchronized(connectInitSynTool)
		{
			synAddress = connectInitSynTool.get(remoteAddress);
			if (synAddress == null)
			{
				connectInitSynTool.put(remoteAddress, remoteAddress);
				synAddress = remoteAddress;
			}
		}
		
		synchronized(synAddress)
		{
			session = sessionMap.get(remoteAddress);
			if (session == null)
			{
				//TODO should process connection fail
				session = getWorkingSessionShortConn(remoteAddress);
				sessionMap.put(remoteAddress, session);
			}
		}
		
		return session;
	}
	
	protected CommMessage processMessage(CommMessage sendmesg, IoSession session) 
	{
		
		if(!isShortConnection && synProcessData)
		{
			return processMessageSyn(sendmesg, session);
		}
		else
		{
			return processMessageNotSyn(sendmesg, session);
		}
	}
	
	protected CommMessage processMessageNotSyn(CommMessage sendmesg, IoSession session) 
	{
		
		CommMessage recvMessage = null;
		
		session.write(sendmesg);
		ReadFuture readFuture = session.read();
		
		while(recvMessage == null)
		{
			if (!readFuture.isClosed())
			{
				readFuture.awaitUninterruptibly();
				//TODO should process exception event
				recvMessage = (CommMessage)readFuture.getMessage();
			}
			else
			{
				session.close(true);
				return null;
			}
		}
		
		return recvMessage;
	}
	
	protected CommMessage processMessageSyn(CommMessage sendmesg, IoSession session) 
	{
		synchronized (session) {
			return processMessageNotSyn(sendmesg, session);
		}
		
	}

}
