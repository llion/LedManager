package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMDeleteProgramAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -5059465675852973070L;

    public NMDeleteProgramAnswer()
    {
        mType = NetMessageType.deleteProgramAnswer;
    }
}
