package com.example.lee.calllogsandcontacts.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.example.lee.calllogsandcontacts.CompCallHistoryListItem;

import java.util.List;

public class CallLogListViewAdapter extends BaseAdapter {

    private Context _context;
    private List<CallLogItem> _list;

    public CallLogListViewAdapter(Context context, List<CallLogItem> list) {
        this._context = context;
        this._list = list;
    }

    public List<CallLogItem> getList() {
        return _list;
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CallLogItem calllog = _list.get(position);
        CompCallHistoryListItem item;
        if (convertView == null) {
            item = new CompCallHistoryListItem(_context);
        } else {
            item = (CompCallHistoryListItem) convertView;
        }
        item.setCallLogItem(calllog);

        return item;
    }
}
