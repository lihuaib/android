package com.example.lee.calllogsandcontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        View btnCallLogs = this.findViewById(R.id.btnCallLogs);
        btnCallLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, CallHistoryActivity.class);
                SampleActivity.this.startActivity(intent);
            }
        });


        View btnPeople = this.findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SampleActivity.this, PeopleActivity.class);
                SampleActivity.this.startActivity(intent);
            }
        });
    }
}
