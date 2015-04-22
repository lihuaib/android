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


public class NotLeakActivity extends Activity {

    private static final String tag = "lee";
    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        tvValue = (TextView) this.findViewById(R.id.tvValue);

        handler_test.postDelayed(runableUI_test, 1000 * 30);
        this.finish();
    }

    static class TestHandler extends Handler {
        protected final WeakReference<NotLeakActivity> _activity;

        TestHandler(NotLeakActivity a) {
            _activity = new WeakReference<NotLeakActivity>(a);
        }

        @Override
        public void handleMessage(Message msg) {
            NotLeakActivity a = _activity.get();
            if (null == a) return;

            Log.d(tag, "this is your logic");
            a.addText();
        }
    }

    private final TestHandler handler_test = new TestHandler(this);

    static class TestRunableUI implements Runnable {
        protected final WeakReference<NotLeakActivity> _activity;

        TestRunableUI(NotLeakActivity a) {
            _activity = new WeakReference<NotLeakActivity>(a);
        }

        public void run() {
            try {
                NotLeakActivity a = _activity.get();
                if (null == a) return;

                for (int i = 0; i < 1000; i++) {
                    Thread.sleep(10);
                    a.handler_test.sendEmptyMessage(0);
                }
            } catch (Exception ex) {
                Log.e(tag, ex.toString());
            }
        }
    }

    private final TestRunableUI runableUI_test = new TestRunableUI(this);

    void addText() {
        tvValue.setText("1");
    }
}
