package com.clt.netmessage;

import java.io.Serializable;

public class NMSetSenderColorTemp extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3552545913432505119L;

    public int getColorTemp()
    {
        return colorTemp;
    }

    public void setColorTemp(int colorTemp)
    {
        this.colorTemp = colorTemp;
    }

    protected int colorTemp = 6500;

    public NMSetSenderColorTemp()
    {
        mType = NetMessageType.SetColorTemperture;
    }

}
