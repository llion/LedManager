package com.clt.netmessage;

import java.io.Serializable;

public class NMSetEDIDAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 688572019139984693L;

    public NMSetEDIDAnswer()
    {
        mType = NetMessageType.SetEDIDAnswer;
    }

}
