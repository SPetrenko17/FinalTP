package com.example.sergei.finaltp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;

import com.example.sergei.finaltp.AdressCallback;
import com.example.sergei.finaltp.CordsCallback;
import com.example.sergei.finaltp.GridAdapter;
import com.example.sergei.finaltp.MainActivity;
import com.example.sergei.finaltp.Place;

import com.example.sergei.finaltp.R;
import com.example.sergei.finaltp.RequestManager;
import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.yandex.mapkit.geometry.Point;
import java.util.ArrayList;
public class PlacesFragment extends android.app.Fragment {

    RequestManager serverManager;
    CordsCallback cordsCallback;
    GeoObject geoObject;
    private PlacesFragment.OnFragmentActionListener mListener;

    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    ArrayList<Place> placeList;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        serverManager = new RequestManager(getActivity());
        cordsCallback = new CordsCallback(geoObject,mListener);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        placeList = new ArrayList<>();
        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();
        textList.add("Москва-Тверская улица 6с1-37.611347-55.760241");
        String jsonText1 = gson.toJson(textList);
            String jsonText = sharedPreferences.getString("key",jsonText1);


            String[] text = gson.fromJson(jsonText, String[].class);
            for (String place : text) {
                String[] strPlace = place.split("-");
                parseJSON(new Point(Float.parseFloat(strPlace[2]), Float.parseFloat(strPlace[3])));
                //placeList.add(new Place(strPlace[0], strPlace[1], Float.parseFloat(strPlace[2]), Float.parseFloat(strPlace[3])));
                Log.d("MAP","SPGEt  "+strPlace[0]+ strPlace[1]+ Float.parseFloat(strPlace[2])+ Float.parseFloat(strPlace[3]));
            }



       //Log.d("MAP",placeList.get(0).getName()+placeList.get(0).getAdress());

        return createView();

    }
    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        mGridAdapter = new GridAdapter(getActivity(),placeList);
        mRecyclerView = new RecyclerView(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mGridAdapter);
        layout.addView(mRecyclerView);
        return layout;
    }
    public void addPlace(User user){
        placeList.add(new Place(user));

        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();

      for(Place place:placeList){
          textList.add(place.getName()+"-"+place.getAdress()+"-"+place.getLat()+"-"+place.getLng());
          Log.d("MAP","SP-Put "+place.getName()+"-"+place.getAdress()+"-"+place.getLat()+"-"+place.getLng() );
      }

        String jsonText = gson.toJson(textList);
        sharedPreferences.edit().putString("key", jsonText);
        sharedPreferences.edit().apply();
        mRecyclerView.invalidate();
        mGridAdapter.notifyDataSetChanged();

        for(Place place:placeList){
            Log.d("MAP","PlacesFragment-addPlace_LIST(USER)  "+ place.getName()+" "+place.getAdress()+" "+place.getLat()+" "+place.getLng());
        }



    }
    public void addPlace(Place place){
        placeList.add(place);
        parseJSON(new Point(place.getLat(),place.getLng()));
        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();

        for(Place place1 :placeList){

            textList.add(place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng());
            Log.d("MAP","SP-Put "+place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng() );
        }

        String jsonText = gson.toJson(textList);
        sharedPreferences.edit().putString("key", jsonText);
        sharedPreferences.edit().apply();

        for(Place places:placeList){
            Log.d("MAP","PlacesFragment-addPlace_LIST_(PLACE):"+ places.getName()+" "+places.getAdress()+" "+places.getLat()+" "+places.getLng());
        }
        mRecyclerView.invalidate();
        mGridAdapter.notifyDataSetChanged();




    }
    public void addPlace(GeoObject geoObject){
        Log.d("MAP","addAdressPlace");
        placeList.add(new Place(geoObject));
        parseJSON(geoObject);
        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();

        for(Place place1 :placeList){

            textList.add(place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng());
            Log.d("MAP","SP-Put "+place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng() );
        }

        String jsonText = gson.toJson(textList);
        sharedPreferences.edit().putString("key", jsonText);
        sharedPreferences.edit().apply();

        for(Place places:placeList){
            Log.d("MAP","PlacesFragment-addPlace_LIST_(PLACE):"+ places.getName()+" "+places.getAdress()+" "+places.getLat()+" "+places.getLng());
        }
        mRecyclerView.invalidate();
        mGridAdapter.notifyDataSetChanged();


    }
    private void parseJSON(Point point) {
        Log.d("MAP","ParseJson");
        serverManager.getAdressByCords(cordsCallback,point.getLongitude()+","+point.getLatitude());
    }
    private void parseJSON(GeoObject geoObject) {
        Log.d("MAP","ParseJson");
        serverManager.getAdressByCords(cordsCallback,geoObject.point.pos);
    }


    public interface OnFragmentActionListener {
        void onFragmentAction(String link);
        void onFragmentAction(GeoObject geoObject);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (PlacesFragment.OnFragmentActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentActionListener");
        }
    }
}
