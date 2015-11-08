package com.clt.netmessage;

import java.io.Serializable;

public class NMSaveBrightAndColorTemp extends NMBase implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -3020215378638334707L;

    protected int bright = 255;

    protected int colorTemp = 6500;

    public int getBright()
    {
        return bright;
    }

    public void setBright(int bright)
    {
        this.bright = bright;
    }

    public int getColorTemp()
    {
        return colorTemp;
    }

    public void setColorTemp(int colorTemp)
    {
        this.colorTemp = colorTemp;
    }

    public NMSaveBrightAndColorTemp()
    {
        mType = NetMessageType.SaveBrightAndColorTemp;
    }

}
