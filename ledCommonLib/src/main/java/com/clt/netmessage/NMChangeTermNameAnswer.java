package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMChangeTermNameAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3531789589490784452L;


    public NMChangeTermNameAnswer()
    {
        mType = NetMessageType.ModifyTerminateNameAnswer;
    }
}
