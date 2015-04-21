package com.example.lee.calllogsandcontacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lee.calllogsandcontacts.models.PeopleItem;

import java.net.URLEncoder;

public class CompPeopleListItem extends LinearLayout {

    private Context _context;
    private TextView _sortKey;
    private TextView _tvName;
    private TextView _tvPhone;

    public CompPeopleListItem(Context context) {
        super(context, null);

        LayoutInflater.from(context).inflate(R.layout.comp_people_item, this,
                true);

        _context = context;
        _sortKey = (TextView) this.findViewById(R.id.capital);
        _tvName = (TextView) this.findViewById(R.id.tvName);
        _tvPhone = (TextView) this.findViewById(R.id.tvPhone);

        bindEvent();
    }

    public void setPeopleItem(PeopleItem item) {
        _sortKey.setText(item.sortKey);
        _tvName.setText(item.getName());
        _tvPhone.setText(item.phone);
    }

    public void setSectionVisible() {
        _sortKey.setVisibility(VISIBLE);
    }

    public void setSectionGone() {
        _sortKey.setVisibility(GONE);
    }

    private void bindEvent() {
        View lyitem = this.findViewById(R.id.lyPeopleItem);
        lyitem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String press_value = _tvPhone.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + URLEncoder.encode(press_value)));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(intent1);
            }
        });
    }
}