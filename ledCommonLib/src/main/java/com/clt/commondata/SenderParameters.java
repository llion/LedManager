package com.clt.commondata;

import java.io.Serializable;



import android.R.integer;

/**
 * 保存到发送卡的数据
 * @author Administrator
 *
 */
public class SenderParameters implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8084327763353601376L;

    private boolean bBigPack = false; // 是否大包

    private boolean bAutoBright = false;// 是否自动设置

    private int m_frameRate = 60;// 帧率

    private boolean realParamFlag = false;// 发送卡实时参数标志

    private boolean bZeroDelay = false;// 是否启用0延迟

    private int rgbBitsFlag = 0;

    private boolean bHDCP = false;

    private int inputType = 0; // 信号输入类型 1为DVI 其他为hdmi
    
    protected PortArea ports[] =
        {
                new PortArea(), new PortArea(), new PortArea(), new PortArea()
        };

    public boolean isbBigPack()
    {
        return bBigPack;
    }

    public void setbBigPack(boolean bBigPack)
    {
        this.bBigPack = bBigPack;
    }

    public boolean isbAutoBright()
    {
        return bAutoBright;
    }

    public void setbAutoBright(boolean bAutoBright)
    {
        this.bAutoBright = bAutoBright;
    }

    public int getM_frameRate()
    {
        return m_frameRate;
    }

    public void setM_frameRate(int m_frameRate)
    {
        this.m_frameRate = m_frameRate;
    }

    public boolean isRealParamFlag()
    {
        return realParamFlag;
    }

    public void setRealParamFlag(boolean realParamFlag)
    {
        this.realParamFlag = realParamFlag;
    }

    public boolean isbZeroDelay()
    {
        return bZeroDelay;
    }

    public void setbZeroDelay(boolean bZeroDelay)
    {
        this.bZeroDelay = bZeroDelay;
    }

    public int getRgbBitsFlag()
    {
        return rgbBitsFlag;
    }

    public void setRgbBitsFlag(int rgbBitsFlag)
    {
        this.rgbBitsFlag = rgbBitsFlag;
    }

    public boolean isbHDCP()
    {
        return bHDCP;
    }

    public void setbHDCP(boolean bHDCP)
    {
        this.bHDCP = bHDCP;
    }

    public int getInputType()
    {
        return inputType;
    }

    public void setInputType(int inputType)
    {
        this.inputType = inputType;
    }

    public PortArea [] getPorts()
    {
        return ports;
    }

    public void setPorts(PortArea [] ports)
    {
        this.ports = ports;
    }

}
