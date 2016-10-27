package com.light.project.szafc.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.light.common.CommonTools;
import com.light.common.tools.IFactory;
import com.light.common.tools.ctxdata.ICtxAccessor;
import com.light.common.tools.spring.SpringContextHolder;
import com.light.common.xdr.entity.TU32;
import com.light.project.szafc.common.ctxdata.ComboCtxFactory;
import com.light.project.szafc.common.entity.file.TSZAFCTime64;

public class BusiTools {
	
	public static final String GLOBAL_CTXACCESSOR_FACTORY = "globalCtxFactoryBean";
	
	public static final String GLOBAL_CTX = "GLOBAL_CTX";
	
	public static final String GLOBAL_CTX_TEST = "GLOBAL_CTX_TEST";
		
	public static ICtxAccessor getCtxAccessor(String ctxKey)
	{
		if (ctxKey.equalsIgnoreCase(GLOBAL_CTX))
		{
			IFactory factory = (IFactory)(SpringContextHolder.getBean(GLOBAL_CTXACCESSOR_FACTORY));
			
			ICtxAccessor ctx = null;
			
			try
			{
				ctx = (ICtxAccessor)(factory.getObject());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return ctx;
		}
		else if (ctxKey.equalsIgnoreCase(GLOBAL_CTX_TEST))
		{
			IFactory ctxFactory = new ComboCtxFactory();
			
			try {
				return (ICtxAccessor)ctxFactory.getObject();
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
			
		}
		else
		{
			return null;
		}
	}
	
	
	
	public static TSZAFCTime64 getCurrTimeGMT()
	{
//		TU32 loSec = new TU32((int)com.light.common.CommonTools.getCurrSecGMT());
//		TU32 hiSec = new TU32(0);
//		TSZAFCTime64 time = new TSZAFCTime64();
//		time.setTime_LO(loSec);
//		time.setTime_HI(hiSec);
		return getDateGMT(null);
		
	}
	
	public static TSZAFCTime64 getDateGMT(Long dateSec)
	{
		
		if (dateSec == null)
		{
			dateSec = com.light.common.CommonTools.getCurrSecGMT();
		}
		TU32 loSec = new TU32(dateSec.intValue());
		TU32 hiSec = new TU32(0);
		TSZAFCTime64 time = new TSZAFCTime64();
		time.setTime_LO(loSec);
		time.setTime_HI(hiSec);
		return time;
	}
	
	
	public static TSZAFCTime64 getDateGMT(String dateString, String dateFormat) throws ParseException
	{
//		TU32 loSec = new TU32((int)com.light.common.CommonTools.getCurrSecGMT());
//		TU32 hiSec = new TU32(0);
//		TSZAFCTime64 time = new TSZAFCTime64();
//		time.setTime_LO(loSec);
//		time.setTime_HI(hiSec);
		return getDateGMT(com.light.common.CommonTools.getDateGMT(dateString, dateFormat));
		
	}
	
	public static Object getNumFromStr(String numStr, Integer radix, String returnType)
	{
		return CommonTools.getNumFromStr(numStr.split("\\.")[0], radix, returnType);
	}
	
	public static String dateCal(String date, String dateFormat, Integer calTime, Integer calUnit) throws ParseException
	{
		Date dateTime;
		
		if (dateFormat == null)
		{
			dateFormat = "yyyyMMdd";
		}
		SimpleDateFormat myFmt=new SimpleDateFormat(dateFormat);
		
		if (date == null)
		{
			dateTime = new Date();
		}
		else
		{
			
			dateTime = myFmt.parse(date);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		if (calUnit == null)
		{
			calUnit = Calendar.DATE;
		}
		if (calTime == null)
		{
			calTime = 0;
		}
		cal.add(calUnit, calTime);
		
		return myFmt.format(cal.getTime());

//		return CommonTools.getNumFromStr(numStr.split("\\.")[0], radix, returnType);
	}
	
	
	public static String trimFileNameFromPath(String filePath, String fileName)
	{
		String path = filePath.trim();
		String name = fileName.trim();
		
		if (path.equalsIgnoreCase(name))
		{
			return ".";
		}
		
		int idx = path.indexOf(name);
		
		if (idx == 1)
		{
			return path.substring(0, idx);
		}
		else
		{
			return path.substring(0, idx - 1);
		}
	}
	
	public static void main(String[] argv)
	{
		String a = "d:/1234/hhh/ll.t";
		String sub = "ll.t";
		
		System.out.println(trimFileNameFromPath(a, sub));
	}
}
