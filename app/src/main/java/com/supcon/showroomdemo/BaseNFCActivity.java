package com.supcon.showroomdemo;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;

import com.blankj.utilcode.util.SPUtils;
import com.supcon.showroomdemo.activity.LoginByCardActivity;
import com.yaobing.module_middleware.activity.BaseActivity;

public abstract class BaseNFCActivity extends BaseActivity {

    protected NfcAdapter mNfcAdapter;
    protected PendingIntent mPendingIntent = null;

    protected IntentFilter tagDetected = null;
    protected IntentFilter[] intentFiltersArray;
    protected String[][] techListsArray;

    private String TIME_TAMP_APP_RESTART = "appRestartTimeTamp";

    @Override
    protected void onStart() {
        super.onStart();
        initNFC();
    }

    protected void initNFC() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoginByCardActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);//
        tagDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);//ACTION_TECH_DISCOVERED
        try {
            tagDetected.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[]{tagDetected,};
        techListsArray = new String[][]{
                new String[]{NfcF.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()},
                new String[]{Ndef.class.getName()},
                new String[]{IsoDep.class.getName()},
                new String[]{MifareClassic.class.getName()},
                new String[]{MifareUltralight.class.getName()}};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, intentFiltersArray, techListsArray);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //恢复默认状态
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
        SPUtils.getInstance().put(TIME_TAMP_APP_RESTART, System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

