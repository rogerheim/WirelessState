package com.aremaitch.glass.wirelessstate.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.TextUtils;
import android.util.Log;

import com.aremaitch.glass.wirelessstate.Constants;
import com.aremaitch.glass.wirelessstate.R;
import com.aremaitch.glass.wirelessstate.classes.BluetoothStateManager;
import com.aremaitch.glass.wirelessstate.classes.WifiStateManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SetWirelessStateSvc extends Service implements TextToSpeech.OnInitListener {
    private static final String UTTERANCE_ID = "1";

    TextToSpeech tts;

    public SetWirelessStateSvc() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(Constants.TAG, "initializing speech");
        tts = new TextToSpeech(this, this);
        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                if (utteranceId.equalsIgnoreCase(UTTERANCE_ID)) {
                    Log.v(Constants.TAG, "stopping service");
                    stopSelf();
                }
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(Constants.TAG, "shutting down speech");
        tts.shutdown();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.hasExtra(RecognizerIntent.EXTRA_RESULTS)) {
            ArrayList<String> voiceResults = intent.getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);

            if (voiceResults != null) {
                String mostLikely = voiceResults.get(0);
                if (!TextUtils.isEmpty(mostLikely)) {
                    if (mostLikely.equalsIgnoreCase(getString(R.string.wifi_on))) {
                        Log.v(Constants.TAG, "wifi on");
                        speakConfirmation(getString(R.string.wifi_on_confirmation));
//                        WifiStateManager.enableWifi(this);

                    } else if (mostLikely.equalsIgnoreCase(getString(R.string.wifi_off))) {
                        Log.v(Constants.TAG, "wifi off");
                        speakConfirmation(getString(R.string.wifi_off_confirmation));
//                        WifiStateManager.disableWifi(this);

                    } else if (mostLikely.equalsIgnoreCase(getString(R.string.bluetooth_on))) {
                        Log.v(Constants.TAG, "bluetooth on");
                        speakConfirmation(getString(R.string.bluetooth_on_confirmation));
//                        BluetoothStateManager.enableBluetooth();

                    } else if (mostLikely.equalsIgnoreCase(getString(R.string.bluetooth_off))) {
                        Log.v(Constants.TAG, "bluetooth off");
                        speakConfirmation(getString(R.string.bluetooth_off_confirmation));
//                        BluetoothStateManager.disableBluetooth();

                    } else if (mostLikely.equalsIgnoreCase(getString(R.string.cancel))) {
                        Log.v(Constants.TAG, "cancel");
                        speakConfirmation(getString(R.string.cancelled_confirmation));

                    } else {
                        //  We didn't recognize what the user said. If they didn't say anything, the system
                        //  will prompt them to tap to try again.
                    }
                }
            }
        }
//        stopSelf();
        return START_STICKY;
    }

    private String mConfirmation = "";
    private void speakConfirmation(String confirmation) {
        //  There's a race condition here; I must wait for the tts engine to be ready before trying to speak.
        //  I also can't shutdown the engine or the service until after the speech is complete.
        mConfirmation = confirmation;
//        tts.speak(confirmation, TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.v(Constants.TAG, "speech initialized");
            if (!TextUtils.isEmpty(mConfirmation)) {
                HashMap<String, String> speakParams = new HashMap<String, String>();
                speakParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, UTTERANCE_ID);
                tts.speak(mConfirmation, TextToSpeech.QUEUE_ADD, speakParams);
            }

        } else {
            Log.v(Constants.TAG, "speech failed to initialize");
        }
    }
}
