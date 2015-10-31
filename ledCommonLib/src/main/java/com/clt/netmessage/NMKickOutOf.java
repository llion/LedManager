package com.clt.netmessage;

import java.io.Serializable;

public class NMKickOutOf extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 5963509508999818946L;

    public NMKickOutOf()
    {
        mType = NetMessageType.KickOutOf;
    }
}
