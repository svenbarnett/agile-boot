package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1002__InitTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        this.createAdminLogTable(context);
    }

    private void createAdminLogTable(Context context) {
        String tableName = TableName.getTableName("admin_log");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `adminId` int(11) NOT NULL DEFAULT '0',\n" +
                        "  `status` int(2) NOT NULL DEFAULT '0',\n" +
                        "  `type` varchar(10) NOT NULL DEFAULT '',\n" +
                        "  `method` varchar(20) NOT NULL DEFAULT '',\n" +
                        "  `action` varchar(255) NOT NULL DEFAULT '',\n" +
                        "  `params` varchar(2048) NOT NULL DEFAULT '',\n" +
                        "  `userAgent` varchar(2048) NOT NULL DEFAULT '',\n" +
                        "  `remoteAddr` varchar(50) NOT NULL DEFAULT '',\n" +
                        "  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `adminId` (`adminId`),\n" +
                        "  KEY `type` (`type`)\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=100359 DEFAULT CHARSET=utf8mb4;");
    }
}
