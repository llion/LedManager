package com.clt.netmessage;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class NMSetDayPeriodBright extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -7643678161817131025L;

    private LinkedHashMap<String, Integer> maps = new LinkedHashMap<String, Integer>();

    public LinkedHashMap<String, Integer> getMaps()
    {
        return maps;
    }

    public void setMaps(LinkedHashMap<String, Integer> maps)
    {
        this.maps = maps;
    }

    public NMSetDayPeriodBright()
    {
        mType = NetMessageType.setDayPeriodBright;
    }
}
