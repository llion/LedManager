package com.clt.commondata;

import java.io.Serializable;
import java.util.ArrayList;

public class LedTerminateInfoList implements Serializable
{

    protected ArrayList<LedTerminateInfo> terminateList;

    public LedTerminateInfoList()
    {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<LedTerminateInfo> getTerminateList()
    {
        return terminateList;
    }

    public void setTerminateList(ArrayList<LedTerminateInfo> terminateList)
    {
        this.terminateList = terminateList;
    }
}
