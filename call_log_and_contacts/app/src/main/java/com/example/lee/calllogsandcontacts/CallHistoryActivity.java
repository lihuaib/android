package com.example.lee.calllogsandcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.ListView;

import com.example.lee.calllogsandcontacts.models.CallLogItem;
import com.example.lee.calllogsandcontacts.models.CallLogListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CallHistoryActivity extends Activity {

    ListView lvCallLog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_call_history);

        lvCallLog = (ListView) this.findViewById(R.id.lvCfg);

        initCallHistory();
    }

    private void initCallHistory() {
        List<CallLogItem> callLogList = new ArrayList<CallLogItem>();

        Cursor managedCursor = null;
        try {
            managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                    null, null, null);
            int pos_number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int pos_name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            int pos_type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int pos_date = managedCursor.getColumnIndex(CallLog.Calls.DATE);

            while (managedCursor.moveToNext()) {
                CallLogItem calllog = new CallLogItem();
                calllog.phone = managedCursor.getString(pos_number);
                calllog.setName(this, managedCursor.getString(pos_name));
                calllog.setDateTime(managedCursor.getString(pos_date));
                int callType = Integer.parseInt(managedCursor.getString(pos_type));
                if (callType == CallLog.Calls.OUTGOING_TYPE) {
                    calllog.callType = CallLogItem.CallOut;
                } else if (callType == CallLog.Calls.INCOMING_TYPE) {
                    calllog.callType = CallLogItem.CallIn;
                } else if (callType == CallLog.Calls.MISSED_TYPE) {
                    calllog.callType = CallLogItem.CallMiss;
                }

                callLogList.add(0, calllog);
            }

            CallLogListViewAdapter myAdapter = new CallLogListViewAdapter(this, callLogList);
            lvCallLog.setAdapter(myAdapter);

            if (Build.VERSION.SDK_INT < 14) {
                managedCursor.close();
            }
        } catch (Exception e) {
            Log.e("Lee", e.toString());
        } finally {
            if (managedCursor != null)
                managedCursor = null;
        }
    }
}