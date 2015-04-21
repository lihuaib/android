package com.example.lee.calllogsandcontacts;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lee.calllogsandcontacts.models.PeopleItem;
import com.example.lee.calllogsandcontacts.models.PeopleListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PeopleActivity extends Activity {

    ListView lvPeople;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.page_people);

        lvPeople = (ListView) this.findViewById(R.id.lvCfg);

        initPeople();
    }

    private void initPeople() {
        List<PeopleItem> peopleList = new ArrayList<PeopleItem>();

        Cursor managedCursor = null;
        try {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            managedCursor = this.getContentResolver().query(
                    uri,
                    new String[]{"display_name", "sort_key", "contact_id",
                            "data1"}, null, null, "sort_key");

            int pos_number = managedCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int pos_name = 0;
            int pos_sorkey = 1;
            int pos_contact_id = managedCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);

            while (managedCursor.moveToNext()) {
                PeopleItem people = new PeopleItem();

                if (managedCursor.getString(pos_name) != null) {
                    people.phone = managedCursor.getString(pos_number);
                    people.setName(this, managedCursor.getString(pos_name));
                    people.sortKey = getSortKey(managedCursor.getString(pos_sorkey));
                    people.contact_id = managedCursor.getInt(pos_contact_id);

                    peopleList.add(people);
                }
            }

            final PeopleListViewAdapter myAdapter = new PeopleListViewAdapter(this, peopleList);
            lvPeople.setAdapter(myAdapter);

            TextView dialog = (TextView) findViewById(R.id.dialog);
            CompCommonLetterListView letterView = (CompCommonLetterListView) findViewById(R.id.letterView);
            letterView.setTextView(dialog);

            letterView.setOnTouchingLetterChangedListener(new CompCommonLetterListView.OnTouchingLetterChangedListener() {
                public void onTouchingLetterChanged(String s) {
                    int position = myAdapter.getPositionForSection(s);
                    if (position != -1) {
                        lvPeople.setSelection(position);
                    }
                }
            });

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

    private static String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }
}