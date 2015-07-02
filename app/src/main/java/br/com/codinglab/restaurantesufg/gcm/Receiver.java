package br.com.codinglab.restaurantesufg.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by PC MASTER RACE on 01/07/2015.
 */

public class Receiver extends WakefulBroadcastReceiver {
    public static final String TAG = "GCM Demo";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "GCM Broadcaster Started");
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),  Receiver.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}

