package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.entity.ReceiverSetting;

public class NMReceiveCardSetting extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6547561478362750294L;
    /**
     * 
     */
    
    private ArrayList<ReceiverSetting> receiverSettings;

    public NMReceiveCardSetting()
    {
        mType = NetMessageType.setReceiveCardSetting;
    }

    public ArrayList<ReceiverSetting> getReceiverSettings()
    {
        return receiverSettings;
    }

    public void setReceiverSettings(ArrayList<ReceiverSetting> receiverSettings)
    {
        this.receiverSettings = receiverSettings;
    }

    
    
}
