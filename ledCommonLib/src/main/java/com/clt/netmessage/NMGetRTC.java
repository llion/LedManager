package com.clt.netmessage;

import java.io.Serializable;

public class NMGetRTC extends NMBase implements Serializable
{


    /**
     * 
     */
    private static final long serialVersionUID = -5653063968741213354L;

    public NMGetRTC()
    {
        mType = NetMessageType.getRTC;
    }
    private String path;
    public String getPath()
    {
        return path;
    }
    public void setPath(String path)
    {
        this.path = path;
    }
}
