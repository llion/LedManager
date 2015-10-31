package com.clt.entity;

import java.io.Serializable;

/**
 * 节目
 * @author Administrator
 *
 */
public class Program implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7413700004989193254L;
    
    public static final int UDISK=1;//U盘中
    
    public static final int SDCARD=2;//sd卡中
    
    public static final int INTERNAL_STORAGE=3;//内部存储

    private String fileName;

    private String path;// 路径
    
    private int pathType;//路径的类型
    
    private long size;
    
    private String createTime;
    
    private boolean isPlaying;
    
    public Program()
    {
        super();
    }

    
    public Program(String fileName, String path, int pathType)
    {
        super();
        this.fileName = fileName;
        this.path = path;
        this.pathType = pathType;
    }


    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public int getPathType()
    {
        return pathType;
    }

    public void setPathType(int pathType)
    {
        this.pathType = pathType;
    }

    
    

    public long getSize()
    {
        return size;
    }


    public void setSize(long size)
    {
        this.size = size;
    }


    public String getCreateTime()
    {
        return createTime;
    }


    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    
    public boolean isPlaying()
	{
		return isPlaying;
	}


	public void setPlaying(boolean isPlaying)
	{
		this.isPlaying = isPlaying;
	}


	@Override
    public String toString()
    {
        return "Program [fileName=" + fileName + ", path=" + path
                + ", pathType=" + pathType + ", size=" + size + ", createTime="
                + createTime + "]";
    }


    
    
}
