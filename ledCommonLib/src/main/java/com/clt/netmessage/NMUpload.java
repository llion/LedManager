package com.clt.netmessage;

import java.io.Serializable;

public class NMUpload extends NMBase implements Serializable
{
    public Type type;

    public enum Type
    { // 类型
        IsDownloded, Add, start, Pause, Delete
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public NMUpload()
    {
        mType = NetMessageType.upload;
    }
}
