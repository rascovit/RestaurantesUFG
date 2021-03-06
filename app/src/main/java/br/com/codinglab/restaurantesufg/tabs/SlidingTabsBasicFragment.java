package br.com.codinglab.restaurantesufg.tabs;

/**
 * Created by thiagodurante on 29/06/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.codinglab.restaurantesufg.R;
import br.com.codinglab.restaurantesufg.modelos.Restaurante;

public class SlidingTabsBasicFragment extends Fragment {

    private GoogleMap mapa = null;
    private String nomeRestaurante;
    private String enderecoRestaurante;
    private View view;
    private boolean inflouMapa = false;

    //OBJETO RESTAURANTE
    private Restaurante restaurante;

    private FloatingActionButton botaoFavoritarRestaurante;

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        restaurante = (Restaurante) getArguments().getSerializable("objetoRestaurante");
        nomeRestaurante = restaurante.getNomeRestaurante();
        enderecoRestaurante = restaurante.getLocalizacaoRestaurante().getEnderecoRestaurante();
        setRetainInstance(false);
        return rootView;
    }

    // BEGIN_INCLUDE (fragment_onviewcreated)
    /**
     * This is called after the {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} has finished.
     * Here we can pick out the {@link View}s we need to configure from the content view.
     *
     * We set the {@link ViewPager}'s adapter to be an instance of {@link SamplePagerAdapter}. The
     * {@link SlidingTabLayout} is then given the {@link ViewPager} so that it can populate itself.
     *
     * @param view View created in {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 21) {
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
            toolbar.setElevation(0);
        }

        // BEGIN_INCLUDE (setup_viewpager)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        // END_INCLUDE (setup_viewpager)

        // BEGIN_INCLUDE (setup_slidingtablayout)
        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if(getFragmentManager().findFragmentById(R.id.mapa_restaurantes) != null){
            Fragment fragment = (getFragmentManager().findFragmentById(R.id.mapa_restaurantes));
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.remove(fragment).commitAllowingStateLoss();
        }
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
     * The individual pages are simple and just display two lines of text. The important section of
     * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
     * {@link SlidingTabLayout}.
     */
    class SamplePagerAdapter extends PagerAdapter {

        String[] tabText = getResources().getStringArray(R.array.tabs);

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return 3;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        // BEGIN_INCLUDE (pageradapter_getpagetitle)
        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return tabText[position];
        }
        // END_INCLUDE (pageradapter_getpagetitle)

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(position == 0){
                view = getActivity().getLayoutInflater().inflate(R.layout.tab_detalhes_restaurante, container, false);
                container.addView(view);

                // COMPLETANDO OS DADOS NA TELA
                TextView nomeRestauranteTextView = (TextView) view.findViewById(R.id.nomeRestaurante_textView);
                nomeRestauranteTextView.setText(restaurante.getNomeRestaurante());
                TextView restauranteEnderecoTextView = (TextView) view.findViewById(R.id.restauranteEndereco_textView);
                restauranteEnderecoTextView.setText(restaurante.getLocalizacaoRestaurante().getEnderecoRestaurante() + ". " +restaurante.getLocalizacaoRestaurante().getCampus());
                TextView cafeDaManhaTextView = (TextView) view.findViewById(R.id.cafeDaManha_textView);
                cafeDaManhaTextView.setText(restaurante.getHorarioCafe().getInicio() + " às " +restaurante.getHorarioCafe().getFim());
                TextView almocoTextView = (TextView) view.findViewById(R.id.almoco_textView);
                almocoTextView.setText(restaurante.getHorarioAlmoco().getInicio() + " às " +restaurante.getHorarioAlmoco().getFim());
                TextView jantarTextView = (TextView) view.findViewById(R.id.jantar_textView);
                jantarTextView.setText(restaurante.getHorarioJantar().getInicio() + " às " +restaurante.getHorarioJantar().getFim());
                TextView restauranteValorMinimoTextView = (TextView) view.findViewById(R.id.restauranteValorMinimo_textView);
                restauranteValorMinimoTextView.setText("Refeições à partir de R$" +restaurante.getValorMinino());
                TextView pontoDeReferenciaTextView = (TextView) view.findViewById(R.id.pontoDeReferencia_textView);
                pontoDeReferenciaTextView.setText(restaurante.getLocalizacaoRestaurante().getPontoDeReferencia());
                TextView estiloDeServirTextView = (TextView) view.findViewById(R.id.estiloDeServir_textView);
                estiloDeServirTextView.setText(restaurante.getEstiloDeServir());

                // BOTÃO 'FAVORITAR RESTAURANTE'
                botaoFavoritarRestaurante = (FloatingActionButton) view.findViewById(R.id.botaoFavoritarRestaurante);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);

                if(sharedPreferences.contains(String.valueOf(restaurante.getId()))){
                    botaoFavoritarRestaurante.setImageResource(R.mipmap.ic_star_favoritado);
                    botaoFavoritarRestaurante.setPressed(true);
                }

                botaoFavoritarRestaurante.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (sharedPreferences.contains(String.valueOf(restaurante.getId()))) {
                            editor.remove(String.valueOf(restaurante.getId())).commit();
                            botaoFavoritarRestaurante.setImageResource(R.mipmap.ic_star);
                            botaoFavoritarRestaurante.setPressed(false);
                            if (Build.VERSION.SDK_INT >= 21) {
                                botaoFavoritarRestaurante.setElevation(6);
                            }
                            Toast.makeText(getActivity(), "Removido com sucesso dos favoritos", Toast.LENGTH_SHORT).show();
                        } else {
                            editor.putString(String.valueOf(restaurante.getId()),"favorito").commit();
                            botaoFavoritarRestaurante.setImageResource(R.mipmap.ic_star_favoritado);
                            botaoFavoritarRestaurante.setPressed(true);
                            if (Build.VERSION.SDK_INT >= 21) {
                                botaoFavoritarRestaurante.setElevation(12);
                            }
                            Toast.makeText(getActivity(), "Adicionado com sucesso aos favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            if(position == 1){
                view = getActivity().getLayoutInflater().inflate(R.layout.tab_cardapio, container, false);
                RecyclerView cardapioRecyclerView = (RecyclerView) view.findViewById(R.id.cardapio_recyclerView);
                RecyclerView.LayoutManager cardapioLayoutManager = new LinearLayoutManager(getActivity());
                RecyclerView.Adapter cardapioAdapter = new CardapioAdapter(restaurante, getActivity());
                cardapioRecyclerView.setAdapter(cardapioAdapter);
                cardapioRecyclerView.setLayoutManager(cardapioLayoutManager);
                container.addView(view);
            }
            if (position == 2 && !inflouMapa) {
                inflouMapa = true;
                view = getActivity().getLayoutInflater().inflate(R.layout.tab_mapa, container, false);
                if(mapa == null) {
                    mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapa_restaurantes)).getMap();
                    mapa.setMyLocationEnabled(true);
                    LatLng localizacaoRestaurante = new LatLng(Double.parseDouble(restaurante.getLocalizacaoRestaurante().getLatitude()), Double.parseDouble(restaurante.getLocalizacaoRestaurante().getLongitude()));
                    mapa.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);
                    CameraPosition posicaoCamera = new CameraPosition.Builder()
                            .target(localizacaoRestaurante)
                            .zoom(15)
                            .build();
                    mapa.animateCamera(CameraUpdateFactory.newCameraPosition(posicaoCamera));
                    mapa.addMarker(new MarkerOptions()
                            .position(localizacaoRestaurante)
                            .snippet(enderecoRestaurante)
                            .title(nomeRestaurante)).showInfoWindow();
                }
                // Add the newly created View to the ViewPager
                container.addView(view);
            }

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position != 2) {
                container.removeView((View) object);
            }
        }
    }
}