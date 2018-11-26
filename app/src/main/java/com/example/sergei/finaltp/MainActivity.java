package com.example.sergei.finaltp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sergei.finaltp.Fragments.MapFragment;
import com.example.sergei.finaltp.Fragments.PlacesFragment;
import com.example.sergei.finaltp.Fragments.SearchFragment;
import com.example.sergei.finaltp.serializables.User;
import com.yandex.mapkit.MapKitFactory;


public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentActionListener , GridAdapter.onItemClickListener   {
    private final static String KEY_CORDS = "cords";
    private DrawerLayout mDrawerLayout;
    TextView currentPoint;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(getResources().getString(R.string.api_key));
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null ) {
            Log.d("MAP","MainActivity-OnCreate-mapFragment is not null");
        }
        final LinearLayout mapLayout = findViewById(R.id.map_layout);
        final LinearLayout placeLayout = findViewById(R.id.place_layout);
        final LinearLayout settingsLayout = findViewById(R.id.settings_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                    switch(item.toString()){
                        case "Карта":
                            Log.d("MAP","Show: map");
                            placeLayout.setVisibility(View.GONE);
                            mapLayout.setVisibility(View.VISIBLE);
                            settingsLayout.setVisibility(View.GONE);
                            break;
                        case "Мои места":
                            placeLayout.setVisibility(View.VISIBLE);
                            mapLayout.setVisibility(View.GONE);
                            settingsLayout.setVisibility(View.GONE);
                            Log.d("MAP","Show: places");
                            break;
                        case "Мои маршруты":
                            Log.d("MAP","Show: routes");
                            break;
                        case "Настройки":
                            placeLayout.setVisibility(View.GONE);
                            mapLayout.setVisibility(View.GONE);
                            settingsLayout.setVisibility(View.VISIBLE);

                            Log.d("MAP","Show: settings");
                            break;
                        case "Выход":
                            Log.d("MAP","Show: exit");
                            break;
                    }
                return true;
            }
        });
        currentPoint=findViewById(R.id.currentPoint);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //currentPoint.setText(String.valueOf(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)));

    }
        @Override
        protected void onStop () {
            super.onStop();
        }
        @Override
        protected void onStart () {
            super.onStart();
        }
    @Override
    public void onFragmentAction(String adress) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null ) {
            Log.d("MAP","MainActivity-onFragmentAction(STRING)(onButtonPressed in SearchFragment)-mapFragment is NOT NULL");
            mapFragment.showByAdress(adress);
            mapFragment.getView().invalidate();
        }
        else{
            Log.d("MAP","MainActivity-onFragmentAction(onButtonPressed in SearchFragment)-mapFragment is NULL");
        }
    }
    @Override
    public void onFragmentAction(User user) {
        PlacesFragment placesFragment = (PlacesFragment)getFragmentManager().findFragmentById(R.id.placesFragment);
        if(placesFragment!=null){
            Log.d("MAP","MainActivity-onFragmentAction(USER)(onButtonPressed in SearchFragment)-placesFragment is NOT NULL");
            placesFragment.addPlace(user);
            placesFragment.getView().invalidate();

        }
        else{
            Log.d("MAP","MainActivity-onFragmentAction(USER)(onButtonPressed in SearchFragment)-placesFragment is NULL");
        }
    }

    @Override
    public void onItemClick(float lat, float lng) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null ) {
            Log.d("MAP","MainActivity-onFragmentAction(STRING)(onButtonPressed in SearchFragment)-mapFragment is NOT NULL");
            mapFragment.showByLatLng(lat,lng);
            mapFragment.getView().invalidate();
        }
        else{
            Log.d("MAP","MainActivity-onFragmentAction(onButtonPressed in SearchFragment)-mapFragment is NULL");
        }

    }
}



