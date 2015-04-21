package com.example.lee.customkeypad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URLEncoder;

public class CompKeypad extends LinearLayout {

    private String[] numList = new String[]{"1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "*", "0", "#"};
    private String[] tagList = new String[]{"", "abc", "def",
            "ghi", "jkl", "mno",
            "pqrs", "tuv", "wxyz",
            ",", "+", ""};
    private int[] idList = new int[]{R.id.one, R.id.two, R.id.three,
            R.id.four, R.id.five, R.id.six,
            R.id.seven, R.id.eight, R.id.nine,
            R.id.star, R.id.zero, R.id.pound};

    private TextView tvNumberInput;
    private ImageButton btnDelete;
    private Button btnCall;

    public CompKeypad(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.comp_ccpal_keypad, this,
                true);

        tvNumberInput = (TextView) this.findViewById(R.id.tvNumberInput);
        btnDelete = (ImageButton) this.findViewById(R.id.delete_bt);
        btnCall = (Button) this.findViewById(R.id.btnCall);

        bindNumberInput();
        bindButtonClear();
        bindMakeCall();
    }

    private void bindButtonClear() {
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = tvNumberInput.getText().toString();
                if (str.length() > 1) {
                    tvNumberInput.setText(str.substring(0, str.length() - 1));
                } else {
                    tvNumberInput.setText("");
                }
            }
        });

        btnDelete.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tvNumberInput.setText("");
                return false;
            }
        });
    }

    private void bindNumberInput() {
        for (int i = 0; i < idList.length; i++) {
            final int pos = i;
            ImageButton btn = (ImageButton) this.findViewById(idList[i]);
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    appendText(numList[pos]);
                }
            });
            btn.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String long_press_value = tagList[pos];
                    if (long_press_value.equals(",")
                            || long_press_value.equals("+")) {
                        appendText(long_press_value);
                    }
                    return true;
                }
            });
        }
    }

    private void bindMakeCall() {
        btnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String press_value = tvNumberInput.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + URLEncoder.encode(press_value)));
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CompKeypad.this.getContext().startActivity(intent1);
            }
        });
    }

    private void appendText(String str) {
        String s = tvNumberInput.getText().toString();
        s += str;
        tvNumberInput.setText(s);
    }
}