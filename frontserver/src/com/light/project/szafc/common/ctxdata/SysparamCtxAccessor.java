package com.light.project.szafc.common.ctxdata;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.huateng.bussiness.sysparameter.entity.SysParameterVo;
import com.huateng.bussiness.sysparameter.service.ISysParameterService;
import com.light.common.tools.ctxdata.ICtxAccessor;

public class SysparamCtxAccessor implements ICtxAccessor {
	
	private ISysParameterService syService;

	public ISysParameterService getSyService() {
		return syService;
	}

	public void setSyService(ISysParameterService syService) {
		this.syService = syService;
	}

	@Override
	public Object getData(String key) {
		
		SysParameterVo param = new SysParameterVo();
		param.setParamId(key);
		
		SysParameterVo retParam;
		try {
			retParam = syService.findSysParameterByGroupIdAndId(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if (retParam == null)
		{
			return null;
		}
		else
		{
			return retParam.getParamVal();
		}
		
	}

	@Override
	public void setData(String key, Object data) {
		
		
		SysParameterVo param = new SysParameterVo();
		param.setParamId(key);
		param.setParamVal((String)data);
		
		param.setLastModifyTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		try {
			syService.updateSysParameterBySysSeqNo(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
