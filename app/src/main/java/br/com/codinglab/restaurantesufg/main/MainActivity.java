package br.com.codinglab.restaurantesufg.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.Campus;
import br.com.codinglab.restaurantesufg.utils.Handler;


public class MainActivity extends ActionBarActivity {

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    GoogleCloudMessaging gcm;
    String regId;
    Context context;
    String SENDER_ID = "126111843784";

    static final String TAG = "GCM Demo";
    private Toolbar toolbar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryColorDark));

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new InicioFragment())
                    .commit();
        }

        if(checkPlayServices()){
            context = getApplicationContext();
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = getRegistrationId(context);
            if(regId.isEmpty()){
                registerInBackground();
            }
            Log.i(TAG, "Registered: " + regId);
        }else{
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class InicioFragment extends Fragment {

        private RecyclerView campusRecyclerView;
        private RecyclerView.Adapter campusAdapter;
        private RecyclerView.LayoutManager campusLayoutManager;

        public InicioFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ArrayList<Campus> listaCampus = new ArrayList<>();

            listaCampus.add(new Campus("Câmpus Colemar Natal e Silva",1,"Avenida Universitária"));
            listaCampus.add(new Campus("Câmpus Samambaia", 2, "Avenida Samambaia"));
            listaCampus.add(new Campus("Câmpus Goiás Velho", 3, "Avenida Dario Sampaio"));
            listaCampus.add(new Campus("Câmpus Jataí",4,"Avenida Jataí"));
            listaCampus.add(new Campus("Câmpus Catalão",5,"Rua das Amendoeiras"));

            // Referenciando a RV
            campusRecyclerView = (RecyclerView) rootView.findViewById(R.id.campus_recycler_view);

            // Criando e setando um LayoutManager para a RV
            campusLayoutManager = new LinearLayoutManager(getActivity());
            campusRecyclerView.setLayoutManager(campusLayoutManager);

            // Criando e especificando um Adapter para a RV
            campusAdapter = new CampusAdapter(getActivity(), listaCampus);
            campusRecyclerView.setAdapter(campusAdapter);

            return rootView;
        }

    }


    /*CGM*/
    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Toast.makeText(this,"Dispositivo não suportado",Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    private String getRegistrationId(Context context) {

        final SharedPreferences prefs = getGcmPreferences(context);
        //prefs.edit().remove(PROPERTY_REG_ID).commit();

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
    private SharedPreferences getGcmPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(),Context.MODE_PRIVATE);
    }
    private void registerInBackground() {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;

                    sendRegistrationIdToBackend(regId);
                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i(TAG, msg);
            }
        }.execute(null, null, null);


    }
    private void sendRegistrationIdToBackend(String regId) {
        String url = "http://codinglab.com.br/samuel/gcm/salvar_registro.php?regid=" + regId;
        Handler handler = new Handler();
        handler.makeServiceCall(url, Handler.GET);
    }
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    /*END-CGM*/
}
