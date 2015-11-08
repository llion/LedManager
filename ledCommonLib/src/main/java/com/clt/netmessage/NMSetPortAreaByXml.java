package com.clt.netmessage;

import java.io.Serializable;

public class NMSetPortAreaByXml extends NMBase implements Serializable
{

    private String path;
    public NMSetPortAreaByXml()
    {
        mType = NetMessageType.SetPortAreaByXml;
    }
    public String getPath()
    {
        return path;
    }
    public void setPath(String path)
    {
        this.path = path;
    }
    
    
}
