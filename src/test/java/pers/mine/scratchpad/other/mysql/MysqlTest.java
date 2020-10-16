package pers.mine.scratchpad.other.mysql;

import org.junit.Test;
import pers.mine.scratchpad.util.DbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Mine
 * @create 2020-10-16 16:29
 */
public class MysqlTest {

    @Test
    public void querySqlExecuteTest1() throws Exception {
        String url = "jdbc:mysql://localhost:5306/test_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String sql = "-- Dear mysql \n--  I'm not a bad sql \nselect now();";

        Connection conn = null;
        Statement stmt = null;
        ResultSet result = null;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            conn.setReadOnly(false);
            stmt = conn.createStatement();
            boolean flag = stmt.execute(sql);
            System.out.println(flag);
            result = stmt.getResultSet();
            while (result.next()) {
                System.out.println(result.getString(1));
            }
        } finally {
            DbUtils.close(result, stmt, conn);
        }
    }

    @Test
    public void querySqlExecuteTest2() throws Exception {
        String url = "jdbc:mysql://localhost:5306/test_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false&useCursorFetch=true";
        String sql = "-- Dear mysql \n--  I'm not a bad sql \nselect now();";

        Connection conn = null;
        Statement stmt = null;
        ResultSet result = null;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            conn.setReadOnly(true);
            stmt = conn.createStatement();
            stmt.setFetchSize(100);
            result = stmt.executeQuery(sql);
            while (result.next()) {
                System.out.println(result.getString(1));
            }
        } finally {
            DbUtils.close(result, stmt, conn);
        }
    }
}
