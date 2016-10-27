package com.light.project.szafc.message.keepalive;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.light.project.szafc.common.entity.message.MessageHead;
import com.light.project.szafc.common.entity.message.PingMessage;
import com.light.project.szafc.message.tools.PackageTools;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;

public class PingCommMessageFactory implements KeepAliveMessageFactory {
	
	private String destNodeId;

	public String getDestNodeId() {
		return destNodeId;
	}

	public void setDestNodeId(String destNodeId) {
		this.destNodeId = destNodeId;
	}

	@Override
	public Object getRequest(IoSession arg0) {
		
		Map<String, String> oprFlag = new HashMap<String, String>();
		
		oprFlag.put(PackageTools.OPRFLAG_REQ_REP_FLAG_KEY, PackageTools.OPRFLAG_REQ_REP_FLAG_REQ);
		oprFlag.put(PackageTools.OPRFLAG_MESSAGE_TYPE_KEY, "2001");
		oprFlag.put(PackageTools.OPRFLAG_RECIEVERID_KEY, destNodeId);
		
		CommMessage message = PackageTools.getBaseMessage(oprFlag);
		
		PingMessage body = new PingMessage();
		body.setReceiverId(destNodeId);
		
		((HeadBodyMessage)message.getMessageObject()).setBodyMessage(body);
		
		
		return message;
	}

	@Override
	public Object getResponse(IoSession arg0, Object arg1) {
		
		return null;
	}

	@Override
	public boolean isRequest(IoSession arg0, Object arg1) {
		return reqRepCheck("req", arg1);
		
	}
	
	
	private MessageHead getMessageHead(Object message)
	{
		if (message instanceof CommMessage)
		{
			CommMessage messObj = (CommMessage)message;
			MessageHead headMessage = (MessageHead)(((HeadBodyMessage)messObj.getMessageObject()).getHeadMessage());
			return headMessage;
		}
		else
		{
			return null;
		}
	}
	
	private boolean reqRepCheck(String checkFlag,	Object message)
	{
		MessageHead headMessage = getMessageHead(message);
		
		if (headMessage != null && headMessage.getMessageType().equalsIgnoreCase("2001"))
		{
			if (checkFlag.equalsIgnoreCase("req"))
			{
				return headMessage.isReqFlag();
			}
			else if (checkFlag.equalsIgnoreCase("rep"))
			{
				return !headMessage.isReqFlag();
			}
			else
			{
				return false;
			}
			
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean isResponse(IoSession arg0, Object arg1) {
		return reqRepCheck("rep", arg1);
	}

}
