package com.clt.netmessage;

import java.io.Serializable;

public class NMFindTerminateAnswer extends NMAnswer implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 855521138809107844L;

    protected String terminateName;

    protected String password;

    public String imgVersion = "5.2.9";

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getTerminateName()
    {
        return terminateName;
    }

    public void setTerminateName(String mTerminateName)
    {
        this.terminateName = mTerminateName;
    }

    public NMFindTerminateAnswer()
    {
        mType = NetMessageType.FindTerminateAnswer;
    }

}
