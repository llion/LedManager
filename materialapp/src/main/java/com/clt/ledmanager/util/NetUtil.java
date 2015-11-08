package com.clt.ledmanager.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * netutil.
 * @author Abelart.
 */
public class NetUtil
{
    private static final String TAG = "NetUtil";

    /**
     * 网络连接是否可用
     */
    public static boolean isConnnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager)
        {
            NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

            if (null != networkInfo)
            {
                for (NetworkInfo info : networkInfo)
                {
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否是wifi连接
     */
    public static boolean isWifiConnect(Context context)
    {

        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mWifi = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi.isConnected();

    }
    
    /**
     * 获得本机Ip地址
     * @return
     */
    public static String getIpAddress(Context context) { 
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        
        WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
        int ipAddress = wifiInfo.getIpAddress(); 
         
        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109 
        String ip = String.format("%d.%d.%d.%d", 
                (ipAddress & 0xff), 
                (ipAddress >> 8 & 0xff), 
                (ipAddress >> 16 & 0xff), 
                (ipAddress >> 24 & 0xff)); 
        return ip; 
         
    } 
    
    /**
     * 获得本机Ip地址的前三位
     * @return
     */
    public static String getNetNumIpAddress(Context context) { 
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        
        WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
        int ipAddress = wifiInfo.getIpAddress(); 
         
        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109 
        String ip = String.format("%d.%d.%d", 
                (ipAddress & 0xff), 
                (ipAddress >> 8 & 0xff), 
                (ipAddress >> 16 & 0xff)); 
        return ip; 
         
    } 

}