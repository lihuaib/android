package com.example.lee.callinteceptor;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.text.format.Time;
import android.util.Log;

import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class Interceptor extends BroadcastReceiver {
    /**
     * Intercept outgoing calls and does translation if needed
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String dialedNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

        Log.i("lee", "dialedNumber=" + dialedNumber);

        if(dialedNumber.startsWith("+"))
            return;

        // set Result data to null so that current dialing intent will fail
        // silently
        this.setResultData(null);

        // abort this broadcast for down the chain receivers since we have
        // taken care of it.
        // and we don't want anyone else will mess it again
        this.abortBroadcast();

        // register content observer to modify call log
        updateCallLog(context, dialedNumber);

        // start a new intent with our new number;
        // we will be called again, but we pass we shall find the new number
        // is a local number
        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                + URLEncoder.encode("+" + dialedNumber)));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }

    private void updateCallLog(final Context context, final String dialedNumber) {
        class CallLogTimerTask extends TimerTask {
            public void run() {

                // find the latest call log entry
                String[] projection = new String[]{CallLog.Calls._ID,
                        CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME};

                Uri myCalls = CallLog.Calls.CONTENT_URI;
                Cursor cursor = context.getContentResolver().query(myCalls,
                        projection, null, null, CallLog.Calls.DATE + " DESC");
                if (cursor.getCount() == 0) {
                    Log.w("lee",
                            "updateCallLog() cannot find no call log existing");
                    cursor.close();
                    return;
                }

                cursor.moveToFirst();
                String id = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls._ID));
                String number = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                String name = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                cursor.close();

                Log.i("lee", "updateCallLog() found the latest record, "
                        + number + ", " + name);

                // the newly added entry should be the same as access number
                // or starts with the access number
                // but first we remove some chars that can be added by phone
                number = number.replaceAll("[^0-9,]+", "");
                if (number.startsWith("15") == true) {
                    Log.w("lee", "updateCallLog() : " + number
                            + " is not the expected number, "
                            + number);
                    return;
                }

                ContentValues values = new ContentValues();
                values.put(CallLog.Calls.NUMBER, "456789");
                Log.i("lee", "updateCallLog() modifying call entry: " + number
                        + " => " + "456789");
                context.getContentResolver().update(myCalls, values,
                        CallLog.Calls._ID + "=" + id, null);
                context.getContentResolver().notifyChange(myCalls, null);
            }
        }

        class CallLogObserver extends ContentObserver {

            public CallLogObserver(Handler handler) {
                super(handler);
            }

            public void onChange(boolean selfChange) {
                // HACK: we check for sending update back *after* a call is
                // made.
                // This is because on CDMA network we don't have internet
                // connection during
                // calls.
                // Unregister ourself so that we don't hang around
                // accidentally.
                context.getContentResolver().unregisterContentObserver(this);
                Timer myTimer = new Timer();
                myTimer.schedule(new CallLogTimerTask(), 1000);
            }
        }

        context.getContentResolver().registerContentObserver(
                CallLog.Calls.CONTENT_URI, true,
                new CallLogObserver(new Handler()));
    }
}
