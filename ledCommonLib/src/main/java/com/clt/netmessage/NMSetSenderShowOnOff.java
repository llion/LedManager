package com.clt.netmessage;

import java.io.Serializable;

public class NMSetSenderShowOnOff extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 7448842571067898224L;

    public boolean isShowOn()
    {
        return showOn;
    }

    public void setShowOn(boolean showOn)
    {
        this.showOn = showOn;
    }

    protected boolean showOn = true;

    public NMSetSenderShowOnOff()
    {
        mType = NetMessageType.SetSenderShowOnOff;
    }

}
