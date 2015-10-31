package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.entity.Program;
import com.clt.entity.ReceiverSetting;
import com.clt.entity.ReceiverSettingInfo;

public class NMSetReceiverCardInfoSaveToReceiverAnswer extends NMAnswer implements
        Serializable
{


    /**
     * 
     */
    private static final long serialVersionUID = -6474277985458832997L;
    private ArrayList<ReceiverSettingInfo> receiverSettingInfos;
    
    public NMSetReceiverCardInfoSaveToReceiverAnswer()
    {
        mType = NetMessageType.setReceiveCardSettingInfoSaveToReceiverAnswer;
    }

    public ArrayList<ReceiverSettingInfo> getReceiverSettings()
    {
        return receiverSettingInfos;
    }

    public void setReceiverSettings(ArrayList<ReceiverSettingInfo> receiverSettingInfos)
    {
        this.receiverSettingInfos = receiverSettingInfos;
    }

    
}
