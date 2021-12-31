package com.android.myfinalproject.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.android.myfinalproject.R;
import com.android.myfinalproject.activities.MoreDetailActivity;
import com.android.myfinalproject.apiServices.ApiService;
import com.android.myfinalproject.app.G;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    public static String EXTRA_KEY = "tran_extra";
    private EditText fromEditText;
    private TextView toTextView;
    private AppCompatSpinner fromLang, toLang;
    private AppCompatButton translateButton, moreButton;
    FloatingActionButton changeFab;
    String langs[] = {"Persian", "English", "Arabic"};
    String db = "fa2en";


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(HomeFragment.this.getContext(), android.R.layout.simple_spinner_item, langs);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromLang = view.findViewById(R.id.homeFromLangSpinner);
        fromLang.setAdapter(spinnerArrayAdapter);
        toLang = view.findViewById(R.id.homeToLangSpinner);
        toLang.setAdapter(spinnerArrayAdapter);
        fromLang.setSelection(0);
        toLang.setSelection(1);
        fromEditText = view.findViewById(R.id.homeFrommEditText);
        toTextView = view.findViewById(R.id.homeToTextView);
        translateButton = view.findViewById(R.id.homeTranslateButton);
        moreButton = view.findViewById(R.id.homeSeeMoreButton);
        changeFab = view.findViewById(R.id.homeChangeFab);
        toTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1)
                    moreButton.setVisibility(View.VISIBLE);
                else
                    moreButton.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fromLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                db = selectDb();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        toLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                db = selectDb();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        changeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int toPosition = toLang.getSelectedItemPosition();
                toLang.setSelection(fromLang.getSelectedItemPosition());
                fromLang.setSelection(toPosition);
            }
        });

        translateButton.setOnClickListener(view1 -> {
            HashMap<String, String> map = new HashMap<>();
            map.put(ApiService.TITLE, fromEditText.getText().toString());
            map.put(ApiService.DB, db);
            ApiService.getTranslate(map, response -> {
                G.translateModel = response;
                if (response.getResponse().getCode() == 200) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        toTextView.setText(Html.fromHtml(response.getWord().getText(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        toTextView.setText(Html.fromHtml(response.getWord().getText()));
                    }
                } else
                    Snackbar.make(view, response.getResponse().getCode() + " : Error in receive data", Snackbar.LENGTH_LONG).show();
            }, error -> Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show());
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MoreDetailActivity.class));
            }
        });
    }

    private void handleRequest() {

    }

    private String selectDb() {
        int toPos = toLang.getSelectedItemPosition();
        switch (fromLang.getSelectedItemPosition()) {
            case 0://fa
                if (toPos == 0)
                    return "dehkhoda";
                if (toPos == 1)
                    return "fa2en";
                if (toPos == 2)
                    return "fa2ar";
            case 1://en
                if (toPos == 0)
                    return "en2fa";
                if (toPos == 1)
                    return "en2en";
                if (toPos == 2)
                    return "en2ar";
            case 2://ar
                if (toPos == 0)
                    return "ar2fa";
                if (toPos == 1)
                    return "ar2en";
                if (toPos == 2)
                    return "ar2ar";
            default:
                return "";
        }

    }

}