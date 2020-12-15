package com.supcon.showroomdemo.presenter;

import com.supcon.showroomdemo.model.api.NetworkAPIHttpclient;
import com.supcon.showroomdemo.model.bean.TestEntity;
import com.supcon.showroomdemo.model.contract.TestContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author : yaobing
 * @date : 2020/12/15 13:50
 * @desc :
 */
public class TestPresenter extends TestContract.Presenter {
    @Override
    public void login() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("userName", "dyh");
        queryMap.put("password", "Supcon1304");
        queryMap.put("clientId", "android");//这个字段要问一下  0:普通用户,1:系统管理员,2:安全员,3:审计员'
        mCompositeSubscription.add(NetworkAPIHttpclient.getToken(queryMap).onErrorReturn(new Function<Throwable, TestEntity>() {
            @Override
            public TestEntity apply(Throwable throwable) {
                return new TestEntity();
            }
        }).subscribe(new Consumer<TestEntity>() {
            @Override
            public void accept(TestEntity testEntity) {
                getView().loginSuccess(testEntity);
            }
        }));
    }
}
