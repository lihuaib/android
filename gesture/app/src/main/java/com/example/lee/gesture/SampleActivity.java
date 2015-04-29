package com.example.lee.gesture;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        this.findViewById(R.id.btnLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CcpalFromLeftSlideViewGroup vc = (CcpalFromLeftSlideViewGroup)SampleActivity.this.findViewById(R.id.ccpalFromLeftSlideViewGroup);
                vc.startMove();
            }
        });
    }
}