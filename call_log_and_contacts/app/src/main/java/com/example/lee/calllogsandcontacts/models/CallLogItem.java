package com.example.lee.calllogsandcontacts.models;

import android.content.Context;

import com.example.lee.calllogsandcontacts.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogItem {

    public static final int CallOut = R.drawable.phone_record_out_call;
    public static final int CallMiss = R.drawable.phone_record_miss_call;
    public static final int CallIn = R.drawable.phone_record_in_call;

    private String name;
    public String phone;
    public int callType = CallLogItem.CallOut;
    private String dateTime;

    public void setName(Context c, String name) {
        if (name == null || name.length() == 0) {
            this.name = c.getString(R.string.lb_unkown_name);
        } else
            this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateTime(String dateTime) {
        Date callDayTime = new Date(Long.valueOf(dateTime));

        if(isToday(callDayTime)) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a");
            String dateString = formatter.format(callDayTime);
            this.dateTime = dateString;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateString = formatter.format(callDayTime);
            this.dateTime = dateString;
        }
    }

    public String getDateTime() {
        return dateTime;
    }

    private boolean isToday(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String now_str = formatter.format(new Date());
        String com_str = formatter.format(date);

        try {
            return  formatter.parse(now_str).compareTo(formatter.parse(com_str)) == 0;
        } catch (ParseException e) {
            return  false;
        }
    }
}