package com.clt.entity;

import java.io.Serializable;
/**
 * 连接关系的参数
 */
public class ConnectionParam implements Serializable
{

    private static final long serialVersionUID = -5247150801960164705L;
    private int mode;
    private int column;
    private int row;
    private int width;
    private int height;
    private int sender;
    private int port;
    public int getMode()
    {
        return mode;
    }
    public void setMode(int mode)
    {
        this.mode = mode;
    }
    public int getColumn()
    {
        return column;
    }
    public void setColumn(int column)
    {
        this.column = column;
    }
    public int getRow()
    {
        return row;
    }
    public void setRow(int row)
    {
        this.row = row;
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
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    public int getSender()
    {
        return sender;
    }
    public void setSender(int sender)
    {
        this.sender = sender;
    }
    public int getPort()
    {
        return port;
    }
    public void setPort(int port)
    {
        this.port = port;
    }
    
    
}
