package br.com.codinglab.restaurantesufg.main;

import android.annotation.TargetApi;
import android.content.Context;
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

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.gcm.GcmRegistro;
import br.com.codinglab.restaurantesufg.modelos.Campus;


public class MainActivity extends ActionBarActivity {

    private GcmRegistro gcmRegistro;
    static final String TAG = "GCM Demo";
    private Toolbar toolbar;
    GoogleCloudMessaging gcm;
    String regId;
    Context context;
    String SENDER_ID = "126111843784";
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setarConfiguracoesView();
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        gcmRegistro = new GcmRegistro(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new InicioFragment())
                    .commit();
        }

        if(gcmRegistro.checkPlayServices()){
            context = getApplicationContext();
            gcm = GoogleCloudMessaging.getInstance(this);
            regId = gcmRegistro.getRegistrationId(context);
            if(regId.isEmpty()){
                gcmRegistro.registerInBackground();
            }
            Log.i(TAG, "Registrado: " + regId);
        }else{
            Log.i(TAG, "Nenhum APK válido do Google Play Services encontrado.");
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setarConfiguracoesView() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryColorDark));
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
}
