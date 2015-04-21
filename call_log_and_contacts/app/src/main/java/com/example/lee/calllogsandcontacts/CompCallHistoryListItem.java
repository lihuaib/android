package com.example.lee.calllogsandcontacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lee.calllogsandcontacts.models.CallLogItem;

import java.net.URLEncoder;

public class CompCallHistoryListItem extends LinearLayout {

    private Context _context;
    private TextView _tvName;
    private TextView _tvPhone;
    private TextView _tvTime;
    private ImageView _ivCallType;

    public CompCallHistoryListItem(Context context) {
        super(context, null);

        LayoutInflater.from(context).inflate(R.layout.comp_call_history_item, this,
                true);

        _context = context;
        _tvName = (TextView) this.findViewById(R.id.tvName);
        _tvPhone = (TextView) this.findViewById(R.id.tvPhone);
        _tvTime = (TextView) this.findViewById(R.id.tvTime);
        _ivCallType = (ImageView) this.findViewById(R.id.ivCallType);

        bindEvent();
    }

    public void setCallLogItem(CallLogItem item) {
        _tvName.setText(item.getName());
        _tvPhone.setText(item.phone);
        _tvTime.setText(item.getDateTime());
        _ivCallType.setImageResource(item.callType);
    }

    private void bindEvent() {
        View lyitem = this.findViewById(R.id.lyCallLogItem);
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