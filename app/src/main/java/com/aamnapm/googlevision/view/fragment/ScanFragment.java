package com.aamnapm.googlevision.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aamnapm.googlevision.R;
import com.aamnapm.googlevision.helper.BarcodeScanListener;
import com.aamnapm.googlevision.helper.QrScan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanFragment extends Fragment implements BarcodeScanListener {


    @BindView(R.id.surface_view)
    SurfaceView surfaceView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        ButterKnife.bind(this, view);


        QrScan qrScan = new QrScan(getContext(), getActivity(), surfaceView, this);
        qrScan.permissionRequest();

        return view;
    }


    /**
     * remove fragment
     */
    private void removeFragment() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }


    @Override
    public void scanResult(String barcodeData) {
        if (barcodeData.length() > 0) {
            Log.e("Barcode", "barcodeData " + barcodeData);
            removeFragment();
        } else {
            Log.e("Barcode", "error barcodeData " + barcodeData);
        }
    }
}
