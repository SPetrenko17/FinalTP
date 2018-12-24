package com.example.sergei.finaltp;

import android.util.Log;

import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.User;

public class Place {
    float lat;
    float lng;
    String name;
    String adress;


    public Place(User user){
        Log.d("MAP","create new place by user");
        this.name=user.response.geoObjectCollection.featureMember.get(0).geoObject.name;
        this.adress=user.response.geoObjectCollection.featureMember.get(0).geoObject.description;
        this.lat=Float.valueOf(user.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ")[1]);
        this.lng=Float.valueOf(user.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ")[0]);
    }
    public Place(String name, String adress, float lat,float lng){
        Log.d("MAP","create new place by data ");
        this.name=name;
        this.adress=adress;
        this.lat=lat;
        this.lng=lng;
    }
    public Place(GeoObject geoObject){
        Log.d("MAP","create new place by geoobject ");
        this.name=geoObject.name.toString();
        this.adress=geoObject.metaDataProperty.geocoderMetaData.text;
        this.lat=Float.valueOf(geoObject.point.pos.split(" ")[0]); //возможно надо поменять местаи lat и  lng
        this.lng=Float.valueOf(geoObject.point.pos.split(" ")[1]);
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String name){
        this.adress=adress;
    }
}
