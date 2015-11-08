package com.clt.netmessage;

import java.io.Serializable;

public class NMSetSenderColorTempRGB extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 547534850976390353L;

    protected int colorTempR = 255;

    protected int colorTempG = 255;

    protected int colorTempB = 255;

    public int getColorTempR()
    {
        return colorTempR;
    }

    public void setColorTempR(int colorTempR)
    {
        this.colorTempR = colorTempR;
    }

    public int getColorTempG()
    {
        return colorTempG;
    }

    public void setColorTempG(int colorTempG)
    {
        this.colorTempG = colorTempG;
    }

    public int getColorTempB()
    {
        return colorTempB;
    }

    public void setColorTempB(int colorTempB)
    {
        this.colorTempB = colorTempB;
    }

    public NMSetSenderColorTempRGB()
    {
        mType = NetMessageType.SetColorTempertureRGB;
    }

}
