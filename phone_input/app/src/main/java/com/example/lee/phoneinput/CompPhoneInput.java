package com.example.lee.phoneinput;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompPhoneInput extends LinearLayout {

    CompTextInput etPhone = null;
    TextView tvCode = null;

    public CompPhoneInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.comp_phone_input, this,
                true);
        etPhone = (CompTextInput) findViewById(R.id.compTextInput);
        tvCode = (TextView) findViewById(R.id.countryCode);

        etPhone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text = etPhone.getText();
                if (text.indexOf("#") == 0) {
                    findViewById(R.id.lyPlusPrefix).setVisibility(View.GONE);
                    findViewById(R.id.lyCodeSuffix).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.lyPlusPrefix).setVisibility(View.VISIBLE);
                    findViewById(R.id.lyCodeSuffix).setVisibility(View.VISIBLE);
                    setCode(text);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });
    }

    public void setPhoneNumber(String phone) {
        etPhone.setText(phone);
    }

    public String getPhoneNumber() {
        String phone = etPhone.getText().toString();
        return phone;
    }

    public String getCode() {
        String code = tvCode.getText().toString();
        return code;
    }

    private void setCode(String phone) {
        //String code = CountryPrefix.getCountryCode(phone).toUpperCase();
        //this.tvCode.setText(code);
        this.tvCode.setText("US");
    }
}