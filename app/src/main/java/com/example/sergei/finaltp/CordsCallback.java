package com.example.sergei.finaltp;

import android.util.Log;

import com.example.sergei.finaltp.Fragments.PlacesFragment;
import com.example.sergei.finaltp.Fragments.SearchFragment;
import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class CordsCallback implements VolleyCallback {
    GeoObject geoObject;
    PlacesFragment.OnFragmentActionListener mListener;
    public CordsCallback(GeoObject geoObject, PlacesFragment.OnFragmentActionListener mListener){
        this.geoObject=geoObject;
        this.mListener = mListener;
    }
    @Override
    public void onSuccess(JSONObject result) {
        Log.d("MAP","PlacesFragment-parseJSON-onSucces get Object ");
        final Gson gson = new Gson();
        Type userType = new TypeToken<GeoObject>(){}.getType();
        geoObject = gson.fromJson(result.toString(), userType);
        Log.d("MAP","PlacesFragment-parseJSON-onSucces  ALL OBJECTS: \n");
        //geoObject.metaDataProperty.geocoderMetaData.text.toString();
        Log.d("MAP","CordsCallback");
        if(geoObject.metaDataProperty !=null) {
            Log.d("MAP","not null geoobject");
            mListener.onFragmentAction( geoObject);

        }

    }
}
