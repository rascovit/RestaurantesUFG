package br.com.codinglab.restaurantesufg.tabs;

/**
 * Created by thiagodurante on 29/06/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.codinglab.restaurantesufg.R;

/**
 * A basic sample which shows how to use {@link com.example.android.common.view.SlidingTabLayout}
 * to display a custom {@link ViewPager} title strip which gives continuous feedback to the user
 * when scrolling.
 */
public class SlidingTabsBasicFragment extends Fragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";
    private GoogleMap mapa;
    private String[] coordenadasRestaurantes;
    private String nomeRestaurante;
    private String enderecoRestaurante;
    private static View view;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sample, container, false);
        coordenadasRestaurantes = getArguments().getStringArray("coordenadasRestaurante");
        nomeRestaurante = getArguments().getString("nomeRestaurante");
        enderecoRestaurante = getArguments().getString("enderecoRestaurante");
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
                return getResources().getColor(R.color.primaryColorDark);
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
        // END_INCLUDE (setup_slidingtablayout)
    }
    // END_INCLUDE (fragment_onviewcreated)

    @Override
    public void onDestroy() {
        Log.d("OK", "OK");
        mViewPager.removeView(view);
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
            //View view = null;
            if(position == 0){
                // Inflate a new layout from our resources
                view = getActivity().getLayoutInflater().inflate(R.layout.tab_detalhes_restaurante, container, false);
                // Add the newly created View to the ViewPager
                container.addView(view,position);
            }
            if(position == 1){
                // Inflate a new layout from our resources
                view = getActivity().getLayoutInflater().inflate(R.layout.tab_cardapio, container, false);
                // Add the newly created View to the ViewPager
                container.addView(view,position);
            }
            if (position == 2 && mapa == null){
                view = getActivity().getLayoutInflater().inflate(R.layout.tab_mapa, container, false);
                mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapa_restaurantes)).getMap();
                mapa.setMyLocationEnabled(true);
                LatLng localizacaoRestaurante = new LatLng(Double.parseDouble(coordenadasRestaurantes[0]), Double.parseDouble(coordenadasRestaurantes[1]));
                mapa.addMarker(new MarkerOptions()
                        .position(localizacaoRestaurante)
                        .snippet(enderecoRestaurante)
                        .title(nomeRestaurante)).showInfoWindow();
                mapa.animateCamera(CameraUpdateFactory.zoomTo(14), 1000, null);
                CameraPosition posicaoCamera = new CameraPosition.Builder()
                        .target(localizacaoRestaurante)      // Sets the center of the map to Mountain View
                        .zoom(15)                   // Sets the zoom
                        .build();                   // Creates a CameraPosition from the builder
                mapa.animateCamera(CameraUpdateFactory.newCameraPosition(posicaoCamera));
                // Add the newly created View to the ViewPager
                container.addView(view,position);
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
            container.removeView((View) object);
        }
    }
}