package com.clt.entity;

import java.io.Serializable;

/**
 * 上传文件的实体类
 */
public class UploadBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 2483070934291834307L;

    private String fileName;// 文件名

    private String filePath;// 文件路径

    private long fileSize;// 文件大小

    private String savePath;// 保存路径

    private long currentPosition;// 当前上传大小

    public UploadBean(String fileName, String filePath)
    {
        super();
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public UploadBean()
    {
        super();
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getSavePath()
    {
        return savePath;
    }

    public void setSavePath(String savePath)
    {
        this.savePath = savePath;
    }

    public long getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(long currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fileName == null) ? 0 : fileName.hashCode());
        return result;
    }

    public int getProgress()
    {
        if (fileSize < 1)
        {
            return 0;
        }
        return (int) (currentPosition * 100 / fileSize);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UploadBean other = (UploadBean) obj;
        if (fileName == null)
        {
            if (other.fileName != null)
                return false;
        }
        else if (!fileName.equals(other.fileName))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "UploadBean [fileName=" + fileName + ", filePath=" + filePath
                + ", fileSize=" + fileSize + ", savePath=" + savePath
                + ", currentPosition=" + currentPosition + "]";
    }

}
