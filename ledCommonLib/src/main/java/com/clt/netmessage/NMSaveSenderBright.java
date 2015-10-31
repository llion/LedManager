package com.clt.netmessage;

import java.io.Serializable;

public class NMSaveSenderBright extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3020215378638334707L;

    public int getBright()
    {
        return bright;
    }

    public void setBright(int bright)
    {
        this.bright = bright;
    }

    protected int bright = 255;

    public NMSaveSenderBright()
    {
        mType = NetMessageType.SaveSenderBright;
    }

}
