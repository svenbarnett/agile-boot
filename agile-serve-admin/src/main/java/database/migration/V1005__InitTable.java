package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1005__InitTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        this.createShopBrandCategoryTable(context);
    }

    private void createShopBrandCategoryTable(Context context) {
        String tableName = TableName.getTableName("shop_brand_category");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `shopBrandId` int NOT NULL DEFAULT '0',\n" +
                        "  `shopCategoryId` int NOT NULL DEFAULT '0',\n" +
                        "  PRIMARY KEY (`id`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;");
    }
}
