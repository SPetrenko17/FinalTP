
package com.example.sergei.finaltp.serializables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Envelope {

    @SerializedName("lowerCorner")
    @Expose
    public String lowerCorner;
    @SerializedName("upperCorner")
    @Expose
    public String upperCorner;

}
