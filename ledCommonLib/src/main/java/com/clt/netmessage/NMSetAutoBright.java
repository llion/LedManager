package com.clt.netmessage;

import java.io.Serializable;
/**
 * 自动亮度调节曲线
 *
 */
public class NMSetAutoBright extends NMBase implements Serializable
{


    private static final long serialVersionUID = -1202742365638441123L;

    public NMSetAutoBright()
    {
        mType = NetMessageType.SetAutoBrightness;
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
