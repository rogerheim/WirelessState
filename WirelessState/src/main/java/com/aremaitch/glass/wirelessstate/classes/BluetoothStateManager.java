package com.aremaitch.glass.wirelessstate.classes;

import android.bluetooth.BluetoothAdapter;

public class BluetoothStateManager {
    public static void enableBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.enable()) {
            // start listening for an ACTION_STATE_CHANGED message indicating that bluetooth's state has changed
        } else {
            // there was an error and bluetooth cannot be enabled
        }
    }

    public static void disableBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.disable()) {
            // start listening for an ACTION_STATE_CHANGED
        } else {
            // there was an error and bluetooth cannot be disabled
        }
    }
}
