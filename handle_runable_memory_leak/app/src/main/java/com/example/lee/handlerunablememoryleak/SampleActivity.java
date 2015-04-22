package com.example.lee.handlerunablememoryleak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        this.findViewById(R.id.btnLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, LeakActivity.class);
                SampleActivity.this.startActivity(intent);
            }
        });

        this.findViewById(R.id.btnNotLeak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, NotLeakActivity.class);
                SampleActivity.this.startActivity(intent);
            }
        });
    }
}
