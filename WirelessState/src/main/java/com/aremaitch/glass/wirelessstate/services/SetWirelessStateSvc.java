package com.aremaitch.glass.wirelessstate.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.RecognizerIntent;

import java.util.ArrayList;

public class SetWirelessStateSvc extends Service {
    public SetWirelessStateSvc() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ArrayList<String> voiceResults = intent.getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

        stopSelf();
        return START_NOT_STICKY;
    }
}
