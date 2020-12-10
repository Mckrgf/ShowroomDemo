package com.supcon.showroomdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arcsoft.face.ActiveFileInfo
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import com.arcsoft.face.enums.RuntimeABI
import com.supcon.showroomdemo.activity.RegisterAndRecognizeActivity
import com.supcon.showroomdemo.common.Constants
import com.yaobing.module_middleware.activity.BaseActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
        bt_active.setOnClickListener {
            activeEngine()
        }

        bt_scan.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,RegisterAndRecognizeActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 激活引擎
     *
     * @param view
     */
    fun activeEngine() {
        Observable.create<Int> { emitter ->
            val runtimeABI: RuntimeABI = FaceEngine.getRuntimeABI()
            val start = System.currentTimeMillis()
            val activeCode: Int = FaceEngine.activeOnline(this@MainActivity, Constants.APP_ID, Constants.SDK_KEY)
            emitter.onNext(activeCode)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(activeCode: Int) {
                        when {
                            activeCode === ErrorInfo.MOK -> {
                                showToast("成功")
                            }
                            activeCode === ErrorInfo.MERR_ASF_ALREADY_ACTIVATED -> {
                                showToast("已经更新过")
                            }
                            else -> {
                                showToast("失败")
                            }
                        }
//                        if (view != null) {
//                            view.isClickable = true
//                        }
                        val activeFileInfo = ActiveFileInfo()
                        val res: Int = FaceEngine.getActiveFileInfo(this@MainActivity, activeFileInfo)
                        if (res == ErrorInfo.MOK) {
                            Log.i(TAG, activeFileInfo.toString())
                        }
                    }

                    override fun onError(e: Throwable) {
                        showToast(e.message)
//                        if (view != null) {
//                            view.isClickable = true
//                        }
                    }

                    override fun onComplete() {}
                })
    }
    protected fun showToast(s: String?) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(s: String?) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}