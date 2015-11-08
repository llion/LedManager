package com.clt.commondata;

import java.io.Serializable;

public class PortArea implements Serializable
{

    /** 
     * 
     */
    private static final long serialVersionUID = 7429769764868265663L;

    public PortArea(int startX, int starty, int width, int height)
    {
        super();
        this.startX = startX;
        this.starty = starty;
        this.width = width;
        this.height = height;
    }

    public PortArea()
    {
        super();
    }

    public int getStartX()
    {
        return startX;
    }

    public void setStartX(int startX)
    {
        this.startX = startX;
    }

    public int getStarty()
    {
        return starty;
    }

    public void setStarty(int starty)
    {
        this.starty = starty;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    protected int startX = 0;

    protected int starty = 0;

    protected int width = 0;

    protected int height = 0;

}
