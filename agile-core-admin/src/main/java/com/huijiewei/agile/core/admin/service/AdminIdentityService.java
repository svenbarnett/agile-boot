package com.huijiewei.agile.core.admin.service;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.consts.AccountTypeEnums;
import com.huijiewei.agile.core.entity.IdentityLog;
import com.huijiewei.agile.core.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminIdentityService implements IdentityService {

    private final AdminRepository adminRepository;
    private final AdminLogRepository adminLogRepository;

    @Autowired
    public AdminIdentityService(AdminRepository adminRepository, AdminLogRepository adminLogRepository) {
        this.adminRepository = adminRepository;
        this.adminLogRepository = adminLogRepository;
    }

    @Override
    public Admin getIdentityByAccount(String account, AccountTypeEnums accountType) {
        Optional<Admin> optionalAdmin = Optional.empty();

        if (accountType == AccountTypeEnums.PHONE) {
            optionalAdmin = this.adminRepository.findByPhone(account);
        }

        if (accountType == AccountTypeEnums.EMAIL) {
            optionalAdmin = this.adminRepository.findByEmail(account);
        }

        return optionalAdmin.isEmpty() ? null : optionalAdmin.get();
    }

    @Override
    public void saveIdentityLog(IdentityLog identityLog) {
        this.adminLogRepository.save((AdminLog) identityLog);
    }

    @Override
    public String getRetryCacheName() {
        return "AGILE_ADMIN_LOGIN_RETRY";
    }
}
