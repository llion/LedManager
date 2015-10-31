package com.clt.netmessage;

import java.io.Serializable;

public class NMHeartBreak extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3239031164595617585L;

    public NMHeartBreak()
    {
        mType = NetMessageType.HeartBreak;
    }
}
