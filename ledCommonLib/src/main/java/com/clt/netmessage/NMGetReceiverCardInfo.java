package com.clt.netmessage;

import java.io.Serializable;

public class NMGetReceiverCardInfo extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1096964454359596572L;

    public NMGetReceiverCardInfo()
    {
        mType = NetMessageType.getReceiveCardInfo;
    }
}
