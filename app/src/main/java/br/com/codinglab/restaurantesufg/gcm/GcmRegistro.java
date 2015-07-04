package br.com.codinglab.restaurantesufg.gcm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import br.com.codinglab.restaurantesufg.main.MainActivity;
import br.com.codinglab.restaurantesufg.utils.Handler;

/**
 * Created by Gabriel on 04/07/2015.
 */
public class GcmRegistro {

    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    GoogleCloudMessaging gcm;
    String regId;
    Context context;
    String SENDER_ID = "126111843784";
    Activity activity;
    static final String TAG = "GCM Demo";

    public GcmRegistro(Activity activity) {
        this.activity = activity;
    }

    /*CGM*/
    public boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Toast.makeText(activity, "Dispositivo não suportado", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    public String getRegistrationId(Context context) {

        final SharedPreferences prefs = getGcmPreferences(context);
        //prefs.edit().remove(PROPERTY_REG_ID).commit();

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registro não encontrado.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "Versão da aplicação mudou.");
            return "";
        }
        return registrationId;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Não conseguiu obter o pacote: " + e);
        }
    }
    public SharedPreferences getGcmPreferences(Context context) {
        return activity.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
    }
    public void registerInBackground() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(SENDER_ID);
                    msg = "Dispositivo registrado. ID=" + regId;

                    sendRegistrationIdToBackend(regId);
                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Erro :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i(TAG, msg);
            }
        }.execute(null, null, null);


    }
    public void sendRegistrationIdToBackend(String regId) {
        String url = "http://codinglab.com.br/samuel/gcm/salvar_registro.php?regid=" + regId;
        Handler handler = new Handler();
        handler.makeServiceCall(url, Handler.GET);
    }
    public void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Salvando regId na versão " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    /*END-CGM*/
}
