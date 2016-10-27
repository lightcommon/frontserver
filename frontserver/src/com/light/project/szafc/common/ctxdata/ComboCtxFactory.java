package com.light.project.szafc.common.ctxdata;

import java.util.ArrayList;
import java.util.List;

import com.light.common.tools.ICommonOprFlags;
import com.light.common.tools.IFactory;
import com.light.common.tools.ctxdata.CtxDataAccessor;
import com.light.common.tools.ctxdata.ICtxAccessor;
import com.light.common.tools.ctxdata.MapCtxDataAccessor;
import com.light.project.szafc.common.CommonConst;

public class ComboCtxFactory implements IFactory {
	
	private ICtxAccessor ctx = new CtxDataAccessor();;
	
	{
//		CtxDataAccessor myctx = new CtxDataAccessor();
//		ICtxAccessor mapCtx  = new MapCtxDataAccessor();
//		mapCtx.setData(CommonConst.GlobalConfig.THIS_NODE_KEY, CommonConst.GlobalConfig.THIS_NODE_DEFAULTVALUE);
//		ICtxAccessor seqCtx = new SeqCtxAccessor();
//		
//		List ctxLst =  new ArrayList<ICtxAccessor>();
//		ctxLst.add(mapCtx);
//		ctxLst.add(seqCtx);
//		
//		myctx.setAccessorLst(ctxLst);
//		
//		ctx = myctx;
	}
	
	public void setAccessorList(List<ICtxAccessor> list)
	{
		if (list != null)
		{
			((CtxDataAccessor)ctx).setAccessorLst(list);
		}
	}

	@Override
	public Object getObject() throws Exception {

		return getObject(null);
	}

	@Override
	public Class getObjectType() {
		
		return ctx.getClass();
	}

	@Override
	public boolean isSingleton() {
		
		return true;
	}

	@Override
	public Object getObject(ICommonOprFlags oprFlags) throws Exception {
		
		return ctx;
	}

}
