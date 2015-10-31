package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMSetConnectionToReceiverCardAnswer extends NMAnswer implements
        Serializable
{



    /**
     * 
     */
    private static final long serialVersionUID = -1446702664575037906L;

    public NMSetConnectionToReceiverCardAnswer()
    {
        mType = NetMessageType.setConnectionToReceiverCardAnswer;
    }
    
    public static final int OK = 1;//没有bin文件
    
    public static final int ERROR = 2;//探测发送卡失败
    
    public static final int ERROR_NO_BIN_FILE = 3;//没有bin文件
    
    public static final int ERROR_DETECT_RECEVIER_FAIL = 4;//探测发送卡失败
    
   
    
    
}
