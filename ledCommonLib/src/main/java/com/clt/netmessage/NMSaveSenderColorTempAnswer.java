package com.clt.netmessage;

import java.io.Serializable;

public class NMSaveSenderColorTempAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -2416254463550436983L;

    public NMSaveSenderColorTempAnswer()
    {
        mType = NetMessageType.SaveBrightAndColorTempAnswer;
    }

}
