package com.supcon.showroomdemo.activity

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.supcon.showroomdemo.App
import com.supcon.showroomdemo.BaseNFCActivity
import com.supcon.showroomdemo.R
import com.supcon.showroomdemo.common.Constants
import com.supcon.showroomdemo.model.UserDao
import com.supcon.showroomdemo.util.DataTransform

class LoginByCard : BaseNFCActivity(), View.OnClickListener {
    //读取出来的id
    private var mId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)
        findViewById<View>(R.id.ll_psd_login).setOnClickListener(this)
        findViewById<View>(R.id.ll_scan_login).setOnClickListener(this)
        findViewById<View>(R.id.iv_back).setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val action = intent.action
        try {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
                //处理该intent
                processIntent(intent)
                ParseID()
            }
        } catch (e: Exception) {
        }
    }

    private fun ParseID() {
        val userDao = App.getAppContext().daoSession.userDao
        val users = userDao.queryBuilder().where(UserDao.Properties.NfcId.eq(mId)).list()
        if (users.size > 0) {
            ToastUtils.showShort("NFC卡登录成功，开始人脸识别")
            val intent = Intent()
            intent.setClass(this, LoginByFace::class.java)
            intent.putExtra(Constants.INTENT_KEY_USER,users[0])
            startActivity(intent)
        }else {
            ToastUtils.showShort("没有该卡${mId}的信息")
        }

    }

    override fun onNewIntent(intent: Intent) {
        val action = intent.action
        try {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
                //处理该intent
                processIntent(intent)
                ParseID()
            }
        } catch (e: Exception) {
        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private fun processIntent(intent: Intent): String? {
        //取出封装在intent中的TAG
        val tagFromIntent =
            intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val uidBytes = tagFromIntent.id
        mId = DataTransform.bytesToHexString(uidBytes)
        return mId
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.ll_scan_login -> {
            }
            R.id.ll_psd_login -> {
            }
        }
    }
}