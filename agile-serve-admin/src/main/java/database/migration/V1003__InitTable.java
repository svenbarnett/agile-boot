package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1003__InitTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        this.createCaptchaTable(context);
    }

    private void createCaptchaTable(Context context) {
        String tableName = TableName.getTableName("captcha");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `code` varchar(10) NOT NULL DEFAULT '',\n" +
                        "  `uuid` varchar(22) NOT NULL DEFAULT '',\n" +
                        "  `userAgent` varchar(2048) NOT NULL DEFAULT '',\n" +
                        "  `remoteAddr` varchar(50) NOT NULL DEFAULT '',\n" +
                        "  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `uuid` (`uuid`,`code`,`remoteAddr`) USING BTREE\n" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4;");
    }
}
