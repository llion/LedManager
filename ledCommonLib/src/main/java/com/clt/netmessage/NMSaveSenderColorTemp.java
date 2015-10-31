package com.clt.netmessage;

import java.io.Serializable;

public class NMSaveSenderColorTemp extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 545543769596629043L;

    public int getColorTemp()
    {
        return colorTemp;
    }

    public void setColorTemp(int colorTemp)
    {
        this.colorTemp = colorTemp;
    }

    protected int colorTemp = 6500;

    public NMSaveSenderColorTemp()
    {
        mType = NetMessageType.SaveSenderColorTemp;
    }

}
