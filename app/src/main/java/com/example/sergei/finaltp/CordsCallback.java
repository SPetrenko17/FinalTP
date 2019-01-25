package com.example.sergei.finaltp;

import android.util.Log;

import com.example.sergei.finaltp.Fragments.PlacesFragment;
import com.example.sergei.finaltp.Fragments.SearchFragment;
import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.GeoObjectCollection;
import com.example.sergei.finaltp.serializables.Point;
import com.example.sergei.finaltp.serializables.Response;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class CordsCallback implements VolleyCallback {

    User user;
    PlacesFragment.OnPlaceFragmentActionListener mListener;
    public CordsCallback(User user, PlacesFragment.OnPlaceFragmentActionListener mListener){

        this.mListener = mListener;
        this.user = user;

    }
    @Override
    public void onSuccess(JSONObject result) {
        Log.d("MAP","PlacesFragment-parseJSON-onSucces get Object ");
        final Gson gson = new Gson();
        Type userType = new TypeToken<User>(){}.getType();
        user = gson.fromJson(result.toString(), userType);
        Log.d("MAP","PlacesFragment-parseJSON-onSucces  ALL OBJECTS: \n");
        if(!user.response.geoObjectCollection.featureMember.isEmpty()) {
            mListener.onPlaceFragmentAction(user);
        }
        }
}
