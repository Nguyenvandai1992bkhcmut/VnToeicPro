package com.vntoeic.bkteam.vntoeicpro.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 5/29/17.
 */

public class CustomDialog extends Dialog {
    Context mContext;
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View customView = LayoutInflater.from(mContext).inflate(R.layout.custom_dialog, null, false);

        setContentView(customView);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        Window window = getWindow();
        params.copyFrom(window.getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }


}
