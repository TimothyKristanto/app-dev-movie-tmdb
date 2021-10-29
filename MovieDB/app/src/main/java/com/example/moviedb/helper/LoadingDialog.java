package com.example.moviedb.helper;

import android.app.Activity;
import android.app.ProgressDialog;

import com.example.moviedb.R;

public class LoadingDialog {
    Activity activity;
    ProgressDialog progressDialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoading(){
        progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        progressDialog.setContentView(R.layout.loading_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void stopLoading(){
        progressDialog.dismiss();
    }
}
