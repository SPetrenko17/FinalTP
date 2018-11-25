package com.example.sergei.finaltp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.sergei.finaltp.GridAdapter;
import com.example.sergei.finaltp.Place;
import com.example.sergei.finaltp.R;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PlacesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    List<Place> placeList;
    private RequestQueue mRequestQueue;

    private Gson gson;
    private Type userType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        gson = new Gson();
        userType = new TypeToken<User>(){}.getType();
        placeList = new List<Place>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Place> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Place place) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Place> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Place> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public Place get(int index) {
                return null;
            }

            @Override
            public Place set(int index, Place element) {
                return null;
            }

            @Override
            public void add(int index, Place element) {

            }

            @Override
            public Place remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Place> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Place> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Place> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        placeList.add(0,new Place("Test ","Adress ",1.111f,2.222f));

        //Log.d("MAP",placeList.get(0).getName()+placeList.get(0).getAdress());
        mGridAdapter = new GridAdapter(getContext(),new ArrayList<Place>(placeList));

        return createView();

    }
    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        mGridAdapter = new GridAdapter(getContext(),new ArrayList<Place>(placeList));
        mRecyclerView = new RecyclerView(getActivity());
        //mRecyclerView = getActivity().findViewById(R.id.recycler_view_places);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mGridAdapter);
        layout.addView(mRecyclerView);
        return layout;
    }
    public void addPlace(User usr){
        User user = gson.fromJson(usr.toString(), userType);
        placeList.add(new Place(user));
    }



}
