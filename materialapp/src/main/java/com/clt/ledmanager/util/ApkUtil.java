package com.clt.ledmanager.util;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.clt.ledmanager.activity.Application;

import java.util.Locale;

/**
 * APK全局工具类
 * 
 * @author Administrator
 * 
 */
public class ApkUtil
{
    public static final String TAG = ApkUtil.class.getSimpleName();
    public static final boolean DEBUG = true;
    /**
     * 获取系统语言
     * 
     * @return
     */
    public static String getSysLanguage()
    {
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        if (DEBUG) {
            Log.d(TAG, "language=" + language + ", country=" + country);
        }
        if (language.equalsIgnoreCase("zh")) {
            if ("cn".equalsIgnoreCase(country))
                return Const.ZH_rCN;
            else
                return Const.ZH_rTW;
        }
        return language;
    }

    public static void configLanguage(String language)
    {
        // Locale locale = new Locale(Application.getInstance().getLanguage()
        // .equals("cn") ? "zh" : "en");
        // Locale.setDefault(locale);
        Resources resources = Application.getInstance().getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (language.equalsIgnoreCase(Const.ZH_rCN)) {// 中文
            config.locale = Locale.SIMPLIFIED_CHINESE;
        }  else if (language.equalsIgnoreCase(Const.ZH_rTW)) { // 繁体字
            config.locale = Locale.TRADITIONAL_CHINESE;
        } else if (language.equalsIgnoreCase(Const.EN)) {// 英文
            config.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(config, metrics);
    }
}
