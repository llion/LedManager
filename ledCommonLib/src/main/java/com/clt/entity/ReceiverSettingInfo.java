package com.clt.entity;

import java.io.Serializable;
/**接收卡参数设置***/

public class ReceiverSettingInfo implements Serializable
{
    private String fileName;

    private int refreshRate;// 刷新

    private int grayLevel;// 灰度

    private String serialClock;// 串钟

    private String gammaValue;// 伽马

    private String brightnessPercent;// 亮度等级

    private String muliple;// 刷新倍数

    private String grayMode;// 灰度等级

    private String blankingValue;// 消隐时间

    private String brightnessLevel;// 亮度等级

    private String minimumOE;// 最小OE
    
    private int width;//高
    
    private int height;//宽

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

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public int getRefreshRate()
    {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate)
    {
        this.refreshRate = refreshRate;
    }

    public int getGrayLevel()
    {
        return grayLevel;
    }

    public void setGrayLevel(int grayLevel)
    {
        this.grayLevel = grayLevel;
    }

    public String getSerialClock()
    {
        return serialClock;
    }

    public void setSerialClock(String serialClock)
    {
        this.serialClock = serialClock;
    }

    public String getGammaValue()
    {
        return gammaValue;
    }

    public void setGammaValue(String gammaValue)
    {
        this.gammaValue = gammaValue;
    }

    public String getBrightnessPercent()
    {
        return brightnessPercent;
    }

    public void setBrightnessPercent(String brightnessPercent)
    {
        this.brightnessPercent = brightnessPercent;
    }

    public String getMuliple()
    {
        return muliple;
    }

    public void setMuliple(String muliple)
    {
        this.muliple = muliple;
    }

    public String getGrayMode()
    {
        return grayMode;
    }

    public void setGrayMode(String grayMode)
    {
        this.grayMode = grayMode;
    }

    public String getBlankingValue()
    {
        return blankingValue;
    }

    public void setBlankingValue(String blankingValue)
    {
        this.blankingValue = blankingValue;
    }

    public String getBrightnessLevel()
    {
        return brightnessLevel;
    }

    public void setBrightnessLevel(String brightnessLevel)
    {
        this.brightnessLevel = brightnessLevel;
    }

    public String getMinimumOE()
    {
        return minimumOE;
    }

    public void setMinimumOE(String minimumOE)
    {
        this.minimumOE = minimumOE;
    }

    
    
}
