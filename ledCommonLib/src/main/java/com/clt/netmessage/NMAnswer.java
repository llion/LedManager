package com.clt.netmessage;

import java.io.Serializable;

public class NMAnswer extends NMBase implements Serializable
{
    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getErrorDesp()
    {
        return errorDesp;
    }

    public void setErrorDesp(String errorDesp)
    {
        this.errorDesp = errorDesp;
    }

    protected int errorCode = 0;

    protected String errorDesp;
}
