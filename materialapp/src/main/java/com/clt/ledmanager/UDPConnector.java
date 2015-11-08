package com.clt.ledmanager;

import android.os.Handler;
/**
 * UDP连接器
 */
public interface UDPConnector extends LifeCycle
{
    void setHandler(Handler mHandler);
}
