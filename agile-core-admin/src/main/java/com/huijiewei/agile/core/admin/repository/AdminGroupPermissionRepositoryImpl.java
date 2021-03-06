package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminGroupPermission;
import com.huijiewei.agile.core.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdminGroupPermissionRepositoryImpl implements BatchRepository<AdminGroupPermission> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminGroupPermissionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsertImpl(List<AdminGroupPermission> adminGroupPermissions) {
        jdbcTemplate.batchUpdate(
                String.format("INSERT INTO %s(adminGroupId,actionId) values(?,?)", AdminGroupPermission.tableName(AdminGroupPermission.class)),
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i)
                            throws SQLException {
                        AdminGroupPermission adminGroupPermission = adminGroupPermissions.get(i);

                        preparedStatement.setInt(1, adminGroupPermission.getAdminGroupId());
                        preparedStatement.setString(2, adminGroupPermission.getActionId());
                    }

                    @Override
                    public int getBatchSize() {
                        return adminGroupPermissions.size();
                    }
                });
    }
}
