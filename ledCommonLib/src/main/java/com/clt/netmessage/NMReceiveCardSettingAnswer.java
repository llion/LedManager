package com.clt.netmessage;

import java.io.Serializable;

public class NMReceiveCardSettingAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 855521138809107844L;


    public NMReceiveCardSettingAnswer()
    {
        mType = NetMessageType.setReceiveCardSettingAnswer;
    }

}
