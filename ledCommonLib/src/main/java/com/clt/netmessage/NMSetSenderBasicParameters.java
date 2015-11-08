package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.PortArea;
import com.clt.commondata.SenderParameters;

public class NMSetSenderBasicParameters extends NMBase implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6986992003638332742L;

    private SenderParameters params = new SenderParameters();

    public NMSetSenderBasicParameters()
    {
        mType = NetMessageType.SetSenderBasicParameters;
    }

    public SenderParameters getParams()
    {
        return params;
    }

    public void setParams(SenderParameters params)
    {
        this.params = params;
    }

}
