package com.clt.ledmanager.service;


/**
 * 
 *工厂
 */
public class BaseServiceFactory
{
    /**
     * 获得服务类
     */
    public static Class getBaseService(){
        
       return NetService.class;
    }
}
