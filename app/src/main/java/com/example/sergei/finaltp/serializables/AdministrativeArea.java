
package com.example.sergei.finaltp.serializables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {

    @SerializedName("AdministrativeAreaName")
    @Expose
    public String administrativeAreaName;
    @SerializedName("Locality")
    @Expose
    public Locality locality;

}
