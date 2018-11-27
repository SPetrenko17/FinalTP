
package com.example.sergei.finaltp.serializables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Address {

    @SerializedName("country_code")
    @Expose
    public String countryCode;
    @SerializedName("formatted")
    @Expose
    public String formatted;
    @SerializedName("Components")
    @Expose
    public List<Component> components = null;

}
