package com.example.lee.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompDialog extends Dialog {

    private static CompDialog instance;

    private Context _mContext;
    private TextView tvTitle;
    private TextView tvMsg;
    private EditText tvInput;
    private LinearLayout lyTwoBtn;
    private LinearLayout lyOneBtn;
    private Button btnLeft;
    private Button btnRight;
    private Button btnCenter;

    public static void ShowConfirm(Context m, View.OnClickListener leftLis, View.OnClickListener rightLis,
                                   String leftmsg, String rightmsg, String title, String msg) {
        instance = getInstance(m);
        instance.show();

        if (title == null || title.length() == 0) {
            instance.tvTitle.setVisibility(View.GONE);
        } else {
            instance.tvTitle.setVisibility(View.VISIBLE);
            instance.tvTitle.setText(title);
        }
        if (leftLis == null) {
            leftLis = instance.getCancelLis();
        }
        if (rightLis == null) {
            rightLis = instance.getCancelLis();
        }

        instance.tvMsg.setVisibility(View.VISIBLE);
        instance.tvMsg.setText(msg);
        instance.tvInput.setVisibility(View.GONE);
        instance.lyOneBtn.setVisibility(View.GONE);
        instance.lyTwoBtn.setVisibility(View.VISIBLE);
        instance.btnLeft.setOnClickListener(leftLis);
        instance.btnLeft.setText(leftmsg);
        instance.btnRight.setOnClickListener(rightLis);
        instance.btnRight.setText(rightmsg);
    }

    public static void ShowConfirmWithInput(Context m, View.OnClickListener leftLis,
                                            View.OnClickListener rightLis,
                                            String leftmsg, String rightmsg, String title, String msg, String inputHint) {
        ShowConfirm(m, leftLis, rightLis, leftmsg, rightmsg, title, msg);
        instance.tvInput.setVisibility(View.VISIBLE);
        instance.tvInput.setHint(inputHint);
        instance.tvInput.setSelection(instance.tvInput.getText().toString().length());
    }

    public static void ShowAlert(Context m, String btnmsg, String title, String msg, View.OnClickListener okLis) {
        instance = getInstance(m);
        instance.show();

        if (title == null || title.length() == 0) {
            instance.tvTitle.setVisibility(View.GONE);
        } else {
            instance.tvTitle.setVisibility(View.VISIBLE);
            instance.tvTitle.setText(title);
        }

        instance.tvMsg.setVisibility(View.VISIBLE);
        instance.tvMsg.setText(msg);
        instance.tvInput.setVisibility(View.GONE);
        instance.lyOneBtn.setVisibility(View.VISIBLE);
        instance.lyTwoBtn.setVisibility(View.GONE);
        if (okLis == null)
            instance.btnCenter.setOnClickListener(instance.getCancelLis());
        else
            instance.btnCenter.setOnClickListener(okLis);
        instance.btnCenter.setText(btnmsg);
    }

    public static CompDialog getInstance(Context c) {
        if (instance == null || instance._mContext != c) {
            instance = new CompDialog(c);
        }
        return instance;
    }

    public String getEditInputText() {
        return tvInput.getText().toString();
    }

    private CompDialog(Context context) {
        super(context, R.style.dialogStyle);
        _mContext = context;
    }

    private View.OnClickListener getCancelLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instance.dismiss();
                instance = null;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.comp_ccpal_dialog);
        init();
    }

    private void init() {
        tvTitle = (TextView) this.findViewById(R.id.tvTitle);
        tvMsg = (TextView) this.findViewById(R.id.tvMsg);
        tvInput = (EditText) this.findViewById(R.id.tvInput);
        lyTwoBtn = (LinearLayout) this.findViewById(R.id.lyTwoBtn);
        lyOneBtn = (LinearLayout) this.findViewById(R.id.lyOneBtn);
        btnLeft = (Button) this.findViewById(R.id.btnLeft);
        btnRight = (Button) this.findViewById(R.id.btnRight);
        btnCenter = (Button) this.findViewById(R.id.btnCenter);
    }
}