package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMSetConnectionToSenderCardAnswer extends NMAnswer implements
        Serializable
{


    /**
     * 
     */
    private static final long serialVersionUID = 5760970982293172450L;

    public NMSetConnectionToSenderCardAnswer()
    {
        mType = NetMessageType.setConnectionToSenderCardAnswer;
    }
}
