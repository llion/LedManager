package com.clt.netmessage;

import java.io.Serializable;

public class NMSaveUidEncrpt extends NMBase implements Serializable
{


    /**
     * 
     */
    private static final long serialVersionUID = -2117079399908039155L;

    public NMSaveUidEncrpt()
    {
        mType = NetMessageType.SaveUidEncrpt;
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
