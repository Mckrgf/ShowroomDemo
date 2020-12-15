package com.supcon.showroomdemo.util;

import android.content.Context;

import com.supcon.showroomdemo.common.Constants;
import com.yaobing.module_middleware.Utils.SharedPreferencesUtils;
import com.yaobing.module_middleware.Utils.SpUtil;

/**
 * @author : yaobing
 * @date : 2020/12/15 10:09
 * @desc :
 */
public class IPUtil {
    public static String getIp(Context context) {
        return SharedPreferencesUtils.getParam(context, Constants.IP_KEY, Constants.DEFAULT_IP);
    }

    public static void setIp(Context context,String ip) {
        SharedPreferencesUtils.setParam(context, Constants.IP_KEY, ip);
    }
    public static String getPort(Context context) {
        return SharedPreferencesUtils.getParam(context, Constants.PORT_KEY, Constants.DEFAULT_PORT);
    }

    public static void setPort(Context context,String ip) {
        SharedPreferencesUtils.setParam(context, Constants.PORT_KEY, ip);
    }

    public static String getHost(Context context) {
        return "http://" + getIp(context) + ":" + getPort(context);
    }
}
