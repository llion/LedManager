package com.clt.netmessage;

import java.io.Serializable;

public class NMConnectSuccess extends NMBase implements Serializable
{


    /**
     * 
     */
    private static final long serialVersionUID = -1202742365638441123L;

    public NMConnectSuccess()
    {
        mType = NetMessageType.ConnectSuccess;
    }
}
