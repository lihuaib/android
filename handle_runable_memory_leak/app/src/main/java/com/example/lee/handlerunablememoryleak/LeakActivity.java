package com.example.lee.handlerunablememoryleak;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LeakActivity extends Activity {

    private static final String tag = "lee";
    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        tvValue = (TextView) this.findViewById(R.id.tvValue);

        handler.postDelayed(runableUI, 1000 * 30);
        this.finish();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(tag, "this is your logic");
            addText();
        }
    };

    Runnable runableUI = new Runnable() {
        public void run() {
            try {
                for (int i = 0; i < 1000; i++) {
                    Thread.sleep(10);
                    Thread.sleep(10);
                    handler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };

    void addText() {
        tvValue.setText("1");
    }
}
