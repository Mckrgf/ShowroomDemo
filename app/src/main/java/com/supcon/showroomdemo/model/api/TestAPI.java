package com.supcon.showroomdemo.model.api;

import com.supcon.showroomdemo.model.bean.TestEntity;
import com.yaobing.module_apt.ContractFactory;

/**
 * @author : yaobing
 * @date : 2020/12/15 11:12
 * @desc :
 */
@ContractFactory(entites = TestEntity.class)
public interface TestAPI {
    void login();
}
