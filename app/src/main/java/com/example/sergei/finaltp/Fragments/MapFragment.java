package com.example.sergei.finaltp.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.sergei.finaltp.GridAdapter;
import com.example.sergei.finaltp.serializables.User;
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



public class MapFragment extends Fragment  {
    private final static String KEY_CORDS = "cords";
    private MapView mapView;
    private SearchFragment.OnFragmentActionListener mListener;
    private GridAdapter.onItemClickListener onItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("MAP","MapFragment-onCreateView");
        return createView();

    }
    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        mapView = new MapView(getActivity());
        mapView.getMap().addInputListener(inputListener);
        layout.addView(mapView);
        return layout;
    }

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
    public void showByLatLng(float lat, float lng){
        Log.d("MAP","MapFragment-showByLatLng IS WORKING");
        Log.d("MAP","MapFragment-showByLatLng-point(without converting): "+lat+" "+lng);
        mapView.getMap().move(
                new CameraPosition(new Point(lat, lng), 50.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }
        //Замутить отвязывание от лисеннеров

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                mListener = (SearchFragment.OnFragmentActionListener) context;

            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " должен реализовывать интерфейс OnFragmentActionListener");
            }
        }


    public interface OnFragmentActionListener {
        void onFragmentAction(String link);
        void onFragmentAction(User user);
    }

}
