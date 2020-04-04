package com.huijiewei.agile.core.shop.repository;

import com.huijiewei.agile.core.repository.BatchRepository;
import com.huijiewei.agile.core.shop.entity.ShopBrandCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShopBrandCategoryRepositoryImpl implements BatchRepository<ShopBrandCategory> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShopBrandCategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void batchInsert(List<ShopBrandCategory> shopBrandCategories) {
        jdbcTemplate.batchUpdate(
                String.format("INSERT INTO %s(shopBrandId,shopCategoryId) values(?,?)", ShopBrandCategory.tableName(ShopBrandCategory.class)),
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i)
                            throws SQLException {
                        ShopBrandCategory shopBrandCategory = shopBrandCategories.get(i);

                        preparedStatement.setInt(1, shopBrandCategory.getShopBrandId());
                        preparedStatement.setInt(2, shopBrandCategory.getShopCategoryId());
                    }

                    @Override
                    public int getBatchSize() {
                        return shopBrandCategories.size();
                    }
                });
    }
}
