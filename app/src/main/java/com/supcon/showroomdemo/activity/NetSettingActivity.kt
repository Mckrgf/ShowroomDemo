package com.supcon.showroomdemo.activity

import android.os.Bundle
import com.supcon.showroomdemo.R
import com.supcon.showroomdemo.util.IPUtil
import com.yaobing.module_middleware.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_net_setting.*

class NetSettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_setting)
        bt_save.setOnClickListener {
            val ip = et_ip.text.toString()
            IPUtil.setIp(this,ip)

            val port = et_port.text.toString()
            IPUtil.setPort(this,port)
        }
    }
}