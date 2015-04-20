package com.example.lee.actionbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CompActionBar extends RelativeLayout {
    private View ivLeftBack;
    private View tvLeftBack;
    private TextView tvRightbtn;

    public CompActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPrameters(context, attrs);

        // default way
        addEvent(null, null, null);
    }

    @SuppressWarnings("ResourceType")
    private void setPrameters(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.comp_action_bar, this, true);

        ivLeftBack = findViewById(R.id.ivLeftBack);
        tvLeftBack = findViewById(R.id.tvLeftBack);
        tvRightbtn = (TextView)findViewById(R.id.tvRightbtn);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CompActionBar, 0, 0);

        // init button status
        int showLeftImg = array.getInt(R.styleable.CompActionBar_showLeftImg, GONE);
        ivLeftBack.setVisibility(showLeftImg);

        int showLeftTv = array.getInt(R.styleable.CompActionBar_showLeftTv, GONE);
        tvLeftBack.setVisibility(showLeftTv);

        int showRightTv = array.getInt(R.styleable.CompActionBar_showRightTv, GONE);
        tvRightbtn.setVisibility(showRightTv);

        // init value
        String textRight = array.getString(R.styleable.CompActionBar_showRightTvText);
        if (textRight != null) {
            tvRightbtn.setText(textRight);
        }

        String textMiddle = array.getString(R.styleable.CompActionBar_middle_title);
        if (textMiddle != null) {
            ((TextView) findViewById(R.id.tvMiddleHeader)).setText(textMiddle);
        }

        array.recycle();
    }

    /*
    * This method can called to send the message to action bar
    * */
    public void addEvent(OnClickListener leftImgLis,
                          OnClickListener leftLabLis,
                          OnClickListener rightLabLis) {
        ivLeftBack.setOnClickListener(leftImgLis == null ? getFinishLis() : leftImgLis);
        tvLeftBack.setOnClickListener(leftLabLis == null ? getFinishLis() : leftLabLis);
        if(rightLabLis != null) {
            tvRightbtn.setOnClickListener(rightLabLis);
        }
    }

    private OnClickListener getFinishLis() {
        return new OnClickListener() {
            public void onClick(View arg0) {
                Activity parent = (Activity) getContext();
                parent.finish();
            }
        };
    }
}
