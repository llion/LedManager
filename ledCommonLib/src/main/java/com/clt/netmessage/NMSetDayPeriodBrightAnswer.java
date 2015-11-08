package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SenderInfo;

public class NMSetDayPeriodBrightAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -9112594745737951144L;

    public NMSetDayPeriodBrightAnswer()
    {
        mType = NetMessageType.setDayPeriodBrightAnswer;
    }
}
