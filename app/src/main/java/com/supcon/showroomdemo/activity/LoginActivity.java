package com.supcon.showroomdemo.activity;


import android.content.Intent;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;

import com.supcon.showroomdemo.BaseNFCActivity;
import com.supcon.showroomdemo.R;
import com.supcon.showroomdemo.util.DataTransform;

import java.nio.charset.Charset;
import java.util.Locale;

public class LoginActivity extends BaseNFCActivity implements View.OnClickListener {

    //读取出来的id
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        findViewById(R.id.ll_psd_login).setOnClickListener(this);
        findViewById(R.id.ll_scan_login).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String action = getIntent().getAction();
        try {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                //处理该intent
                processIntent(getIntent());
                ParseID();
            }
        } catch (Exception e) {

        }
    }

    private void ParseID(){


    }

    @Override
    protected void onNewIntent(Intent intent) {

        String action = intent.getAction();
        try {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                //处理该intent
                processIntent(intent);
                ParseID();
            }
        } catch (Exception e) {

        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private String processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        byte[] uidBytes = tagFromIntent.getId();
        mId = DataTransform.bytesToHexString(uidBytes);

        return mId;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_scan_login:
                break;
            case R.id.ll_psd_login:
                break;
        }
    }


}
