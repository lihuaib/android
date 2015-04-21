package com.example.lee.tablebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompTableBar extends LinearLayout {

    private static int INDEX_RECENTS = 0;
    private static int INDEX_CONCTACTS = 1;
    private static int INDEX_KEYPAD = 2;

    LinearLayout lyRecents = null;
    LinearLayout lyContacts = null;
    LinearLayout lyKeypad = null;
    LinearLayout lyHome = null;

    public CompTableBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.comp_table_bar, this,
                true);

        lyRecents = (LinearLayout) this.findViewById(R.id.lyRecents);
        lyContacts = (LinearLayout) this.findViewById(R.id.lyContacts);
        lyKeypad = (LinearLayout) this.findViewById(R.id.lyKeypad);
        lyHome = (LinearLayout) this.findViewById(R.id.lyHome);


        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CompTableBar, 0, 0);
        int showIndex = array
                .getInt(R.styleable.CompTableBar_showIconIndex, INDEX_RECENTS);
        if (showIndex == INDEX_RECENTS) {
            switchStatus(lyRecents);
        } else if (showIndex == INDEX_CONCTACTS) {
            switchStatus(lyContacts);
        } else if (showIndex == INDEX_KEYPAD) {
            switchStatus(lyKeypad);
        } else {
            switchStatus(lyKeypad);
        }
        array.recycle();

        bindRecents();
        bindContacts();
        bindKeypad();
        bindHome();
    }

    public void bindRecents() {
        lyRecents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchStatus(lyRecents);

                // to other activity
                ((Activity) getContext()).finish();
            }
        });
    }

    public void bindContacts() {
        lyContacts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchStatus(lyContacts);

                // to other activity
                ((Activity) getContext()).finish();
            }
        });
    }

    public void bindKeypad() {
        lyKeypad.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchStatus(lyKeypad);

                // to other activity
                ((Activity) getContext()).finish();
            }
        });
    }

    public void bindHome() {
        lyHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchStatus(lyHome);

                // to other activity
                ((Activity) getContext()).finish();
            }
        });
    }

    private void switchStatus(final LinearLayout ly) {
        initUnChecked();

        if (lyRecents == ly) {
            setStatus(lyRecents,
                    R.drawable.bottom_ico_recent_pressed, R.color.blue);
        } else if (lyContacts == ly) {
            setStatus(lyContacts,
                    R.drawable.bottom_ico_user_pressed, R.color.blue);
        } else if (lyKeypad == ly) {
            setStatus(lyKeypad,
                    R.drawable.bottom_ico_keypad_pressed, R.color.blue);
        } else if (lyHome == ly) {
            setStatus(lyHome,
                    R.drawable.bottom_ico_setting_pressed, R.color.blue);
        } else {
            setStatus(lyKeypad,
                    R.drawable.bottom_ico_keypad_pressed, R.color.blue);
        }
    }

    private void initUnChecked() {
        setStatus(lyRecents,
                R.drawable.bottom_ico_recent, R.color.white);
        setStatus(lyContacts,
                R.drawable.bottom_ico_user, R.color.white);
        setStatus(lyKeypad,
                R.drawable.bottom_ico_keypad, R.color.white);
        setStatus(lyHome,
                R.drawable.bottom_ico_setting, R.color.white);
    }

    private void setStatus(LinearLayout ly, int img, int color) {
        ImageView iv = (ImageView) ly.getChildAt(0);
        TextView tv = (TextView) ly.getChildAt(1);

        iv.setImageResource(img);
        tv.setTextColor(this.getResources().getColor(color));
    }
}