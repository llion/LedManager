package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.entity.Program;
import com.clt.entity.ReceiverSetting;
import com.clt.entity.ReceiverSettingInfo;

public class NMSetReceiverCardInfoSendAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1871513145547512650L;

    private ArrayList<ReceiverSettingInfo> receiverSettingInfos;
    
    public NMSetReceiverCardInfoSendAnswer()
    {
        mType = NetMessageType.setReceiveCardSettingInfoSendAnswer;
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
