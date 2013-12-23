package com.aremaitch.glass.wirelessstate.classes;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Date: 12/23/13
 * Time: 4:05 PM
 */
public class WifiStateManager {
    public static void enableWifi(Context ctx) {
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    public static void disableWifi(Context ctx) {
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);
    }
}
