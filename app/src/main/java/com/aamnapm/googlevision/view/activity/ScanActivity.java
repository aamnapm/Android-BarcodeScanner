package com.aamnapm.googlevision.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.aamnapm.googlevision.R;
import com.aamnapm.googlevision.helper.BarcodeScanListener;
import com.aamnapm.googlevision.helper.QrScan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanActivity extends AppCompatActivity implements BarcodeScanListener {


    @BindView(R.id.surface_view)
    SurfaceView surfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        QrScan qrScan = new QrScan(this, this, surfaceView, this);
        qrScan.permissionRequest();

    }




    @Override
    public void scanResult(String barcodeData) {
        if (barcodeData.length() > 0) {
            Log.e("Barcode", "barcodeData " + barcodeData);
            finish();
        } else {
            Log.e("Barcode", "error barcodeData " + barcodeData);
        }
    }
}
