package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1004__InitTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        this.createShopBrandTable(context);
    }

    private void createShopBrandTable(Context context) {
        String tableName = TableName.getTableName("shop_brand");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `name` varchar(60) NOT NULL DEFAULT '',\n" +
                        "  `alias` varchar(50) NOT NULL DEFAULT '',\n" +
                        "  `logo` varchar(255) NOT NULL DEFAULT '',\n" +
                        "  `description` text,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `name` (`name`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=1012 DEFAULT CHARSET=utf8mb4;");
    }
}
