package com.example.lee.customdialog;

import android.app.Activity;
import android.os.Bundle;


public class SampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //CompDialog.ShowConfirm(this, null, null, "Cancel", "OK", "Title", "Confirm dialog");
        //CompDialog.ShowAlert(this, "OK", "Title", "msg", null);
        CompDialog.ShowConfirmWithInput(this, null, null, "Cancel", "OK", "Title", "Confirm dialog", "this is hint");
    }
}
