package com.supcon.showroomdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.arcsoft.face.ActiveFileInfo
import com.arcsoft.face.ErrorInfo
import com.arcsoft.face.FaceEngine
import com.arcsoft.face.enums.RuntimeABI
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.supcon.showroomdemo.activity.LoginByCardActivity
import com.supcon.showroomdemo.activity.NetSettingActivity
import com.supcon.showroomdemo.activity.RegisterActivity
import com.supcon.showroomdemo.common.Constants
import com.supcon.showroomdemo.faceserver.FaceServer
import com.supcon.showroomdemo.model.DaoSession
import com.supcon.showroomdemo.model.User
import com.supcon.showroomdemo.model.UserDao
import com.supcon.showroomdemo.model.api.TestAPI
import com.supcon.showroomdemo.model.bean.TestEntity
import com.supcon.showroomdemo.model.contract.TestContract
import com.supcon.showroomdemo.presenter.TestPresenter
import com.supcon.showroomdemo.util.Util
import com.yaobing.module_apt.Presenter
import com.yaobing.module_middleware.Utils.ToastUtil
import com.yaobing.module_middleware.activity.BaseActivity
import com.yaobing.module_middleware.activity.BasePresenterActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

@Presenter(value = [TestPresenter::class])
class MainActivity : BasePresenterActivity(),TestContract.View {
    private var userDao: UserDao? = null
    private var daoSession: DaoSession? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
        bt_active.setOnClickListener {
            activeEngine()
        }

        bt_scan.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        bt_delete.setOnClickListener {
            clearFaces()
        }

        bt_login.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,LoginByCardActivity::class.java)
            startActivity(intent)
        }

        bt_net_set.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, NetSettingActivity::class.java)
            startActivity(intent)
        }
        bt_test_login.setOnClickListener {
            presenterRouter.create(TestAPI::class.java).login()
        }

        // get the note DAO
        daoSession = (application as App).daoSession
        userDao = daoSession?.userDao
        initDB()
    }

    private fun initDB() {
        try {
            val appArray = JSONObject(
                Util.getJson(
                    this,
                    "default_users.json"
                )
            ).getJSONArray("default_users")
            for (i in 0 until appArray.length()) {
                val userJson = appArray[i] as JSONObject
                val user =
                    Gson().fromJson(
                        userJson.toString(),
                        User::class.java
                    )
                val users = userDao!!.queryBuilder().where(UserDao.Properties.Id.eq(user.id)).list() as List<*>
                if (users.isEmpty()) {
                    userDao!!.insert(user)
                    LogUtils.d("添加用户到数据库：" + user.name)
                }else {
                    LogUtils.d("该用户已经有了不再添加")
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
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

    fun clearFaces() {
        val faceNum: Int = FaceServer.getInstance().getFaceNumber(this)
        if (faceNum == 0) {
            showToast(getString(R.string.batch_process_no_face_need_to_delete))
        } else {
            val dialog =
                AlertDialog.Builder(this)
                    .setTitle(R.string.batch_process_notification)
                    .setMessage(getString(R.string.batch_process_confirm_delete, faceNum))
                    .setPositiveButton(
                        R.string.ok
                    ) { dialog, which ->
                        val deleteCount: Int =
                            FaceServer.getInstance().clearAllFaces(this@MainActivity)
                        showToast("$deleteCount faces cleared!")
                    }
                    .setNegativeButton(R.string.cancel, null)
                    .create()
            dialog.show()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun loginSuccess(entity: TestEntity?) {
        ToastUtil.showToast(this,"成功")
    }

    override fun loginFailed(errorMsg: String?) {
        ToastUtil.showToast(this,"失败")    }

}