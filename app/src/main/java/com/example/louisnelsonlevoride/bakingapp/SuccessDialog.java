package com.example.louisnelsonlevoride.bakingapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class SuccessDialog {

    public Button doneButton;
    public TextView messageTextView;
    public Dialog dialog;
    public void showDialog(Activity activity, String msg){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.success_dialog);
        messageTextView = dialog.findViewById(R.id.messageTextView);
        messageTextView.setText(msg);
        doneButton = dialog.findViewById(R.id.doneBtnId);
        dialog.show();
    }
}
