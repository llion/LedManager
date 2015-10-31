package com.clt.netmessage;

import java.io.Serializable;

public class NMNetNeedClosed extends NMBase implements Serializable
{

    public NMNetNeedClosed()
    {
        mType = NetMessageType.netNeedClosed;
    }

}
