package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.commondata.SomeInfo;
import com.clt.entity.Program;
import com.clt.entity.ReceiverSetting;
import com.clt.entity.ReceiverSettingInfo;

public class NMGetSomeInfoAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -2528823051223637418L;
    
    public NMGetSomeInfoAnswer()
    {
        mType = NetMessageType.GetSomeInfoAnswer;
    }
    private SomeInfo someInfo;
    public SomeInfo getSomeInfo()
    {
        return someInfo;
    }
    public void setSomeInfo(SomeInfo someInfo)
    {
        this.someInfo = someInfo;
    }
    

    
}
