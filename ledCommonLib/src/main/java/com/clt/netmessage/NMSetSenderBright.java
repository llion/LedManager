package com.clt.netmessage;

import java.io.Serializable;

public class NMSetSenderBright extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3552545913432505119L;

    public int getBright()
    {
        return bright;
    }

    public void setBright(int bright)
    {
        this.bright = bright;
    }

    protected int bright = 255;

    public NMSetSenderBright()
    {
        mType = NetMessageType.SetSenderBright;
    }

}
