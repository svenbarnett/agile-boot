package com.huijiewei.agile.core.service;

import com.huijiewei.agile.core.consts.AccountTypeEnums;
import com.huijiewei.agile.core.entity.Identity;
import com.huijiewei.agile.core.entity.IdentityLog;

public interface IdentityService {
    Identity getIdentityByAccount(String account, AccountTypeEnums accountType);

    void saveIdentityLog(IdentityLog identityLog);

    String getRetryCacheName();

    default boolean getIsEnableCaptcha() {
        return true;
    }
}
