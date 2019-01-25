package com.example.sergei.finaltp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.sergei.finaltp.AdressCallback;
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
    RequestManager serverManager;
    AdressCallback adressCallback;
    Button searchButton;
    EditText seachEditText;
    String adress;
    User user;
    private OnFragmentActionListener mListener;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            parseJSON();
            Log.d("MAP","SearchFragment-OnCreateView-OnClickListener-OnClick");
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MapKitFactory.initialize(getActivity());
         serverManager = new RequestManager(getActivity());
         adressCallback = new AdressCallback(user,mListener);
        return  createView();
    }

    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        searchButton = new Button(getActivity());
        searchButton.setPadding(2,2,2,2);
        searchButton.setText(getResources().getText(R.string.find));
        searchButton.setTextColor(getResources().getColor(R.color.colorWhite));
        searchButton.setOnClickListener(onClickListener);
        searchButton.setAlpha(0.8f);
        searchButton.setBackground(getResources().getDrawable(R.drawable.layout_bg));
        seachEditText = new EditText(getActivity());
        layout.addView(searchButton);
        layout.addView(seachEditText);
        layout.setGravity(Gravity.LEFT);
        layout.setAlpha(0.8f);
        layout.setBackground(getResources().getDrawable(R.drawable.layout_bg));
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
        serverManager.getCordsByAdress(adressCallback,adress);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                return;
        }
    }

    public interface OnFragmentActionListener {
        void onFragmentAction(String link);
        void onFragmentAction(User user);
    }

}

