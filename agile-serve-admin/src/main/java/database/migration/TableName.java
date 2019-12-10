package database.migration;

public class TableName {
    // @todo 修改数据库表前缀
    public static final String TABLE_PREFIX = "ag_";

    public static String getTableName(String tableName) {
        return TABLE_PREFIX + tableName;
    }
}
