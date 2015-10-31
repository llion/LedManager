package com.clt.netmessage;

import java.io.Serializable;

public class NMHeartBreakAnswer extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 164451548456618296L;

    public NMHeartBreakAnswer()
    {
        mType = NetMessageType.HeartBreakAnswer;
    }
}
