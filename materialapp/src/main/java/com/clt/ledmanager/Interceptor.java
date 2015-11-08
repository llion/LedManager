package com.clt.ledmanager;

import com.clt.netmessage.NMBase;

/**
 * 消息拦截器
 */
public interface Interceptor
{
    void filterRequest(NMBase nmBase);
    void filterResponse(String jsonStr);
}
