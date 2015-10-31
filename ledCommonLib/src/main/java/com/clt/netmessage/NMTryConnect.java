package com.clt.netmessage;

import java.io.Serializable;

public class NMTryConnect extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1248664466467397643L;

    /**
     * 
     */
    private int index;

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public NMTryConnect()
    {
        mType = NetMessageType.TryConnect;
    }
}
