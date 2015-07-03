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
        ComponentName comp = new ComponentName(context.getPackageName(),  Receiver.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}

