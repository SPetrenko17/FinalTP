package com.example.sergei.finaltp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sergei.finaltp.Fragments.MapFragment;
import com.example.sergei.finaltp.Fragments.PlacesFragment;
import com.example.sergei.finaltp.Fragments.SearchFragment;
import com.yandex.mapkit.MapKitFactory;


public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentActionListener   {
    private final static String KEY_CORDS = "cords";
    private DrawerLayout mDrawerLayout;
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
                            break;
                        case "Мои места":
                            placeLayout.setVisibility(View.VISIBLE);
                            mapLayout.setVisibility(View.GONE);
                            Log.d("MAP","Show: places");
                            break;
                        case "Мои маршруты":
                            Log.d("MAP","Show: routes");
                            break;
                        case "Выход":
                            Log.d("MAP","Show: exit");
                            break;
                    }
                return true;
            }
        });

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
    public void onFragmentAction(String link) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
       // PlacesFragment fragment2 = (PlacesFragment) getFragmentManager().findFragmentById(R.id.placesFragment);
        if (mapFragment != null ) {

            Log.d("MAP","MainActivity-onFragmentAction(onButtonPressed in SearchFragment)-mapFragment is NOT NULL");
            SharedPreferences pref =getPreferences(MODE_PRIVATE);
            mapFragment.showByAdress(pref.getString(KEY_CORDS,"0 0"));
            mapFragment.getView().invalidate();
        }
        else{
            Log.d("MAP","MainActivity-onFragmentAction(onButtonPressed in SearchFragment)-mapFragment is NULL");
        }

//        if(fragment2!=null){
//            Log.d("MAP","onButtonPressed  not null  fragment2 MAIN");
//            fragment2.
//        }
//        else{
//            Log.d("MAP","onButtonPressed  null fragment2 MAIN");
//        }

    }
    }



