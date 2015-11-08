package com.clt.netmessage;

import java.io.Serializable;

public class NMSetReceiverCardInfoSender extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1096964454359596572L;

    private String fileName;
    
    private int boxWidth;
    
    private int boxHeight;
    
    public NMSetReceiverCardInfoSender()
    {
        mType = NetMessageType.setReceiveCardSettingInfoSend;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public int getBoxWidth()
    {
        return boxWidth;
    }

    public void setBoxWidth(int boxWidth)
    {
        this.boxWidth = boxWidth;
    }

    public int getBoxHeight()
    {
        return boxHeight;
    }

    public void setBoxHeight(int boxHeight)
    {
        this.boxHeight = boxHeight;
    }
    
    
}
