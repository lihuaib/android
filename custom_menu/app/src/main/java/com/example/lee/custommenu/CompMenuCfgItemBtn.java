package com.example.lee.custommenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompMenuCfgItemBtn extends LinearLayout {

    private ImageView ivLeft;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView ivRight;
    private ImageView ivSwitch;

    private boolean _check = false;

    public CompMenuCfgItemBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPrameter(context, attrs);

        // test code
        addEvent(null);
    }

    private void setPrameter(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.comp_menu_cfg_item_btn,
                this, true);

        ivLeft = (ImageView) this.findViewById(R.id.ivLeft);
        tvLeft = (TextView) this.findViewById(R.id.tvLeft);
        tvRight = (TextView) this.findViewById(R.id.tvRight);
        ivRight = (ImageView) this.findViewById(R.id.ivRight);
        ivSwitch = (ImageView) this.findViewById(R.id.ivSwitch);

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CompMenuCfgItemBtn, 0, 0);

        setImageViewValue(array,
                R.styleable.CompMenuCfgItemBtn_show_left_icon,
                R.styleable.CompMenuCfgItemBtn_left_icon,
                ivLeft);

        setTextViewValue(array,
                R.styleable.CompMenuCfgItemBtn_show_left_text,
                R.styleable.CompMenuCfgItemBtn_left_text,
                tvLeft);

        setTextViewValue(array,
                R.styleable.CompMenuCfgItemBtn_show_right_text,
                R.styleable.CompMenuCfgItemBtn_right_text,
                tvRight);

        setImageViewValue(array,
                R.styleable.CompMenuCfgItemBtn_show_right_icon,
                R.styleable.CompMenuCfgItemBtn_right_icon,
                ivRight);

        setIvSwitch(array);

        array.recycle();
    }

    @SuppressWarnings("ResourceType")
    private void setImageViewValue(TypedArray array, int attr_show, int attr_icon, ImageView iv) {
        int is_show = array.getInt(attr_show, View.GONE);
        iv.setVisibility(is_show);
        if(is_show == View.VISIBLE) {
            Drawable dr = array.getDrawable(attr_icon);
            iv.setImageDrawable(dr);
        }
    }

    @SuppressWarnings("ResourceType")
    private void setTextViewValue(TypedArray array, int attr_show, int attr_text, TextView tv) {
        int is_show = array.getInt(attr_show, View.GONE);
        tv.setVisibility(is_show);
        if(is_show == View.VISIBLE) {
            String text = array.getString(attr_text);
            tv.setText(text);
        }
    }

    @SuppressWarnings("ResourceType")
    private void setIvSwitch(TypedArray array) {
        int is_show = array.getInt(R.styleable.CompMenuCfgItemBtn_show_right_switch, View.GONE);
        ivSwitch.setVisibility(is_show);
        if(is_show == View.VISIBLE) {
            final Drawable on = array.getDrawable(R.styleable.CompMenuCfgItemBtn_right_switch_on);
            final Drawable off = array.getDrawable(R.styleable.CompMenuCfgItemBtn_right_switch_off);
            ivSwitch.setImageDrawable(_check ? on : off);
            ivSwitch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    _check = !_check;
                    ivSwitch.setImageDrawable(_check ? on : off);
                }
            });
        }
    }

    public void addEvent(OnClickListener l) {
        this.findViewById(R.id.lyItembtn).setOnClickListener(l);
    }
}
