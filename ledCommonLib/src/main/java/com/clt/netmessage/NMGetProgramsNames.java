package com.clt.netmessage;

import java.io.Serializable;

public class NMGetProgramsNames extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3667202437630260807L;

    public NMGetProgramsNames()
    {
        mType = NetMessageType.getProgramsNames;
    }

}
