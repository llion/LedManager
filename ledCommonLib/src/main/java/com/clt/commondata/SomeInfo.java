package com.clt.commondata;

import java.io.Serializable;

public class SomeInfo implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 4092146946532192572L;
    //
    private long sdTotalSize;
    private long sdAviableSize;
    
    private String ledServerVersion;
    private String colorlightVersion;
    private String imgVersion;
    public long getSdTotalSize()
    {
        return sdTotalSize;
    }
    public void setSdTotalSize(long sdTotalSize)
    {
        this.sdTotalSize = sdTotalSize;
    }
    public long getSdAviableSize()
    {
        return sdAviableSize;
    }
    public void setSdAviableSize(long sdAviableSize)
    {
        this.sdAviableSize = sdAviableSize;
    }
    public String getLedServerVersion()
    {
        return ledServerVersion;
    }
    public void setLedServerVersion(String ledServerVersion)
    {
        this.ledServerVersion = ledServerVersion;
    }
    public String getImgVersion()
    {
        return imgVersion;
    }
    public void setImgVersion(String imgVersion)
    {
        this.imgVersion = imgVersion;
    }
	public String getColorlightVersion()
	{
		return colorlightVersion;
	}
	public void setColorlightVersion(String colorlightVersion)
	{
		this.colorlightVersion = colorlightVersion;
	}
    
}
