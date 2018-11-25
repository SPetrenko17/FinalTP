package com.example.sergei.finaltp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterView.OnItemClickListener {
     private Context mContext;
    private ArrayList<Place> mPlaceList;

    public GridAdapter(Context context, ArrayList<Place> placeList) {
        mContext = context;
        mPlaceList = placeList;
    }
    @Override
    public PlaceViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i)  {

        final Place currentPlace = mPlaceList.get(i);
        Context context = viewHolder.itemView.getContext();

        ((GridAdapter.PlaceViewHolder)viewHolder).mPlaceName.setText(currentPlace.name);
        ((PlaceViewHolder)viewHolder).mPlaceAdress.setText(currentPlace.adress);
        ((PlaceViewHolder)viewHolder).mPlaceLat.setText("lat: "+currentPlace.lat);
        ((PlaceViewHolder)viewHolder).mPlaceLng.setText("lng: "+currentPlace.lng);

        ((PlaceViewHolder)viewHolder).mPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.d("MAP","OPEN PLACE ON MAP");
            }
        });
    }


    @Override
    public int getItemCount() {
        return mPlaceList.size();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("TEST",i+" ");
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {
        public TextView mPlaceName;
        public TextView mPlaceAdress;
        public TextView mPlaceLat;
        public TextView mPlaceLng;
        public Button mPlaceButton;

        public PlaceViewHolder(final View itemView) {
            super(itemView);
            mPlaceName = itemView.findViewById(R.id.place_name);
            mPlaceAdress = itemView.findViewById(R.id.place_adress);
            mPlaceLat = itemView.findViewById(R.id.place_lat);
            mPlaceLng = itemView.findViewById(R.id.place_lng);
            mPlaceButton = itemView.findViewById(R.id.place_button);


        }
    }


}

