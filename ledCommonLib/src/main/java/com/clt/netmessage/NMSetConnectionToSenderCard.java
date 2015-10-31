package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.entity.ConnectionParam;
import com.clt.entity.ReceiverSetting;

public class NMSetConnectionToSenderCard extends NMBase implements Serializable
{

    
    /**
     * 
     */
    private static final long serialVersionUID = -8900743806198129368L;
    private ConnectionParam connectionParam;

    public NMSetConnectionToSenderCard()
    {
        mType = NetMessageType.setConnectionToSenderCard;
    }

    public ConnectionParam getConnectionParam()
    {
        return connectionParam;
    }

    public void setConnectionParam(ConnectionParam connectionParam)
    {
        this.connectionParam = connectionParam;
    }

    
    
    
}
