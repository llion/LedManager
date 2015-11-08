package com.clt.netmessage;

import java.io.Serializable;

public class NMBase implements Serializable
{
    public static final int WIFI = 1;

    public static final int Ethernet = 2;

    protected int netType = 1;//网络类型，wifi或百兆网

    protected int mType;

    public int getmType()
    {
        return mType;
    }

    public void setmType(int mType)
    {
        this.mType = mType;
    }

    public int getNetType()
    {
        return netType;
    }

    public void setNetType(int netType)
    {
        this.netType = netType;
    }
    
    
    
}
