package com.clt.netmessage;

import java.io.Serializable;

public class NMSetEDID extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4895276979412744729L;

    int width;

    int height;

    int freq = 60;

    public NMSetEDID()
    {
        mType = NetMessageType.SetEDID;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getFreq()
    {
        return freq;
    }

    public void setFreq(int freq)
    {
        this.freq = freq;
    }

}
