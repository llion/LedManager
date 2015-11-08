package com.clt.netmessage;

import java.io.Serializable;

public class NMSetTestModeAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1530803680301872794L;

    public NMSetTestModeAnswer()
    {
        mType = NetMessageType.SetTestModeAnswer;
    }

}
