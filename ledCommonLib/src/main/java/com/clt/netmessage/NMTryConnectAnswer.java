package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMTryConnectAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -182951472416744740L;

    /**
     * 
     */

    protected SenderInfo senderInfo = new SenderInfo();

    public SenderInfo getSenderInfo()
    {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo)
    {
        this.senderInfo = senderInfo;
    }

    public NMTryConnectAnswer()
    {
        mType = NetMessageType.TryConnectAnswer;
    }
}
