
package com.example.sergei.finaltp.serializables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoObjectCollection {

    @SerializedName("metaDataProperty")
    @Expose
    public MetaDataProperty metaDataProperty;
    @SerializedName("featureMember")
    @Expose
    public List<FeatureMember> featureMember = null;

}
