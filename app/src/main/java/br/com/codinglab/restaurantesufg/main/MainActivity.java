package br.com.codinglab.restaurantesufg.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;


public class MainActivity extends ActionBarActivity {

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new InicioFragment())
                    .commit();
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

            try {
                ArrayList<Restaurante> listaRestaurantes = new RestaurantesAsyncTask().execute("campus2").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //MOCK DE CAMPUS UNIVERSITÁRIOS
            ArrayList<String> campus = new ArrayList<>();
            campus.add("Câmpus Colemar Natal e Silva");
            campus.add("Câmpus Samambaia");
            campus.add("Câmpus Goiás");
            campus.add("Câmpus Jataí");
            campus.add("Câmpus Catalão");

            ArrayList<String> enderecosCampus = new ArrayList<>();
            enderecosCampus.add("Avenida Universitária");
            enderecosCampus.add("Avenida Samambaia");
            enderecosCampus.add("Avenida Dario Sampaio");
            enderecosCampus.add("Avenida Jataí");
            enderecosCampus.add("Rua das amendoeiras");

            // Referenciando a RV
            campusRecyclerView = (RecyclerView) rootView.findViewById(R.id.campus_recycler_view);

            // Criando e setando um LayoutManager para a RV
            campusLayoutManager = new LinearLayoutManager(getActivity());
            campusRecyclerView.setLayoutManager(campusLayoutManager);

            // Criando e especificando um Adapter para a RV
            campusAdapter = new CampusAdapter(getActivity(), campus, enderecosCampus);
            campusRecyclerView.setAdapter(campusAdapter);

            return rootView;
        }
    }
}
