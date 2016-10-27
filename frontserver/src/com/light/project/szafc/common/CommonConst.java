package com.light.project.szafc.common;

public interface CommonConst {
	
	public interface GlobalConfig {
		String THIS_NODE_KEY = "globalconfig.thisnode.id";
		String THIS_NODE_DEFAULTVALUE = "00010001";
		String DEFAULT_DEST_KEY = "globalconfig.defaultdest.id";
		String DEFAULT_DEST_DEFAULTVALUE = "00000001";
	}
	
	public interface BusiConfig {
		String TXNFILE_CURRSEQ_ORDERDETAIL_KEY = "busiconfig.txnfile.currseq.orderdetail";
	}
	
	public interface SeqKey {
		String SEQ_SESSION_KEY = "seq.session";
		String SEQ_OUTFILE_KEY = "seq.outfile";
	}

}
