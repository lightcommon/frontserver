package com.light.project.szafc.message.tools;

import com.light.messageframework.exchange.entity.ExchangeHead;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.ExtractMessType;

public class MessageTypeExtractor implements ExtractMessType {

	@Override
	public String getMessType(Object headMessage) {
		if (headMessage instanceof MessageHead)
		{
			MessageHead hMessage = (MessageHead)headMessage;
			
			String repRepFlag = null;
			
			if (hMessage.isReqFlag())
			{
				repRepFlag = "q";
			}
			else
			{
				repRepFlag = "p";
			}
			
			if (hMessage.getMessageType().equalsIgnoreCase("9000") || hMessage.getMessageType().equalsIgnoreCase("2001"))
			{
				if (hMessage.isReqFlag())
				{
					return hMessage.getMessageType() + repRepFlag;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
	}

}
