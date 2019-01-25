package com.example.sergei.finaltp.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.sergei.finaltp.AdressCallback;
import com.example.sergei.finaltp.CordsCallback;
import com.example.sergei.finaltp.GridAdapter;
import com.example.sergei.finaltp.Place;
import com.example.sergei.finaltp.RequestManager;
import com.example.sergei.finaltp.serializables.GeoObject;
import com.example.sergei.finaltp.serializables.Response;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.yandex.mapkit.geometry.Point;
import java.util.ArrayList;
public class PlacesFragment extends android.app.Fragment {

    RequestManager serverManager;
    CordsCallback cordsCallback;
    AdressCallback adressCallback;
    GeoObject geoObject;
    Response response;
    User user;
    com.example.sergei.finaltp.serializables.Point point;
    private OnPlaceFragmentActionListener mListener;

    private RecyclerView mRecyclerView;
    private GridAdapter mGridAdapter;
    ArrayList<Place> placeList;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        serverManager = new RequestManager(getActivity());
        cordsCallback = new CordsCallback(user,mListener);
        //adressCallback = new AdressCallback(user,mListener)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        placeList = new ArrayList<>();
        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();
        textList.add("Москва-Тверская улица 6с1-55.760241-37.611347");
        String jsonText1 = gson.toJson(textList);
            String jsonText = sharedPreferences.getString("key",jsonText1);


            String[] text = gson.fromJson(jsonText, String[].class);
            for (String place : text) {
                String[] strPlace = place.split("-");
                parseJSON(new Point(Float.parseFloat(strPlace[2]), Float.parseFloat(strPlace[3])));
                //placeList.add(new Place(strPlace[0], strPlace[1], Float.parseFloat(strPlace[2]), Float.parseFloat(strPlace[3])));
                Log.d("MAP","SPGEt  "+strPlace[0]+ strPlace[1]+ Float.parseFloat(strPlace[2])+ Float.parseFloat(strPlace[3]));
            }
        return createView();

    }
    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        mGridAdapter = new GridAdapter(getActivity(),placeList);
        mRecyclerView = new RecyclerView(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(mGridAdapter);
        layout.addView(mRecyclerView);
        return layout;
    }
    public void addPlace(User user){
        placeList.add(new Place(user));
        //parseJSON();

        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();

      for(Place place:placeList){
          textList.add(place.getName()+"-"+place.getAdress()+"-"+place.getLat()+"-"+place.getLng());
          Log.d("MAP","SP-Put "+place.getName()+"-"+place.getAdress()+"-"+place.getLat()+"-"+place.getLng() );
      }

        String jsonText = gson.toJson(textList);
        sharedPreferences.edit().putString("key", jsonText);
        sharedPreferences.edit().apply();
        mRecyclerView.invalidate();
        mGridAdapter.notifyDataSetChanged();

        for(Place place:placeList){
            Log.d("MAP","PlacesFragment-addPlace_LIST(USER)  "+ place.getName()+" "+place.getAdress()+" "+place.getLat()+" "+place.getLng());
        }



    }
    public void addPlace(Place place){
        placeList.add(place);
        parseJSON(new Point(place.getLat(),place.getLng()));
        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();

        for(Place place1 :placeList){

            textList.add(place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng());
            Log.d("MAP","SP-Put "+place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng() );
        }

        String jsonText = gson.toJson(textList);
        sharedPreferences.edit().putString("key", jsonText);
        sharedPreferences.edit().apply();
        sharedPreferences.edit().commit();

        for(Place places:placeList){
            Log.d("MAP","PlacesFragment-addPlace_LIST_(PLACE):"+ places.getName()+" "+places.getAdress()+" "+places.getLat()+" "+places.getLng());
        }
        mRecyclerView.invalidate();
        mGridAdapter.notifyDataSetChanged();




    }
    public void addPlace(Point point){
        //placeList.add(new Place("new place","new adress",(float)point.getLatitude(),(float)point.getLongitude()));
        parseJSON(new Point((float)point.getLatitude(),(float)point.getLongitude()));
        Gson gson = new Gson();
        ArrayList<String> textList = new ArrayList<String>();

        for(Place place1 :placeList){

            textList.add(place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng());
            Log.d("MAP","SP-Put "+place1.getName()+"-"+place1.getAdress()+"-"+place1.getLat()+"-"+place1.getLng() );
        }

        String jsonText = gson.toJson(textList);
        sharedPreferences.edit().putString("key", jsonText);
        sharedPreferences.edit().apply();
        sharedPreferences.edit().commit();

        for(Place places:placeList){
            Log.d("MAP","PlacesFragment-addPlace_LIST_(PLACE):"+ places.getName()+" "+places.getAdress()+" "+places.getLat()+" "+places.getLng());
        }
        mRecyclerView.invalidate();
        mGridAdapter.notifyDataSetChanged();




    }

    private void parseJSON(Point point) {
        Log.d("MAP","ParseJson_Point");
        serverManager.getAdressByCords(cordsCallback,point.getLongitude()+","+point.getLatitude());
    }


    private void parseJSON() {
        if(placeList.size()>1)
        serverManager.getAdressByCords(cordsCallback,placeList.get(placeList.size()-1).getLat()+","+placeList.get(placeList.size()-1).getLng());
    }


    public interface OnPlaceFragmentActionListener {
        void onPlaceFragmentAction(User user);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (PlacesFragment.OnPlaceFragmentActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentActionListener");
        }
    }
}
