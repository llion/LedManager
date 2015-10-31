package com.clt.commondata;

import java.io.Serializable;

/**
 * 探卡后返回的数据
 * @author Administrator
 *
 */
public class SenderInfo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -2139525335893860479L;

    public static final String T7 = "T7";

    public static final String IT7 = "iT7";

    public static final String IQ7 = "iQ7";

    public static final String IQ7E = "iQ7E";

    // protected String senderType;
    // protected int majorVersion = 0;
    // protected int minorVersion = 0;
    // protected int portCount = 2;
    //
    // protected boolean bShowOn = true;
    // protected int savedBright = 255;
    // protected int realTimeBright = 255;
    // protected int realTimeClrTemp = 6500;
    // protected int savedClrTemp = 6500;

    /*
     * 增加成员变量 caocong 2013.3.5
     */
    private int majorVersion = 0;// 大版本号

    private int minorVersion = 0;// 小版本号

    private PortArea ports[] =
        {
                new PortArea(), new PortArea(), new PortArea(), new PortArea()
        };

    private boolean bShowOn = true;// 是否显示

    private int realTimeBright = 255;// 实时亮度值

    private int savedBright = 255;// 保持的亮度值

    /*** 新添加属性**/
    private boolean bVirtual = false;// 是否是虚拟屏

    private int virtualType = 0;// 虚拟屏类型

    private String startTime;

    private String sumOfWorkTime;

    private int contrast = 0;// 对比对

    private int netWorkPackageGap = 0;// 网络包间隙

    private boolean isBigPacket = false;// 是否大包，

    private int colorTempAdjustRatio;// 色温调整系数 R,G,B,VR

    private int realTimeClrTemp = 6500;// 当前的色温

    private int savedClrTemp = 6500;// 保存的色温

    private boolean isAutoBright;// 是否自动亮度调节

    private int frameRate = 0;// 当前设置的发送卡发送图像的帧率 30.60.90

    private int vsyncNum;

    private int hsyncNum;

    private int deNum;

    private int resolutionWidth;// 分辨率宽度

    private int resolutionHeight;// 分辨率高度

    private int persistByte;// 保留字节

    private String senderType;// 发送卡型号

    private String temperature;// 温度

    private int portCount = 2;// 文档中没有

    private boolean realParamFlags;// 发送卡实时参数标志

    /*** 新添加属性**/
    private int saveGama = 40;// 发送卡上保存的gama

    private int realGama = 40;// 发送卡实时gama

    private int testMode = 0;// 测试模式

    private int rotate = 0;

    private String humidity;// 湿度

    private boolean bZeroDelay = false;// 是否启用0延迟

    private int tenBitFlag = 0;// 10bit标志 1 为10bit 2为12bit 其他为8bit

    private boolean bHDCP = false;// 是否启用DHCP

    private int inputType = 0;// 信号输入类型 1为DVI 其他为hdmi

    private int uid;// 发送卡ID

    private boolean authorizeOK;

    private int activeHeight = 0;// 有效高度

    private int activeWidth;// 有效宽度

    private int lockScreen = 0;//

    private String year, month, date, hour, minuter, second, day;

    public int getMajorVersion()
    {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion)
    {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion()
    {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion)
    {
        this.minorVersion = minorVersion;
    }

    public boolean isbShowOn()
    {
        return bShowOn;
    }

    public void setbShowOn(boolean bShowOn)
    {
        this.bShowOn = bShowOn;
    }

    public int getRealTimeBright()
    {
        return realTimeBright;
    }

    public void setRealTimeBright(int realTimeBright)
    {
        this.realTimeBright = realTimeBright;
    }

    public int getSavedBright()
    {
        return savedBright;
    }

    public void setSavedBright(int savedBright)
    {
        this.savedBright = savedBright;
    }

    public int getNetWorkPackageGap()
    {
        return netWorkPackageGap;
    }

    public void setNetWorkPackageGap(int netWorkPackageGap)
    {
        this.netWorkPackageGap = netWorkPackageGap;
    }

    public boolean isBigPacket()
    {
        return isBigPacket;
    }

    public void setBigPacket(boolean isBigPacket)
    {
        this.isBigPacket = isBigPacket;
    }

    public int getColorTempAdjustRatio()
    {
        return colorTempAdjustRatio;
    }

    public void setColorTempAdjustRatio(int colorTempAdjustRatio)
    {
        this.colorTempAdjustRatio = colorTempAdjustRatio;
    }

    public int getRealTimeClrTemp()
    {
        return realTimeClrTemp;
    }

    public void setRealTimeClrTemp(int realTimeClrTemp)
    {
        this.realTimeClrTemp = realTimeClrTemp;
    }

    public int getSavedClrTemp()
    {
        return savedClrTemp;
    }

    public void setSavedClrTemp(int savedClrTemp)
    {
        this.savedClrTemp = savedClrTemp;
    }

    public int getVsyncNum()
    {
        return vsyncNum;
    }

    public void setVsyncNum(int vsyncNum)
    {
        this.vsyncNum = vsyncNum;
    }

    public int getHsyncNum()
    {
        return hsyncNum;
    }

    public void setHsyncNum(int hsyncNum)
    {
        this.hsyncNum = hsyncNum;
    }

    public int getDeNum()
    {
        return deNum;
    }

    public void setDeNum(int deNum)
    {
        this.deNum = deNum;
    }

    public int getResolutionWidth()
    {
        return resolutionWidth;
    }

    public void setResolutionWidth(int resolutionWidth)
    {
        this.resolutionWidth = resolutionWidth;
    }

    public int getResolutionHeight()
    {
        return resolutionHeight;
    }

    public void setResolutionHeight(int resolutionHeight)
    {
        this.resolutionHeight = resolutionHeight;
    }

    public int getPersistByte()
    {
        return persistByte;
    }

    public void setPersistByte(int persistByte)
    {
        this.persistByte = persistByte;
    }

    public String getSenderType()
    {
        return senderType;
    }

    public void setSenderType(String senderType)
    {
        this.senderType = senderType;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public int getPortCount()
    {
        return portCount;
    }

    public void setPortCount(int portCount)
    {
        this.portCount = portCount;
    }

    public PortArea [] getPorts()
    {
        return ports;
    }

    public void setPorts(PortArea [] ports)
    {
        this.ports = ports;
    }

    public boolean isbVirtual()
    {
        return bVirtual;
    }

    public void setbVirtual(boolean bVirtual)
    {
        this.bVirtual = bVirtual;
    }

    public int getVirtualType()
    {
        return virtualType;
    }

    public void setVirtualType(int virtualType)
    {
        this.virtualType = virtualType;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getSumOfWorkTime()
    {
        return sumOfWorkTime;
    }

    public void setSumOfWorkTime(String sumOfWorkTime)
    {
        this.sumOfWorkTime = sumOfWorkTime;
    }

    public int getContrast()
    {
        return contrast;
    }

    public void setContrast(int contrast)
    {
        this.contrast = contrast;
    }

    public boolean isAutoBright()
    {
        return isAutoBright;
    }

    public void setAutoBright(boolean isAutoBright)
    {
        this.isAutoBright = isAutoBright;
    }

    public int getFrameRate()
    {
        return frameRate;
    }

    public void setFrameRate(int frameRate)
    {
        this.frameRate = frameRate;
    }

    public boolean isRealParamFlags()
    {
        return realParamFlags;
    }

    public void setRealParamFlags(boolean realParamFlags)
    {
        this.realParamFlags = realParamFlags;
    }

    public int getSaveGama()
    {
        return saveGama;
    }

    public void setSaveGama(int saveGama)
    {
        this.saveGama = saveGama;
    }

    public int getRealGama()
    {
        return realGama;
    }

    public void setRealGama(int realGama)
    {
        this.realGama = realGama;
    }

    public int getTestMode()
    {
        return testMode;
    }

    public void setTestMode(int testMode)
    {
        this.testMode = testMode;
    }

    public int getRotate()
    {
        return rotate;
    }

    public void setRotate(int rotate)
    {
        this.rotate = rotate;
    }

    public String getHumidity()
    {
        return humidity;
    }

    public void setHumidity(String humidity)
    {
        this.humidity = humidity;
    }

    public boolean isBZeroDelay()
    {
        return bZeroDelay;
    }

    public void setbZeroDelay(boolean bZeroDelay)
    {
        this.bZeroDelay = bZeroDelay;
    }

    public int getTenBitFlag()
    {
        return tenBitFlag;
    }

    public void setTenBitFlag(int tenBitFlag)
    {
        this.tenBitFlag = tenBitFlag;
    }

    public boolean isBHDCP()
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

    public int getUid()
    {
        return uid;
    }

    public void setUid(int uid)
    {
        this.uid = uid;
    }

    public boolean isAuthorizeOK()
    {
        return authorizeOK;
    }

    public void setAuthorizeOK(boolean authorizeOK)
    {
        this.authorizeOK = authorizeOK;
    }

    public int getActiveHeight()
    {
        return activeHeight;
    }

    public void setActiveHeight(int activeHeight)
    {
        this.activeHeight = activeHeight;
    }

    public int getActiveWidth()
    {
        return activeWidth;
    }

    public void setActiveWidth(int activeWidth)
    {
        this.activeWidth = activeWidth;
    }

    public int getLockScreen()
    {
        return lockScreen;
    }

    public void setLockScreen(int lockScreen)
    {
        this.lockScreen = lockScreen;
    }

    /**
     * 将发送卡回复的字节数据进行处理，获取有用的数据
     * 
     * @param buffer
     * @param len
     * @return
     */
    public boolean LoadFromBuffer(byte [] buffer, int len)
    {
        if (buffer[0] != (byte) 0xAA)
            return false;
        // 版本号
        majorVersion = (buffer[2] & 0xff);
        minorVersion = (buffer[3] & 0xff);

        /*************** 网口 ****************************/
        int startPos = 4;
        for (int i = 0; i < 4; i++)
        {
            PortArea port = ports[i];

            int y = (buffer[i * 8 + startPos + 1] & 0xff);
            y *= 256;
            y += (buffer[i * 8 + startPos + 0] & 0xff);
            port.setStarty(y);

            int height = (buffer[i * 8 + startPos + 3] & 0xff);
            height *= 256;
            height += (buffer[i * 8 + startPos + 2] & 0xff);
            port.setHeight(height);

            int x = (buffer[i * 8 + startPos + 5] & 0xff);
            x *= 256;
            x += (buffer[i * 8 + startPos + 4] & 0xff);
            port.setStartX(x);

            int width = (buffer[i * 8 + startPos + 7] & 0xff);
            width *= 256;
            width += (buffer[i * 8 + startPos + 6] & 0xff);
            port.setWidth(width);

        }

        bShowOn = ((buffer[36] & 0xff) > 0) ? true : false;
        realTimeBright = (buffer[37] & 0xff);
        savedBright = (buffer[38] & 0xff);

        netWorkPackageGap = (buffer[41] & 0xff);
        isBigPacket = ((buffer[42] & 0xff) > 0) ? true : false;
        /********* 色温调整系数 ***********/

        realTimeClrTemp = (buffer[47] & 0xff);
        realTimeClrTemp *= 256;
        realTimeClrTemp += (buffer[48] & 0xff);
        if (realTimeClrTemp == 65536)
            realTimeClrTemp = 6500;
        else if (realTimeClrTemp == 0)
            realTimeClrTemp = 6500;
        else if (realTimeClrTemp < 2000)
            realTimeClrTemp = 2000;
        else if (realTimeClrTemp > 10000)
            realTimeClrTemp = 10000;

        isAutoBright = ((buffer[58] & 0xff) > 0) ? true : false;
        frameRate = (buffer[59] & 0xff);

        vsyncNum = (buffer[63] & 0xff) + ((buffer[62] & 0xff) << 8)
                + ((buffer[61] & 0xff) << 16) + ((buffer[60] & 0xff) << 24);

        hsyncNum = (buffer[65] & 0xff) + ((buffer[64] & 0xff) << 8);
        deNum = (buffer[67] & 0xff) + ((buffer[66] & 0xff) << 8);

        resolutionWidth = (buffer[69] & 0xff) + (buffer[68] & 0xff) * 256;

        resolutionHeight = (buffer[71] & 0xff) + (buffer[70] & 0xff) * 256;

        int typeIndex = (buffer[80] & 0xff);
        if (typeIndex == 0x33)
        {
            int type = (buffer[81] & 0xff);
            if (type == 3)
            {
                senderType = IT7;
                portCount = 2;
            }
            else if (type == 1)
            {
                senderType = IQ7;
                portCount = 4;
            }
            else if (type == 2)
            {
                senderType = IQ7E;
                portCount = 4;
            }else{
                senderType = "unkown";
            }
        }
        else
        {
            senderType = T7;
            portCount = 2;
        }

        // year=(buffer[82] & 0xff);
        // month=(buffer[83] & 0xff);
        // date=(buffer[84] & 0xff);
        // hour=(buffer[85] & 0xff);
        // minuter=(buffer[85] & 0xff);
        // second=(buffer[85] & 0xff);
        // day=(buffer[86] & 0xff);
        year = handlerBCD(buffer[82], true);
        month = handlerBCD(buffer[83], false);
        date = handlerBCD(buffer[84], false);
        hour = handlerBCD(buffer[85], false);
        minuter = handlerBCD(buffer[85], true);
        second = handlerBCD(buffer[85], true);
        day = handlerBCD(buffer[86], false);

        int temperatureZ = (buffer[98] & 0xff);// 整数部分
        int temperatureX = (buffer[99] & 0xff);// 小数部分
        temperature = temperatureZ + "." + temperatureX;

        realParamFlags = ((buffer[100] & 0xff) > 0) ? true : false;

        testMode = (buffer[103] & 0xff);// 测试模式

        int humidityZ = (buffer[110] & 0xff);// 整数部分
        int humidityX = (buffer[111] & 0xff);// 小数部分
        humidity = humidityZ + "." + humidityX;

        bZeroDelay = ((buffer[114] & 0xff) > 0) ? true : false;// 是否0延迟

        int bit = (buffer[115] & 0xff);
        if (bit == 1)
        {
            tenBitFlag = 10;
        }
        else
        {
            bit = 8;
        }

        bHDCP = ((buffer[116] & 0xff) > 0) ? true : false;

        inputType = (buffer[117] & 0xff);
        try
        {
            activeHeight = (buffer[132] & 0xff) + ((buffer[131] & 0xff) << 8);
        }
        catch (Exception e)
        {
        }

        return true;
    }

    /**
     * 处理bcd码
     * @param b
     * @param flag 小于10时，是否要格式化 前面加0
     * @return
     */
    public String handlerBCD(byte b, boolean flag)
    {
        int value = b & 0xFF;
        if (flag && value < 10)
        {
            return "0" + Integer.toHexString(value);
        }
        return Integer.toHexString(value);
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public String getMonth()
    {
        return month;
    }

    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getHour()
    {
        return hour;
    }

    public void setHour(String hour)
    {
        this.hour = hour;
    }

    public String getMinuter()
    {
        return minuter;
    }

    public void setMinuter(String minuter)
    {
        this.minuter = minuter;
    }

    public String getSecond()
    {
        return second;
    }

    public void setSecond(String second)
    {
        this.second = second;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

}
