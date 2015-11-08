package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMDetectSenderAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3531789589490784452L;

    protected SenderInfo senderInfo = new SenderInfo();

    public SenderInfo getSenderInfo()
    {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo)
    {
        this.senderInfo = senderInfo;
    }

    public NMDetectSenderAnswer()
    {
        mType = NetMessageType.DetectSenderAnswer;
    }
}
