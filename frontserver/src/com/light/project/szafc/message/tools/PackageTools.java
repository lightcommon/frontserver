package com.light.project.szafc.message.tools;

import java.util.Map;

import com.light.common.tools.ctxdata.ICtxAccessor;
import com.light.project.szafc.common.CommonConst;
import com.light.project.szafc.common.BusiTools;
import com.light.project.szafc.common.entity.message.MessageHead;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.app.HeadBodyPackProcessor.HeadBodyMessage;

public class PackageTools {
	
	public static final String OPRFLAG_REQ_REP_FLAG_KEY = "REQ_REP_FLAG_KEY";
	
	public static final String OPRFLAG_REQ_REP_FLAG_REQ = "REQ_REP_FLAG_REQ";
	
	public static final String OPRFLAG_REQ_REP_FLAG_REP = "REQ_REP_FLAG_REP";
	
	public static final String OPRFLAG_MESSAGE_TYPE_KEY = "MESSAGE_TYPE_KEY";
	
	public static final String OPRFLAG_REQ_SESSION_ID_KEY = "REQ_SESSION_ID_KEY";
	
	public static final String OPRFLAG_RECIEVERID_KEY = "RECIEVERID_KEY";
	
	public static final String OPRFLAG_NEED_SOCKETINFO_KEY = "OPRFLAG_NEED_SOCKETINFO_KEY";
	
	public static CommMessage getBaseMessage(Map<String, String> oprFlag)
	{
		CommMessage req = new CommMessage();
		
		MessageHead mhead = new MessageHead();
		
		HeadBodyMessage messageObj = new HeadBodyMessage();
		
		messageObj.setHeadMessage(mhead);
		
		req.setMessageObject(messageObj);
		
		ICtxAccessor globalCtx = BusiTools.getCtxAccessor(BusiTools.GLOBAL_CTX);
		
		if (oprFlag.get(OPRFLAG_REQ_REP_FLAG_KEY).equalsIgnoreCase(OPRFLAG_REQ_REP_FLAG_REQ))
		{
			mhead.setReqFlagBool(true);
		}
		else
		{
			mhead.setReqFlagBool(false);
		}
		
		mhead.setSenderId((String)globalCtx.getData(CommonConst.GlobalConfig.THIS_NODE_KEY));
		
		mhead.setMessageType(oprFlag.get(OPRFLAG_MESSAGE_TYPE_KEY));
		
		String receiverId = oprFlag.get(OPRFLAG_RECIEVERID_KEY);
		if (receiverId == null)
		{
			receiverId = (String)globalCtx.getData(CommonConst.GlobalConfig.DEFAULT_DEST_KEY);
		}
		mhead.setReceiverId(receiverId);
		
		if (mhead.isReqFlag())
		{
			mhead.setSessionId(String.format("%08x", Integer.valueOf((String)globalCtx.getData(CommonConst.SeqKey.SEQ_SESSION_KEY))));
		}
		else
		{
			mhead.setSessionId(oprFlag.get(OPRFLAG_REQ_SESSION_ID_KEY));
			
		}
		
		
		if (oprFlag.get(OPRFLAG_NEED_SOCKETINFO_KEY) != null && oprFlag.get(OPRFLAG_NEED_SOCKETINFO_KEY).equalsIgnoreCase("TRUE"))
		{
			// TODO
			req.setRemoteip("127.0.0.1");
			req.setRemoteport(8000);
		}
		
		return req;
	}

}
