package com.clt.ledmanager.activity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil
{
	/**
	 * 获得图片
	 * @param imageUrl
	 * @return
	 */
	public static byte[] httpGetImage(String imageUrl)
	{
		try
		{
			URL url = new URL(imageUrl);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5 * 1000);  
	        conn.setRequestMethod("GET");  
	        InputStream inStream = conn.getInputStream();  
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
	            return readStream(inStream);  
	        }  
		}
		catch (Exception e)
		{
			
		}
		return null;
		
	}
	/**
	 * 读取数据流
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception
	{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1)
		{
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}
}
