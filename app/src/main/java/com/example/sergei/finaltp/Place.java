package com.example.sergei.finaltp;

import com.example.sergei.finaltp.serializables.User;

public class Place {
    float lat;
    float lng;
    String name;
    String adress;

    public Place(User user){
        this.name=user.response.geoObjectCollection.featureMember.get(0).geoObject.name;
        this.adress=user.response.geoObjectCollection.featureMember.get(0).geoObject.description;
        this.lat=Float.valueOf(user.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ")[1]);
        this.lng=Float.valueOf(user.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.split(" ")[0]);
    }
    public Place(String name, String adress, float lat,float lng){
        this.name=name;
        this.adress=adress;
        this.lat=lat;
        this.lng=lng;
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
}
