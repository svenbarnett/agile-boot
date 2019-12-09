package database.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V1__InitTable extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        this.createAdminTable(context);
        this.createAdminAccessTokenTable(context);
        this.createAdminGroupTable(context);
        this.createAdminGroupPermissionTable(context);

        this.insertAdminGroupData(context);
        this.insertAdminData(context);
        this.insertAdminGroupPermissionData(context);

        this.createShopCategoryTable(context);
        this.createUserTable(context);
    }

    private String getTableName(String table) {
        return "ag_" + table;
    }

    private void createAdminTable(Context context) {
        String tableName = this.getTableName("admin");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "`\n" +
                        "(\n" +
                        "    `id`           int(11)      NOT NULL AUTO_INCREMENT,\n" +
                        "    `adminGroupId` int(11)               DEFAULT 0,\n" +
                        "    `phone`        varchar(30)  NOT NULL DEFAULT '',\n" +
                        "    `email`        varchar(90)  NOT NULL DEFAULT '',\n" +
                        "    `name`         varchar(30)  NOT NULL DEFAULT '',\n" +
                        "    `avatar`       varchar(500) NOT NULL DEFAULT '',\n" +
                        "    `password`     varchar(200) NOT NULL DEFAULT '',\n" +
                        "    `createdAt`    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "    `updatedAt`    timestamp    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                        "    `deletedAt`    timestamp    NULL     DEFAULT NULL,\n" +
                        "    PRIMARY KEY (`id`),\n" +
                        "    KEY `email` (`email`),\n" +
                        "    KEY `phone` (`phone`),\n" +
                        "    KEY `adminGroupId` (`adminGroupId`),\n" +
                        "    KEY `deletedAt` (`deletedAt`)\n" +
                        ") ENGINE = InnoDB\n" +
                        "  AUTO_INCREMENT = 120\n" +
                        "  DEFAULT CHARSET = utf8mb4;");
    }

    private void createAdminAccessTokenTable(Context context) {
        String tableName = this.getTableName("admin_access_token");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "`\n" +
                        "(\n" +
                        "    `id`          int(11)       NOT NULL AUTO_INCREMENT,\n" +
                        "    `clientId`    varchar(60)   NOT NULL DEFAULT '',\n" +
                        "    `adminId`     int(11)       NOT NULL DEFAULT 0,\n" +
                        "    `accessToken` varchar(60)   NOT NULL DEFAULT '',\n" +
                        "    `remoteAddr`  varchar(50)   NOT NULL DEFAULT '',\n" +
                        "    `userAgent`   varchar(2048) NOT NULL DEFAULT '',\n" +
                        "    `updatedAt`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                        "    PRIMARY KEY (`id`),\n" +
                        "    KEY `clientId` (`clientId`),\n" +
                        "    KEY `accessToken` (`clientId`, `accessToken`)\n" +
                        ") ENGINE = InnoDB\n" +
                        "  AUTO_INCREMENT = 12326\n" +
                        "  DEFAULT CHARSET = utf8mb4;");
    }

    private void createAdminGroupTable(Context context) {
        String tableName = this.getTableName("admin_group");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "`\n" +
                        "(\n" +
                        "    `id`   int(11)     NOT NULL AUTO_INCREMENT,\n" +
                        "    `name` varchar(30) NOT NULL DEFAULT '',\n" +
                        "    PRIMARY KEY (`id`)\n" +
                        ") ENGINE = InnoDB\n" +
                        "  AUTO_INCREMENT = 21\n" +
                        "  DEFAULT CHARSET = utf8mb4;");
    }

    private void createAdminGroupPermissionTable(Context context) {
        String tableName = this.getTableName("admin_group_permission");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "`\n" +
                        "(\n" +
                        "    `id`           int(11)     NOT NULL AUTO_INCREMENT,\n" +
                        "    `adminGroupId` int(11)     NOT NULL DEFAULT 0,\n" +
                        "    `actionId`     varchar(60) NOT NULL DEFAULT '',\n" +
                        "    PRIMARY KEY (`id`),\n" +
                        "    KEY `adminGroupId` (`adminGroupId`)\n" +
                        ") ENGINE = InnoDB\n" +
                        "  AUTO_INCREMENT = 580\n" +
                        "  DEFAULT CHARSET = utf8mb4;");
    }

    private void insertAdminGroupData(Context context) {
        String tableName = this.getTableName("admin_group");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "` (id, name) VALUES (11, '开发组');");
    }

    private void insertAdminData(Context context) {
        String tableName = this.getTableName("admin");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "` (id, adminGroupId, phone, email, name, avatar, password) VALUES (102, 11, '13012345678', 'admin@agile.test', '开发帐号', 'https://yuncars-other.oss-cn-shanghai.aliyuncs.com//agile/201912/6vhndpiufbuidayxg79f.jpg?x-oss-process=style/avatar', '$2a$10$Hjkwb41Fl6kgziqanBx86utS734DbfFsZoJyJ6LrQUQ81B1E/.PeC');");
    }

    private void insertAdminGroupPermissionData(Context context) {
        String tableName = this.getTableName("admin_group_permission");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (386, 11, 'site/sms-send');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (387, 11, 'site/clean-cache');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (388, 11, 'admin/index');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (389, 11, 'admin/create');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (390, 11, 'admin/view');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (391, 11, 'admin/edit');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (392, 11, 'admin/delete');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (393, 11, 'admin-group/index');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (394, 11, 'admin-group/create');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (395, 11, 'admin-group/view');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (396, 11, 'admin-group/edit');");
        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("INSERT INTO `" + tableName + "`(`id`, `adminGroupId`, `actionId`) VALUES (397, 11, 'admin-group/delete');");
    }

    private void createShopCategoryTable(Context context) {
        String tableName = this.getTableName("shop_category");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "`\n" +
                        "(\n" +
                        "    `id`          int(11)      NOT NULL AUTO_INCREMENT,\n" +
                        "    `parentId`    int(11)      NOT NULL DEFAULT '0',\n" +
                        "    `name`        varchar(50)  NOT NULL DEFAULT '',\n" +
                        "    `icon`        text,\n" +
                        "    `image`       varchar(500) NOT NULL DEFAULT '',\n" +
                        "    `description` text,\n" +
                        "    PRIMARY KEY (`id`),\n" +
                        "    KEY `parentId` (`parentId`)\n" +
                        ") ENGINE = InnoDB\n" +
                        "  AUTO_INCREMENT = 306\n" +
                        "  DEFAULT CHARSET = utf8mb4;");
    }

    private void createUserTable(Context context) {
        String tableName = this.getTableName("user");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("DROP TABLE IF EXISTS `" + tableName + "`;");

        new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
                .execute("CREATE TABLE `" + tableName + "`\n" +
                        "(\n" +
                        "    `id`          int(11)      NOT NULL AUTO_INCREMENT,\n" +
                        "    `phone`       varchar(30)  NOT NULL DEFAULT '',\n" +
                        "    `email`       varchar(90)  NOT NULL DEFAULT '',\n" +
                        "    `password`    varchar(200) NOT NULL DEFAULT '',\n" +
                        "    `name`        varchar(20)  NOT NULL DEFAULT '',\n" +
                        "    `avatar`      varchar(500) NOT NULL DEFAULT '',\n" +
                        "    `createdIp`   varchar(30)  NOT NULL DEFAULT '',\n" +
                        "    `createdFrom` varchar(20)  NOT NULL DEFAULT '',\n" +
                        "    `createdAt`   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                        "    `updatedAt`   timestamp    NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,\n" +
                        "    `deletedAt`   timestamp    NULL     DEFAULT NULL,\n" +
                        "    PRIMARY KEY (`id`),\n" +
                        "    KEY `phone` (`phone`) USING BTREE,\n" +
                        "    KEY `email` (`email`) USING BTREE,\n" +
                        "    KEY `deletedAt` (`deletedAt`)\n" +
                        ") ENGINE = InnoDB\n" +
                        "  AUTO_INCREMENT = 2363\n" +
                        "  DEFAULT CHARSET = utf8mb4;");
    }
}
