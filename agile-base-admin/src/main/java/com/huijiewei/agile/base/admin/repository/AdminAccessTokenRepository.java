package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccessTokenRepository extends JpaRepository<AdminAccessToken, Integer> {
    public AdminAccessToken findByClientId(String clientId);

    public AdminAccessToken findByClientIdAndAccessToken(String clientId, String accessToken);
}
