package com.aamnapm.googlevision.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.SurfaceView;
import android.view.View;

import com.aamnapm.googlevision.R;

public class DialogHelper {

    public static void scanDialog(Context context, Activity activity, boolean cancelable, BarcodeScanListener barcodeScanListener) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        View view = activity.getLayoutInflater().inflate(R.layout.dialog_scan, null);

        SurfaceView surfaceView = view.findViewById(R.id.surface_view);

        if (cancelable) {
            alert.setCancelable(true);
        } else {
            alert.setCancelable(false);
        }


        alert.setView(view);

        final AlertDialog alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        QrScan qrScan = new QrScan(context, activity, surfaceView, barcodeScanListener, alertDialog);
        qrScan.permissionRequest();


        alertDialog.show();
    }
}
