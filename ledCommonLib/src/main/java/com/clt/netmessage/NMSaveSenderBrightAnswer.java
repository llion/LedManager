package com.clt.netmessage;

import java.io.Serializable;

public class NMSaveSenderBrightAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7892448291433418030L;

    public NMSaveSenderBrightAnswer()
    {
        mType = NetMessageType.SaveSenderBrightAnswer;
    }

}
