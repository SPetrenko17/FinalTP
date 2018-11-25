package com.example.sergei.finaltp;

import org.json.JSONArray;
import org.json.JSONObject;

public interface VolleyCallback {
    void onSuccess(JSONObject result);
    void onSuccess(JSONArray result);
    }
