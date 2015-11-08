package com.clt.netmessage;

import java.io.Serializable;

public class NMSetSenderBasicParametersAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3876029107074093835L;

    public NMSetSenderBasicParametersAnswer()
    {
        mType = NetMessageType.SetSenderBasicParametersAnswer;
    }

}
