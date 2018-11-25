
package com.example.sergei.finaltp.serializables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Locality {

    @SerializedName("LocalityName")
    @Expose
    public String localityName;
    @SerializedName("Thoroughfare")
    @Expose
    public Thoroughfare thoroughfare;

}
