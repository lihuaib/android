package com.example.lee.calllogsandcontacts.models;

import android.content.Context;

import com.example.lee.calllogsandcontacts.R;

public class PeopleItem {

    public int contact_id;
    public String sortKey;
    private String name;
    public String phone;

    public void setName(Context c, String name) {
        if (name == null || name.length() == 0) {
            this.name = c.getString(R.string.lb_unkown_name);
        } else
            this.name = name;
    }

    public String getName() {
        return name;
    }
}