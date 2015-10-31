package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.entity.ConnectionParam;
import com.clt.entity.ReceiverSetting;

public class NMSetConnectionToReceiverCard extends NMBase implements Serializable
{

    
    /**
     * 
     */
    private static final long serialVersionUID = -859389652924139357L;
    private ConnectionParam connectionParam;

    public NMSetConnectionToReceiverCard()
    {
        mType = NetMessageType.setConnectionToReceiverCard;
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
