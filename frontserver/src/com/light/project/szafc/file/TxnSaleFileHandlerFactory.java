package com.light.project.szafc.file;

import com.light.common.tools.ICommonOprFlags;
import com.light.common.tools.IFactory;

public class TxnSaleFileHandlerFactory implements IFactory {

	@Override
	public Object getObject() throws Exception {
		return getObject(null);
	}

	@Override
	public Object getObject(ICommonOprFlags oprFlags) throws Exception {
		return new TxnSaleFileHandler();
	}

	@Override
	public Class getObjectType() {
		
		return TxnSaleFileHandler.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
