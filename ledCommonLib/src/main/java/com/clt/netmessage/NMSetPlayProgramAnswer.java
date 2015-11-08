package com.clt.netmessage;

import java.io.Serializable;

public class NMSetPlayProgramAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3273915147816437842L;

    public NMSetPlayProgramAnswer()
    {
        mType = NetMessageType.SetPlayProgramAnswer;
    }
}
