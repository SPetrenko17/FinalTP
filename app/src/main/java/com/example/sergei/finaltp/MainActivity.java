package com.example.sergei.finaltp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergei.finaltp.Fragments.MapFragment;
import com.example.sergei.finaltp.Fragments.PlacesFragment;
import com.example.sergei.finaltp.Fragments.SearchFragment;
import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.Response;
import com.example.sergei.finaltp.serializables.User;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yandex.runtime.Runtime.getApplicationContext;


public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentActionListener,
        GridAdapter.onItemClickListener,
                                 MapFragment.OnFragmentActionListener
                                                             ,LocationListener
                                                                        , PlacesFragment.OnPlaceFragmentActionListener{

    static LocationManager locationManager;
    AlertDialog.Builder alertDialog;
    PlacesFragment placesFragment ;
    TextView currentPoint;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.map_layout)
    FrameLayout mapLayout;

    @BindView(R.id.place_layout)
    LinearLayout placeLayout;

    @BindView(R.id.nav_view)
    NavigationView navView;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkPermissions();

        MapKitFactory.setApiKey(getResources().getString(R.string.api_key));
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        placesFragment = (PlacesFragment) getFragmentManager().findFragmentById(R.id.placesFragment);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        View headView =navView.getHeaderView(0);
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        //if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            currentPoint = (TextView)headView.findViewById(R.id.current_point);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            onLocationChanged(location);
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        //}
        //currentPoint = (TextView)headView.findViewById(R.id.current_point);




        if (mapFragment != null) Log.d("MAP", "MainActivity-OnCreate-mapFragment is not null");




        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.user_map:
                        Log.d("MAP", "Show: map");
                        placeLayout.setVisibility(View.GONE);
                        mapLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.user_places:
                        placeLayout.setVisibility(View.VISIBLE);
                        mapLayout.setVisibility(View.GONE);
                        placesFragment = (PlacesFragment) getFragmentManager().findFragmentById(R.id.placesFragment);
                        Log.d("MAP", "Show: places");
                        break;
                    case R.id.user_settings:
                        openSettings();
                        Log.d("MAP", "Show: settings");
                        break;
                    case R.id.user_exit:
                        Log.d("MAP", "Show: exit");
                        exit();
                        break;
                }
                return true;
            }
        });



    }
    void openSettings(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            startActivity(intent);
        }
        else {
            final Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
        }
    }
    void exit(){
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getResources().getString(R.string.exit));  // заголовок
        alertDialog.setMessage(getResources().getString(R.string.exit_question));
        alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                System.exit(0);
            }
        });
        alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast toast = Toast.makeText(getApplicationContext(),getResources().getString(R.string.resumeWork),Toast.LENGTH_LONG);
                toast.show();

            }
        });
        alertDialog.show();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onFragmentAction(String adress) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            Log.d("MAP", "MainActivity-onFragmentAction(STRING)(onButtonPressed in SearchFragment)-mapFragment is NOT NULL");
            mapFragment.showByAdress(adress);
            mapFragment.getView().invalidate();
        } else {
            Log.d("MAP", "MainActivity-onFragmentAction(onButtonPressed in SearchFragment)-mapFragment is NULL");
        }
    }
    @Override
    public void onFragmentAction(User user) {
        PlacesFragment placesFragment = (PlacesFragment) getFragmentManager().findFragmentById(R.id.placesFragment);
        if (placesFragment != null) {
            Log.d("MAP", "MainActivity-onFragmentAction(USER)(onButtonPressed in SearchFragment)-placesFragment is NOT NULL");
             placesFragment.addPlace(user);
             placesFragment.getView().invalidate();



        } else {
            Log.d("MAP", "MainActivity-onFragmentAction(USER)(onButtonPressed in SearchFragment)-placesFragment is NULL");
        }
    }
    //в процессе
    @Override
    public void onPlaceFragmentAction(User user) {
        Log.d("MAP","onFrafmentAction geoobject");
        PlacesFragment placesFragment = (PlacesFragment) getFragmentManager().findFragmentById(R.id.placesFragment);
        try{
            placesFragment.addPlace(user);
        }
        catch (NullPointerException e){
            Log.e("ERROR",e.getMessage());
        }

    }



    @Override
    public void onFragmentChangeLoc(Point point) {
        PlacesFragment placesFragment = (PlacesFragment) getFragmentManager().findFragmentById(R.id.placesFragment);
        //Log.d("MAP", "Добавляем новый элемент в лист ");
        placesFragment.addPlace(point);
       // placesFragment.addPlace(new Place("test","test",(float) point.getLatitude(),(float) point.getLongitude()));
    }

    @Override // срабатывает при нажатии на диалоге
    public void onFragmentAddPoint(Point point) {
        MyTask myTask = new MyTask();
         myTask.execute((double)point.getLatitude(),(double)point.getLongitude());
    }

    @Override //Срабатывает по нажатию на элемент из recyclerview
    public void onItemClick(float lat, float lng) {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            Log.d("MAP", "MainActivity-onFragmentAction(STRING)(onButtonPressed in SearchFragment)-mapFragment is NOT NULL");
            mapFragment.showByLatLng(lat, lng);
            mapFragment.getView().invalidate();

            placeLayout.setVisibility(View.GONE);
            mapLayout.setVisibility(View.VISIBLE);
        } else {
            Log.d("MAP", "MainActivity-onFragmentAction(onButtonPressed in SearchFragment)-mapFragment is NULL");
        }

    }

    void testLoc(double lat, double lng){
        locationManager.addTestProvider (LocationManager.GPS_PROVIDER,
                "requiresNetwork" == "",
                    "requiresSatellite" == "",
                        "requiresCell" == "",
                            "hasMonetaryCost" == "",
                              "supportsAltitude" == "",
                                    "supportsSpeed" == "",
                                          "supportsBearing" == "",
                                                android.location.Criteria.POWER_LOW,
                android.location.Criteria.ACCURACY_FINE);

        Location newLocation = new Location(LocationManager.GPS_PROVIDER);

        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(1f);
        newLocation.setTime(System.currentTimeMillis());
        newLocation.setAccuracy(3);
        newLocation.setBearing(3);
        newLocation.setElapsedRealtimeNanos(1);
        newLocation.setSpeed(3);

        locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
        locationManager.setTestProviderStatus(LocationManager.GPS_PROVIDER,LocationProvider.AVAILABLE,null,System.currentTimeMillis());

        locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, newLocation);
    }



    private void checkPermissions() {
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        try {
            currentPoint.setText(location.getLatitude() + " " + location.getLongitude());
        }
        catch (NullPointerException e){
            Log.e("ERROR",e.getMessage());
            currentPoint.setText("ololo");
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class MyTask extends AsyncTask<Double, Double, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Double... values) {

            try {
                //был while(true)
                    testLoc(values[0], values[1]);
                    Log.d("MAP", "Подменяю данные на: "+values[0]+" - "+values[1]);
                    TimeUnit.SECONDS.sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }


    }
}



