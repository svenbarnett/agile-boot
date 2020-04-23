package com.huijiewei.agile.core.service;

import com.huijiewei.agile.core.consts.AccountTypeEnums;
import com.huijiewei.agile.core.entity.Identity;
import com.huijiewei.agile.core.entity.IdentityLog;

public abstract class IdentityService {
    public abstract Identity getIdentityByAccount(String account, AccountTypeEnums accountType);

    public abstract void saveIdentityLog(IdentityLog identityLog);

    public abstract String getRetryCacheName();

    public boolean getIsEnableCaptcha() {
        return true;
    }

}
