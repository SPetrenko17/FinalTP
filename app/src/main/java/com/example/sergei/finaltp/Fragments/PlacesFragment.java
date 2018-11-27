package com.example.sergei.finaltp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;

import com.example.sergei.finaltp.GridAdapter;
import com.example.sergei.finaltp.MainActivity;
import com.example.sergei.finaltp.Place;

import com.example.sergei.finaltp.R;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class PlacesFragment extends android.app.Fragment {
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    ArrayList<Place> placeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        placeList = new ArrayList<>();
        placeList.add(0,new Place("Moscow ","AdressTest ",55.755814f,37.617634f));
        placeList.add(0,new Place("Name2 ","AdressTest ",33.3333333f,44.444444f));

       Log.d("MAP",placeList.get(0).getName()+placeList.get(0).getAdress());

        return createView();

    }
    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        mGridAdapter = new GridAdapter(getActivity(),new ArrayList<Place>(placeList));
        mRecyclerView = new RecyclerView(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mGridAdapter);
        layout.addView(mRecyclerView);
        return layout;
    }
    public void addPlace(User user){
        placeList.add(new Place(user));

        for(Place place:placeList){
            Log.d("MAP","PlacesFragment-addPlace \n ALL:"+ place.getName()+" "+place.getAdress()+" "+place.getLat()+" "+place.getLng());
        }
        mRecyclerView.invalidate();
        this.getView().invalidate();
        mRecyclerView.getRootView().invalidate();
        mGridAdapter.notifyDataSetChanged();

    }
    public GridAdapter getGridAdapter(){
        return this.mGridAdapter;
    }





}
