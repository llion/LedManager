package com.clt.netmessage;

import java.io.Serializable;

public class NMSetRcvLayoutAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4355003818222719639L;

    public NMSetRcvLayoutAnswer()
    {
        mType = NetMessageType.SetSenderControlArea;
    }

}
