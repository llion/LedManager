package com.clt.netmessage;

import java.io.Serializable;

public class NMPassword extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1012228665962779442L;

    public NMPassword()
    {
        mType = NetMessageType.FindTerminate;
    }

}
