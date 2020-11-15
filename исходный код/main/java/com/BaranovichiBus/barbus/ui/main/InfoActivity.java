package com.BaranovichiBus.barbus.ui.main;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.BaranovichiBus.barbus.R;

public class InfoActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textViewInfo1;
    private TextView textViewInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textView = findViewById(R.id.titleInfo);
        textViewInfo1 = findViewById(R.id.Info1);
        textViewInfo2 = findViewById(R.id.Info2);
        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TITLE)){
            textView.setText(intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TITLE));
        }
        if(intentThatStartedThisActivity.hasExtra("EXTRA_INFO")){
            textViewInfo1.setText(intentThatStartedThisActivity.getStringExtra("EXTRA_INFO"));
        }
        if(intentThatStartedThisActivity.hasExtra("EXTRA_INFO2")){
            textViewInfo2.setText(intentThatStartedThisActivity.getStringExtra("EXTRA_INFO2"));
        }
    }
}
