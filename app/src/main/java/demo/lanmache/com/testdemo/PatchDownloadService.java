package demo.lanmache.com.testdemo;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Copyright: Copyright (c) 2015年, Ltd. All rights reserved. <br>
 * Version:V1.0.0 <br>
 * Author: machao <br>
 * Date:   16/7/1 16:39  <br>
 * Desc:    <br>
 */
public class PatchDownloadService extends IntentService {

    private static final String TAG = "PatchDownloadService";
    private int fileLength, downloadLength;


    public PatchDownloadService() {
        super("patch");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String downloadUrl = intent.getStringExtra("url");

            if (URLUtil.isNetworkUrl(downloadUrl)) {
                downloadPatch(downloadUrl);
            }
        }
    }

    private void downloadPatch(String downloadUrl) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/test/patch");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File patchFile = new File(dir, String.valueOf(System.currentTimeMillis()) + ".apatch");
        downloadFile(downloadUrl, patchFile);
        if (patchFile.exists() && patchFile.length() > 0 && fileLength > 0) {
            try {
                MainApplication.getInstance().getmPatchManager().addPatch(patchFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void downloadFile(String downloadUrl, File file){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(TAG,"can not find the dir");
            e.printStackTrace();
        }
        InputStream ips = null;
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(20000);
            fileLength = Integer.valueOf(httpConnection.getHeaderField("Content-Length"));
            ips = httpConnection.getInputStream();
            int hand = httpConnection.getResponseCode();
            if (hand == 200) {
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = ips.read(buffer)) != -1) {
                    if (fos != null) {
                        fos.write(buffer, 0, len);
                    }
                    downloadLength = downloadLength + len;
                }
            } else {
                Log.e(TAG,"response code: " + hand);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (ips != null) {
                    ips.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
