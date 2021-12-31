package com.android.myfinalproject.activities;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.myfinalproject.R;
import com.android.myfinalproject.app.G;
import com.android.myfinalproject.fragments.HomeFragment;
import com.android.myfinalproject.models.Translate;

import static com.android.myfinalproject.fragments.HomeFragment.EXTRA_KEY;

public class MoreDetailActivity extends AppCompatActivity {

    private TextView wordTextView;
    private TextView moreDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_detail);
        setupViews(G.translateModel);
    }

    private void setupViews(Translate translate) {
        wordTextView = findViewById(R.id.moreWordTextView);
        moreDetailTextView = findViewById(R.id.moreDetailTextView);
        try {
            if (translate != null) {
                wordTextView.setText(translate.getMeta().getTitle());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    moreDetailTextView.setText(Html.fromHtml(translate.getWord().getText(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    moreDetailTextView.setText(Html.fromHtml(translate.getWord().getText()));
                }
            }
        } catch (Exception e) {
        }
    }
}