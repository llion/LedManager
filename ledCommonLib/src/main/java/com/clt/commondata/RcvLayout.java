package com.clt.commondata;

import java.io.Serializable;

public class RcvLayout implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4362650359278044170L;

    public RcvLayout()
    {
        // TODO Auto-generated constructor stub
    }

    protected int tileWidth = 64;

    protected int tileHeight = 64;

    protected int colCount = 1;

    protected int rowCount = 1;

    protected int connectionStyle = 0;
}
