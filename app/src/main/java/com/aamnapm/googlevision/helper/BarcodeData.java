package com.aamnapm.googlevision.helper;

public class BarcodeData {

    /**
     * filter barcode data
     *
     * @param data
     * @return
     */
    public static String filter(String data) {
        String filter = "";
        String barcodeData = data.replace("", "");
        if (barcodeData.length() == 9) {
            return barcodeData;
        } else {
            return null;
        }
    }
}
