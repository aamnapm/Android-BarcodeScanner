package com.aamnapm.googlevision.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.aamnapm.googlevision.R;
import com.aamnapm.googlevision.helper.BarcodeScanListener;
import com.aamnapm.googlevision.helper.DialogHelper;
import com.aamnapm.googlevision.view.fragment.ScanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BarcodeScanListener {


    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_scan_activity)
    public void scanActivity() {
        Intent activityScan = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(activityScan);
    }

    @OnClick(R.id.btn_scan_fragment)
    public void scanFragment() {

        frameLayout.setVisibility(View.VISIBLE);
        Fragment fragment = new ScanFragment();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.btn_scan_dialog)
    public void scanDialog() {
        DialogHelper.scanDialog(this, this, true, this);
    }


    @Override
    public void scanResult(String barcodeData) {
        Log.e("Barcode", "barcodeData " + barcodeData);
    }
}
