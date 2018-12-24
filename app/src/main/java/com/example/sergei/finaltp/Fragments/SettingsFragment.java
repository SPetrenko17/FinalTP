package com.example.sergei.finaltp.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergei.finaltp.R;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.yandex.runtime.Runtime.getApplicationContext;

public class SettingsFragment extends Fragment {

    SharedPreferences sPref;
    final String SAVED_LOCALE = "saved_locale";
    RadioButton radioButtonEn;
    RadioButton radioButtonRu;
    RadioGroup radioGroup;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return createView();
    }
    private View createView(){
        LinearLayout layout = new LinearLayout(getActivity());
        radioGroup = new RadioGroup(getActivity());



        textView = new TextView(getActivity());
        radioButtonEn = new RadioButton(getActivity());
        radioButtonRu = new RadioButton(getActivity());
//        radioButtonEn=(RadioButton) getActivity().findViewById( R.id.radioButtonEn);
//        radioButtonRu=(RadioButton) getActivity().findViewById( R.id.radioButtonRu);

        textView.setText(R.string.setlocale);
        radioButtonRu.setText(R.string.ru);
        radioButtonEn.setText(R.string.en);
        radioGroup.addView(radioButtonEn);
        radioGroup.addView(radioButtonRu);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);



        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.TOP);
        layout.addView(textView);
        layout.addView(radioGroup);



        radioButtonRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("ru");
                changeLocale(locale);
                sPref = getActivity().getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_LOCALE, "ru");
                ed.commit();

            }
        });

        radioButtonEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("en");
                changeLocale(locale);
                sPref = getActivity().getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(SAVED_LOCALE, "en");
                ed.commit();
            }
        });



        return layout;
    }
    @SuppressWarnings("deprecation")
    private void changeLocale(Locale locale)
    {
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getApplicationContext().getResources()
                .updateConfiguration(configuration,
                        getApplicationContext()
                                .getResources()
                                .getDisplayMetrics());

    }




}
