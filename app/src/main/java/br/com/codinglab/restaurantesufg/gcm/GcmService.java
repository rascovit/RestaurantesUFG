package br.com.codinglab.restaurantesufg.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.main.MainActivity;

/**
 * Created by PC MASTER RACE on 01/07/2015.
 */
public class GcmService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public static final String TAG = "GCM Demo";

    public GcmService() {
        super("GcmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG,"Message Received");

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                for (int i = 0; i < 5; i++) {
                    Log.i(TAG, "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {

                        Thread.sleep(500);

                    } catch (InterruptedException e) {

                    }
                }
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                String json = String.valueOf(extras.getString("message"));
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String restauranteId = jsonObject.getString("id-restaurante");
                    String nomeRestaurante = jsonObject.getString("restaurante");

                    if(sharedPreferences.contains(restauranteId)){
                        sendNotification(nomeRestaurante + " recebeu atualizações de cardápio.");
                    }else{
                        sendNotification("Notificação não lhe interessa");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.

                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        Receiver.completeWakefulIntent(intent);
    }
    private void sendNotification(String msg) {

        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
