package com.aamnapm.googlevision.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;

public class QrScan {

    private Context context;
    private Activity activity;
    private SurfaceView surfaceView;
    private AlertDialog alertDialog;
    private CameraSource cameraSource;
    private BarcodeScanListener barcodeScanListener;

    /**
     * constructor
     *
     * @param context
     * @param surfaceView
     * @param activity
     * @param barcodeScanListener
     */
    public QrScan(Context context, Activity activity, SurfaceView surfaceView, BarcodeScanListener barcodeScanListener) {
        this.context = context;
        this.activity = activity;
        this.surfaceView = surfaceView;
        this.barcodeScanListener = barcodeScanListener;
    }

    /**
     * @param context
     * @param activity
     * @param surfaceView
     * @param barcodeScanListener
     * @param alertDialog
     */
    public QrScan(Context context, Activity activity, SurfaceView surfaceView, BarcodeScanListener barcodeScanListener, AlertDialog alertDialog) {
        this.context = context;
        this.activity = activity;
        this.alertDialog = alertDialog;
        this.surfaceView = surfaceView;
        this.barcodeScanListener = barcodeScanListener;
    }

    /**
     * scan barcode with surfaceView and cameraSource and return data with barcodeScanListener to activity or fragment
     */
    private void start() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context.getApplicationContext()).setBarcodeFormats(Barcode.QR_CODE).build();


        cameraSource = new CameraSource
                .Builder(context, barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        CameraSource finalCameraSource = cameraSource;

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission")
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                new Handler().postDelayed(() -> {
                    try {
                        finalCameraSource.start(surfaceView.getHolder());

                        //delay is used because  (api 18,19) before open camera transparent surfaceView and user see back of dialog
                        new Handler().postDelayed(() -> {
                            surfaceView.setBackground(null);
                        }, 100);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, 200);

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                finalCameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodeArray = detections.getDetectedItems();

                if (barcodeArray.size() != 0) {

                    String data = barcodeArray.valueAt(0).displayValue;

                    String barcodeData = BarcodeData.filter(data);

                    alertDialog.dismiss();
                    barcodeScanListener.scanResult(barcodeData);

                }
            }
        });
    }


    /**
     * get permission
     */
    public void permissionRequest() {

        /*if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            start();
            return;
            // Permission is not granted
        }
*/
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                start();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                //TODO:show dialog alert dialog to user to get permission
                Log.e("Permission", "take camera permission");
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        };
        PermissionHelper.permission(activity, permissionListener, Manifest.permission.CAMERA);
    }
}
