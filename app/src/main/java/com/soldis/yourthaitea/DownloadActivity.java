package com.soldis.yourthaitea;

import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ayz4sci.androidfactory.DownloadProgressView;

import java.io.File;

/**
 * Created by snugrah4 on 11/16/2017.
 */

public class DownloadActivity extends AppCompatActivity {
    DownloadProgressView downloadProgressView;
    private long downloadID;
    private DownloadManager downloadManager;

    String VERSION = "";
    String APK_NAME = "";
    TextView txtVersion;
    boolean bDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        InitControl();

        try{
            VERSION = getIntent().getExtras().getString("VERSION");
            APK_NAME = getIntent().getExtras().getString("APK_NAME");
            txtVersion.setText("APK " + getResources().getString(R.string.main_version) + " " + VERSION);
            DownloadAPK();
        }catch (Exception e){
            finish();
        }

    }

    void InitControl(){
        txtVersion = (TextView)findViewById(R.id.text_version);
        downloadProgressView = (DownloadProgressView) findViewById(R.id.downloadProgressView);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    void DownloadAPK(){
        try{
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(AppConstant.DOMAIN_URL + "/download/" + APK_NAME));
            request.setTitle(APK_NAME);

            request.setDescription("DESCRIPTION");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            File fileAPK = new File(AppConstant.PATH_FOLDER_APK + "/" + APK_NAME);
            if (fileAPK.exists()){
                fileAPK.delete();
            }

            bDownload = false;
            File root = new File(AppConstant.PATH_FOLDER_APK);
            Uri path = Uri.withAppendedPath(Uri.fromFile(root), APK_NAME);
            request.setDestinationUri(path);

            downloadID = downloadManager.enqueue(request);

            downloadProgressView.show(downloadID, new DownloadProgressView.DownloadStatusListener() {
                @Override
                public void downloadFailed(int reason) {
                    //Action to perform when download fails, reason as returned by DownloadManager.COLUMN_REASON
                    Log.w("Download", "downloadFailed " +  AppConstant.DOMAIN_URL + "/download/" + AppConstant.APK_FILENAME);
                    AppController.getInstance().CustomeDialog(DownloadActivity.this, DownloadManager.COLUMN_REASON);
                }

                @Override
                public void downloadSuccessful() {
                    //Action to perform on success
                    Log.w("Download", "downloadSuccessful");
                    if (!bDownload){
                        bDownload = true;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(AppConstant.PATH_FOLDER_APK + "/" + APK_NAME)), "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }

                @Override
                public void downloadCancelled() {
                    //Action to perform when user press the cancel button
                    finish();
                }
            });
        }catch (Exception e){

        }
    }

}
