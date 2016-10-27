package com.light.project.szafc.common.ctxdata;

import java.util.HashMap;
import java.util.Map;

import com.light.common.tools.ctxdata.ICtxAccessor;
import com.light.common.tools.sequtils.SeqNoProcUtil.SeqGenerator;
import com.light.project.szafc.common.CommonConst;

public class SeqCtxAccessor implements ICtxAccessor {
	
	
	
	private Map<String, SeqGenerator> dataStore;
	
//	{
//		dataStore = new HashMap<String, Integer>();
//		dataStore.put(CommonConst.SeqKey.SEQ_SESSION_KEY, 0);
//		dataStore.put(CommonConst.SeqKey.SEQ_OUTFILE_KEY, 0);
//		
//	}

	public Map<String, SeqGenerator> getDataStore() {
		return dataStore;
	}

	public void setDataStore(Map<String, SeqGenerator> dataStore) {
		this.dataStore = dataStore;
	}

	@Override
	public synchronized Object getData(String key) {
		SeqGenerator seqGen = dataStore.get(key);
		
		if (seqGen != null)
		{
			return seqGen.getNext();
		}
		else
		{
			return null;
		}
//		return seq;
	}

	@Override
	public void setData(String key, Object data) {
		
//		throw new UnsupportedOperationException(SeqCtxAccessor.class.getName() + " do not surpport setData method");

	}

}
