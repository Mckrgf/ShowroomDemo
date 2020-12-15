package com.supcon.showroomdemo;
import com.supcon.showroomdemo.model.DaoMaster;
import com.supcon.showroomdemo.model.DaoSession;
import com.yaobing.module_middleware.BaseApp;
import com.yaobing.module_middleware.MiddleWareConstant;
import com.yaobing.module_middleware.Utils.SharedPreferencesUtils;

import org.greenrobot.greendao.database.Database;

/**
 * Created by tfhr on 2018/2/1.
 */

public class App extends BaseApp {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    private static App mApp;

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;
    private static final String TAG = "App";
    private DaoMaster daoMaster;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        // regular SQLite database
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
        Database db = helper.getWritableDb();

        // encrypted SQLCipher database
        // note: you need to add SQLCipher to your dependencies, check the build.gradle file
        // DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db-encrypted");
        // Database db = helper.getEncryptedWritableDb("encryption-key");
        BaseApp.setIp("192.168.90.49");
        BaseApp.setPort("8080");
        SharedPreferencesUtils.setParam(BaseApp.getInstance(), MiddleWareConstant.SPKey.URL_ENABLE, false);
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static App getAppContext() {
        return mApp;
    }
}
