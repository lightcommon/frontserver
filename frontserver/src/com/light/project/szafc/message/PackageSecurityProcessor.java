package com.light.project.szafc.message;

import java.util.Arrays;

import com.light.common.security.MD5Handler;
import com.light.common.security.SecurityHandler;
import com.lightcomm.comm.common.entity.CommMessage;
import com.lightcomm.comm.common.filter.protocolcodec.security.IPackageSecurityProcessor;

public class PackageSecurityProcessor implements IPackageSecurityProcessor {
	
	private SecurityHandler securityHandler = new MD5Handler();

	public SecurityHandler getSecurityHandler() {
		return securityHandler;
	}

	public void setSecurityHandler(SecurityHandler securityHandler) {
		this.securityHandler = securityHandler;
	}

	@Override
	public boolean doSecurityCheck(CommMessage commMessage) throws Exception {
		byte[] bodyBytes = commMessage.getMessagbody();
		byte[] dataBytes = new byte[bodyBytes.length - 16];
		byte[] macBytes = new byte[16];
		System.arraycopy(bodyBytes, 0, dataBytes, 0, dataBytes.length);
		System.arraycopy(bodyBytes, bodyBytes.length - 16, macBytes, 0, 16);
		
		securityHandler.update(dataBytes);
		byte[] macCal = securityHandler.getSecurityResult();
		
		return Arrays.equals(macBytes, macCal);
	}

	@Override
	public void doSecurityInfo(CommMessage commMessage) throws Exception {
		
		byte[] bodyBytes = commMessage.getMessagbody();
		securityHandler.update(bodyBytes);
		byte[] macCal = securityHandler.getSecurityResult();

		byte[] fullBytes = new byte[16 + bodyBytes.length];
		
		System.arraycopy(bodyBytes, 0, fullBytes, 0, bodyBytes.length);
		System.arraycopy(macCal, 0, fullBytes, bodyBytes.length, 16);
		commMessage.setMessagbody(fullBytes);
		commMessage.setLength(commMessage.getLength() + 16);
	}

}
