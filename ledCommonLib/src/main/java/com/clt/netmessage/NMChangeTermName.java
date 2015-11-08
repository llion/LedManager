package com.clt.netmessage;

import java.io.Serializable;

public class NMChangeTermName extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -6744311450976242851L;

    private String termName;

    public NMChangeTermName()
    {
        mType = NetMessageType.ModifyTerminateName;
    }

    public String getTermName()
    {
        return termName;
    }

    public void setTermName(String termName)
    {
        this.termName = termName;
    }

}
