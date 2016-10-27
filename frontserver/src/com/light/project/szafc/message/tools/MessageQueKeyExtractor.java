package com.light.project.szafc.message.tools;

import java.util.HashMap;
import java.util.Map;

import com.light.common.com.ClientCenter.QueKeyExtractor;
import com.light.common.messagequeue.IMessage;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;

public class MessageQueKeyExtractor implements QueKeyExtractor {
	
	private Map<String, String> repKeyMap = new HashMap<String, String>();
	
	private Map<String, String> trashMap = new HashMap<String, String>();

	@Override
	public String getKey(Object inData) {
		if (inData instanceof CommMessage)
		{
			CommMessage commMess = (CommMessage) inData;
			
			MessageHead messHead = (MessageHead)((HeadBodyMessage)commMess.getMessageObject()).getHeadMessage();
			
			if (messHead.isReqFlag())
			{
				return "REQ_PROC_QUEUE";
			}
			else
			{
				String key = repKeyMap.get(messHead.getSessionId());
				if (key == null)
				{
					key = trashMap.get(messHead.getSessionId());
				}
				
				if (key != null)
				{
					repKeyMap.remove(messHead.getSessionId());
					trashMap.put(messHead.getSessionId(), key);
				}
				
				return key;
			}
		}
		return null;
	}

	@Override
	public void prepareCtx(Object inData) {
		if (inData instanceof IMessage)
		{
			IMessage qMess = (IMessage)inData;
			CommMessage commMess = (CommMessage) qMess.getMessageBody();
			
			MessageHead messHead = (MessageHead)((HeadBodyMessage)commMess.getMessageObject()).getHeadMessage();
			
			if (messHead.isReqFlag())
			{
				repKeyMap.put(messHead.getSessionId(),qMess.getReplyQueueId());
			}
		}

	}

}
