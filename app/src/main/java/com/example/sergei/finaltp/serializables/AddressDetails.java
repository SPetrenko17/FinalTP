
package com.example.sergei.finaltp.serializables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressDetails {

    @SerializedName("Country")
    @Expose
    public Country country;

}
