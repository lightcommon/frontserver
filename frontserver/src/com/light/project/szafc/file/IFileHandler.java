package com.light.project.szafc.file;

import java.util.List;

import org.acplt.oncrpc.XdrAble;
import com.light.common.file.FileInfo;

public interface IFileHandler {
	
	FileInfo addOneData(Object data) throws Exception;
	
	FileInfo currFileEnd() throws Exception;
	
}
