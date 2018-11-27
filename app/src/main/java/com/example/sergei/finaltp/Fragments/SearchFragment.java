package com.example.sergei.finaltp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.sergei.finaltp.R;
import com.example.sergei.finaltp.RequestManager;
import com.example.sergei.finaltp.VolleyCallback;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.mapview.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SearchFragment extends Fragment  {
    private OnFragmentActionListener mListener;

    Button searchButton;
    EditText seachEditText;
    String adress;
    User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MapKitFactory.initialize(getActivity());
        return  createView();
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            parseJSON();
            Log.d("MAP","SearchFragment-OnCreateView-OnClickListener-OnClick");
        }
    };

    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        searchButton = new Button(getActivity());
        seachEditText = new EditText(getActivity());
        searchButton.setOnClickListener(onClickListener);
        layout.addView(searchButton);
        layout.addView(seachEditText);
        return layout;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    private void parseJSON() {
        adress=seachEditText.getText().toString();
        RequestManager serverManager = new RequestManager(getActivity(),adress);
        serverManager.getCordsByAdress(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.d("MAP","SearchFragment-parseJSON-onSucces get Object ");
                final Gson gson = new Gson();
                Type userType = new TypeToken<User>(){}.getType();
                user = gson.fromJson(result.toString(), userType);
                Log.d("MAP","SearchFragment-parseJSON-onSucces  ALL OBJECTS: \n");
                for(int i =0;i<user.response.geoObjectCollection.featureMember.size();i++){
                    Log.d("MAP",i+": "+user.response.geoObjectCollection.featureMember.get(i).geoObject.point.pos.toString()+"\n");
                }
                mListener.onFragmentAction(user.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos.toString());
                mListener.onFragmentAction(user);
            }
            @Override
            public void onSuccess(JSONArray result) {
            }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentActionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentActionListener");
        }
    }

    public interface OnFragmentActionListener {
        void onFragmentAction(String link);
        void onFragmentAction(User user);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    // permission denied
                }
                return;
        }
    }

}

