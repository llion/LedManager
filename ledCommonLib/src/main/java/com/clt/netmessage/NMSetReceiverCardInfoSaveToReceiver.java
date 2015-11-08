package com.clt.netmessage;

import java.io.Serializable;

public class NMSetReceiverCardInfoSaveToReceiver extends NMBase implements Serializable
{


    /**
     * 
     */
    private static final long serialVersionUID = -4370427598548144139L;
    private String fileName;
    
    private int boxWidth;
    
    private int boxHeight;
    
    public NMSetReceiverCardInfoSaveToReceiver()
    {
        mType = NetMessageType.setReceiveCardSettingInfoSaveToReceiver;
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
