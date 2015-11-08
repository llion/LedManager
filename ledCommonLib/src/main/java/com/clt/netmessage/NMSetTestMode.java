package com.clt.netmessage;

import java.io.Serializable;

public class NMSetTestMode extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3020215378638334707L;

    public int index;

    public NMSetTestMode()
    {
        mType = NetMessageType.SetTestMode;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

}
