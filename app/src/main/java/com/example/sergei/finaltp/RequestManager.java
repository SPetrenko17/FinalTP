package com.example.sergei.finaltp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yandex.mapkit.geometry.Point;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestManager {

    private final String mainUrl = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=";
    private String apiKey;
    private final  String adressUrl ="&geocode=";
    private String adress;
    private RequestQueue requestQueue;

    public RequestManager(Context context){

        this.requestQueue = Volley.newRequestQueue(context);
        this.apiKey=context.getString(R.string.api_key_geocoder);

    }
    public void getCordsByAdress(final VolleyCallback callback, String adress){
        Log.d("MAP",mainUrl+apiKey+adressUrl+adress);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, mainUrl+apiKey+adressUrl+adress, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });

        this.requestQueue.add(stringRequest);
    }
    public void getAdressByCords(final VolleyCallback callback, String cords){
        Log.d("MAP",mainUrl+apiKey+adressUrl+cords);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, mainUrl+apiKey+adressUrl+cords, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error", error.toString());
            }
        });

        this.requestQueue.add(stringRequest);
    }


}
