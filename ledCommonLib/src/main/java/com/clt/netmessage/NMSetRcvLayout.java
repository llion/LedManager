package com.clt.netmessage;

import java.io.Serializable;

public class NMSetRcvLayout extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -7077895854450552291L;

    public NMSetRcvLayout()
    {
        mType = NetMessageType.SetRcvLayout;
    }

}
