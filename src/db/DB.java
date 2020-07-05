package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public  static Connection getConnection(String db) {
        if (conn == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
                useDataBase(conn, db);
            } catch (SQLException throwables) {
                throw new DbException(throwables.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throw new DbException(throwables.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException throwables) {
                throw new DbException(throwables.getMessage());
            }
        }
    }

    public static void closeResultset(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throw new DbException(throwables.getMessage());
            }
        }
    }

    private static void useDataBase(Connection conn, String db) {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            statement.execute("USE " + db);
        } catch (SQLException throwables) {
            throw new DbException(throwables.getMessage());
        }
        finally {
            DB.closeStatement(statement);
        }
    }

    private static Properties loadProperties() {
        try(FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
}
