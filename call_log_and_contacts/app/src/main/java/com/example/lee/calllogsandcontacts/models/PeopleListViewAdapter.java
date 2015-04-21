package com.example.lee.calllogsandcontacts.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lee.calllogsandcontacts.CompPeopleListItem;

import java.util.List;

public class PeopleListViewAdapter extends BaseAdapter {

    private Context _context;
    private List<PeopleItem> _list;

    public PeopleListViewAdapter(Context context, List<PeopleItem> list) {
        this._context = context;
        this._list = list;
    }

    public List<PeopleItem> getList() {
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
        PeopleItem peopleItem = _list.get(position);
        CompPeopleListItem item;
        if (convertView == null) {
            item = new CompPeopleListItem(_context);
        } else {
            item = (CompPeopleListItem) convertView;
        }
        item.setPeopleItem(peopleItem);

        String section = _list.get(position).sortKey;
        if(position == getPositionForSection(section)) {
            item.setSectionVisible();
        } else {
            item.setSectionGone();
        }

        return item;
    }

    public int getPositionForSection(String s) {
        if (s == null || s.length() == 0)
            return -1;

        for (int i = 0; i < this.getCount(); i++) {
            String sortStr = _list.get(i).sortKey;
            if (sortStr.equalsIgnoreCase(s)) {
                return i;
            }
        }

        return -1;
    }
}
