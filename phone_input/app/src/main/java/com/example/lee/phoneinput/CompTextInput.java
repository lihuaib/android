package com.example.lee.phoneinput;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CompTextInput extends LinearLayout {

    EditText etText = null;
    Button btnClear = null;
    TextWatcher _watcher = null;

    public void addTextChangedListener(TextWatcher watcher) {
        _watcher = watcher;
    }

    public CompTextInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.comp_text_input, this,
                true);
        initCompoent();

        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.CompTextInput, 0, 0);
        initEditText(array);
        array.recycle();

        bindEvent();
    }

    public void setText(String str) {
        if (str != null && !str.equals("")) {
            etText.setText(str);
            etText.setSelection(str.length());
        } else {
            etText.setText("");
        }
    }

    public String getText() {
        String res = etText.getText().toString();
        return res;
    }

    private void initCompoent() {
        etText = (EditText) findViewById(R.id.txtText);
        btnClear = (Button) findViewById(R.id.btnClear);
        showClearFlag();
    }

    private void initEditText(TypedArray array) {
        String digits = array.getString(R.styleable.CompTextInput_digits);
        if (digits != null && !digits.equals("")) {
            etText.setKeyListener(DigitsKeyListener.getInstance(digits));
        }

        int type = array.getInt(R.styleable.CompTextInput_inputType,
                InputType.TYPE_CLASS_PHONE);
        etText.setRawInputType(type);
    }

    private void bindEvent() {
        etText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                showClearFlag();
                if (_watcher != null) {
                    _watcher.afterTextChanged(s);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        btnClear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                etText.setText("");
                showClearFlag();
            }
        });
    }

    private void showClearFlag() {
        String str = this.getText();
        if (str == null || str.equals("")) {
            btnClear.setVisibility(View.GONE);
        } else {
            btnClear.setVisibility(View.VISIBLE);
        }
    }
}