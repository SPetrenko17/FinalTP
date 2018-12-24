package com.example.sergei.finaltp;

import android.util.Log;

import com.example.sergei.finaltp.Fragments.SearchFragment;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class AdressCallback implements VolleyCallback {
    User user;
    SearchFragment.OnFragmentActionListener mListener;
    public AdressCallback(User user, SearchFragment.OnFragmentActionListener mListener){
        this.user=user;
        this.mListener=mListener;

    }
    @Override
    public void onSuccess(JSONObject result) {
        Log.d("MAP","SearchFragment-parseJSON-onSucces get Object ");
        final Gson gson = new Gson();
        Type userType = new TypeToken<User>(){}.getType();
        user = gson.fromJson(result.toString(), userType);
        Log.d("MAP","SearchFragment-parseJSON-onSucces  ALL OBJECTS: \n");
        for(int i =0;i<user.response.geoObjectCollection.featureMember.size();i++){
            Log.d("MAP",i+": "+user.response.geoObjectCollection.featureMember.get(i).geoObject.point.pos.toString()+"\n");
        }
        if(!user.response.geoObjectCollection.featureMember.isEmpty()) {
            mListener.onFragmentAction(user.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.toString());
            mListener.onFragmentAction(user);
        }
    }
}
