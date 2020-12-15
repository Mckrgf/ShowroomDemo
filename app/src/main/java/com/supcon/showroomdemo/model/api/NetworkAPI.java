package com.supcon.showroomdemo.model.api;

import com.supcon.showroomdemo.model.bean.TestEntity;
import com.yaobing.module_apt.ApiFactory;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author : yaobing
 * @date : 2020/12/15 13:48
 * @desc :
 */
@ApiFactory(name = "NetworkAPIHttpclient")
public interface NetworkAPI {
    @POST("/inter-api/auth/login")
    Flowable<TestEntity> getToken(@Body Map<String, Object> loginMap);
}
