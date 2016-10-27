// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValueByte2HexStringCodecImpl.java

package test;

import com.light.bomap.common.codec.face.IValueCodec;
import com.light.bomap.common.codec.impl.value.ValueCodecDefaultProc;
import com.light.bomap.common.util.ConvertTools;

// Referenced classes of package com.huateng.bomap.common.codec.impl.value:
//            ValueCodecDefaultProc

public class ValueByte2HexStringCodecImpl extends ValueCodecDefaultProc
    implements IValueCodec
{

    public ValueByte2HexStringCodecImpl()
    {
    }

    public String doDecode(byte inBytes[])
        throws Exception
    {
        String resultHex = ConvertTools.bytesToHexString(inBytes);
        return resultHex;
    }

    public byte[] doEncode(String outData, int maxWidth)
        throws Exception
    {
        if(outData == null)
            outData = "";
        byte resultbytes[] = ConvertTools.hexStringToByte(outData);
        return resultbytes;
    }

    public byte[] doEncode(Object obj, int i)
        throws Exception
    {
        return doEncode((String)obj, i);
    }

    public Object decoder(byte abyte0[])
        throws Exception
    {
        return doDecode(abyte0);
    }
}
