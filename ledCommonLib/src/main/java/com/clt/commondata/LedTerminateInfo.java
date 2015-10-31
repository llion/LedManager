package com.clt.commondata;

import java.io.Serializable;

public class LedTerminateInfo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -282211188729209792L;

    protected String strName;

    protected String password;

    protected String ipAddress;

    public String getStrName()
    {
        return strName;
    }

    public void setStrName(String strName)
    {
        this.strName = strName;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
