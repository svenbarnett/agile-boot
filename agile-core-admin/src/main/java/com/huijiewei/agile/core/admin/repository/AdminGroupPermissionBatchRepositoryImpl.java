package com.huijiewei.agile.core.admin.repository;

import com.huijiewei.agile.core.admin.entity.AdminGroupPermission;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminGroupPermissionBatchRepositoryImpl implements AdminGroupPermissionBatchRepository {
    private final JdbcTemplate jdbcTemplate;

    public AdminGroupPermissionBatchRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsert(List<AdminGroupPermission> adminGroupPermissions) {
        jdbcTemplate.batchUpdate("INSERT INTO " + AdminGroupPermission.TABLE_NAME + "(adminGroupId,actionId) values(?,?)",
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
