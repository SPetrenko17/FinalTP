package com.example.sergei.finaltp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sergei.finaltp.GridAdapter;
import com.example.sergei.finaltp.Place;
import com.example.sergei.finaltp.R;
import com.example.sergei.finaltp.serializables.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    RadioGroup radioGroupLanguage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return createView();

    }

    private View createView() {
        LinearLayout layout = new LinearLayout(getActivity());
        radioGroupLanguage = new RadioGroup(getActivity());
        RadioButton radioButtonRu = new RadioButton(getActivity());
        RadioButton radioButtonEn = new RadioButton(getActivity());
        radioGroupLanguage.setOnCheckedChangeListener(onCheckedChangeListener);
        layout.addView(radioGroupLanguage);
        layout.addView(radioButtonRu);
        layout.addView(radioButtonEn);
        return layout;
    }
   RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case -1:
                    Log.d("MAP","ПЕРЕКЛЮЧАТЕЛЬ НЕ ВЫБРАН");
                    break;
                case R.id.radioButtonRu:
                    Log.d("MAP","RU");
                    break;
                case R.id.radioButtonEn:
                    Log.d("MAP","EN");
                    break;
                default:
                    break;
            }
        }
    };


}
