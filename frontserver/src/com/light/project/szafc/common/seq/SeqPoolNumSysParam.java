package com.light.project.szafc.common.seq;

import com.huateng.bussiness.sysparameter.entity.SysParameterVo;
import com.huateng.bussiness.sysparameter.service.ISysParameterService;
import com.light.common.tools.sequtils.*;

//seqId 由分组id和参数id组成，中间用.号分隔
public class SeqPoolNumSysParam extends SeqNoProcUtil.SeqPoolNum {
	
	private ISysParameterService syService;
	
//	private Long seqMaxValue;
//	
//	private Long seqMinValue;
	

	public SeqPoolNumSysParam(String seqId, long batchAmount)
	{
		this(seqId, batchAmount, null, null);
	}
	
	public SeqPoolNumSysParam(String seqId, Long batchAmount, Long maxSeqValue, Long minSeqValue)
	{
		super(seqId, batchAmount, maxSeqValue, minSeqValue);
	}

	@Override
	protected Long getSeqByBatch(SeqNoProcUtil.SeqPoolNum batchParam) {
//		SysParameterVo sysParameterVo = new SysParameterVo();
////		sysParameterVo.setParamCategoryId("SERIAL_NUMBER_TYPE");
////		sysParameterVo.setParamId("SYSTEM_TRANCE_NUMBER");
//		String seqId = getSeqId();
//		String param[] = seqId.split(".");
//		
//		
//		sysParameterVo.setParamCategoryId(param[0]);
//		sysParameterVo.setParamId(param[1]);
		SysParameterVo sysParameterVo = getSysParamVo();
		sysParameterVo.setParamVal(Long.valueOf(batchParam.getBatchAmount()).toString());
		
//		String paramSystemTraceNumber = syService.findSysParameterByParamId(
//				"SYSTEM_TRANCE_NUMBER").getParamVal();
		
		SysParameterVo sysVo = null;
		try {
			sysVo = syService.doGetSysParamSeqByBatch(sysParameterVo, batchParam.getMaxSeqValue(), batchParam.getMinSeqValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		String paramVal = sysVo.getParamVal();
		
//		if ()

		return Long.valueOf(paramVal);
	}
	
	protected Long doSeqStartAgain(Long maxSeqValue, Long minSeqValue,
			Long batchAmount) {
		
//		SysParameterVo sysParameterVo = getSysParamVo();
//		sysParameterVo.setParamVal(Long.valueOf(batchAmount).toString());
//		
//		
//		SysParameterVo sysVo = null;
//		try {
//			sysVo = syService.getSysParamSeqByBatch(sysParameterVo);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
		
		return null;
	}
	
	private SysParameterVo getSysParamVo()
	{
		SysParameterVo sysParameterVo = new SysParameterVo();
//		sysParameterVo.setParamCategoryId("SERIAL_NUMBER_TYPE");
//		sysParameterVo.setParamId("SYSTEM_TRANCE_NUMBER");
//		String seqId = getSeqId();
//		String[] param = seqId.split("\\.");
		
		
//		sysParameterVo.setParamCategoryId(param[0]);
		sysParameterVo.setParamId(getSeqId());
		return sysParameterVo;
	}
	
	
	
	public ISysParameterService getSyService() {
		return syService;
	}

	public void setSyService(ISysParameterService syService) {
		this.syService = syService;
	}
}
