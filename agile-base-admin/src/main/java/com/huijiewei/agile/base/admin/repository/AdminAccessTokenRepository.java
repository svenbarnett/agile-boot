package com.huijiewei.agile.base.admin.repository;

import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdminAccessTokenRepository extends JpaRepository<AdminAccessToken, Integer> {
    public Optional<AdminAccessToken> findByClientId(String clientId);

    public Optional<AdminAccessToken> findByClientIdAndAccessToken(String clientId, String accessToken);

    @Modifying
    @Transactional
    public void deleteByAdminIdAndClientId(Integer adminId, String clientId);
}
