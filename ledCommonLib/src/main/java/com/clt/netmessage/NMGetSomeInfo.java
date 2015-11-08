package com.clt.netmessage;

import java.io.Serializable;

import com.clt.commondata.SomeInfo;

public class NMGetSomeInfo extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1820159404789808128L;

    public NMGetSomeInfo()
    {
        mType = NetMessageType.GetSomeInfo;
    }
    
    

}
