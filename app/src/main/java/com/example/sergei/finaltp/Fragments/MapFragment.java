package com.example.sergei.finaltp.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.toolbox.Volley;
import com.example.sergei.finaltp.R;
import com.example.sergei.finaltp.RequestManager;
import com.example.sergei.finaltp.VolleyCallback;
import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.GeoObjectTapEvent;
import com.yandex.mapkit.layers.GeoObjectTapListener;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateSource;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MapFragment extends Fragment  {
    private final static String KEY_CORDS = "cords";
    private MapView mapView;
    private String adress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("MAP","MapFragment-onCreateView");

       // View view = inflater.inflate(R.layout.fragment_map, container, false);

//        mapView = new MapView(getActivity());
//        mapView.getMap().addCameraListener(cameraListener);
//        mapView.getMap().addInputListener(inputListener);
        //return view;
        return createView();

    }
    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        mapView = new MapView(getActivity());
        mapView.getMap().addCameraListener(cameraListener);
        mapView.getMap().addInputListener(inputListener);
        layout.addView(mapView);
        return layout;
    }

    CameraListener cameraListener = new CameraListener() {
        @Override
        public void onCameraPositionChanged(@NonNull Map map, @NonNull CameraPosition cameraPosition, @NonNull CameraUpdateSource cameraUpdateSource, boolean b) {
            Log.d("MAP","MapFragment-cameraListener: Position Changed");
        }
    };


      InputListener inputListener = new InputListener() {
        @Override
        public void onMapTap(@NonNull Map map, @NonNull Point point) {
            Log.d("MAP","MapFragment-inputListener-onMapTap-point: \n lat: "+point.getLatitude()+"lgt: "+point.getLongitude());
        }

        @Override
        public void onMapLongTap(@NonNull Map map, @NonNull Point point) {
            Log.d("MAP","MapFragment-inputListener-onMapLongTap-point: \n lat: "+point.getLatitude()+"lgt: "+point.getLongitude());
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MAP","MapFragment-onStart");
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MAP","MapFragment-onStop");
        mapView.getMap().addInputListener(null);
        mapView.onStop();
        MapKitFactory.getInstance().onStop();

    }
    public void showByAdress(String point){
        Log.d("MAP","MapFragment-showByAdress IS WORKING");
        Log.d("MAP","MapFragment-showByAdress-point(without converting): "+point);
        Float lat = Float.valueOf(point.split(" ")[1]);
        Float lgt = Float.valueOf(point.split(" ")[0]);
        Log.d("MAP","MapFragment-showByAdress \n lat: "+lat+" lgt:"+lgt+"\n"+"MOVING MAP");
        mapView.getMap().move(
                new CameraPosition(new Point(lat, lgt), 50.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }
        //Замутить отвязывание от лисеннеров


}
