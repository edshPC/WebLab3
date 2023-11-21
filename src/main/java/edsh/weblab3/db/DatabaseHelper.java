package edsh.weblab3.db;

import edsh.weblab3.bean.CheckHitBean;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class DatabaseHelper {
    private static DatabaseHelper instance;

    private Connection connection;

    private DatabaseHelper() {}

    public boolean connect() {
        Properties properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties");
                InputStreamReader ir = new InputStreamReader(is)) {
            properties.load(ir);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql:studs", properties);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean createTables() {
        try {
            Statement st = connection.createStatement();
            st.execute(SQLRequests.tablesCreation);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean addResult(String uuid, CheckHitBean result) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.insertResult);
            int i = 1;
            ps.setString(i++, uuid);
            ps.setDouble(i++, result.getX());
            ps.setDouble(i++, result.getY());
            ps.setDouble(i++, result.getR());
            ps.setBoolean(i++, result.isResult());
            ps.setTimestamp(i++, Timestamp.valueOf(result.getDateTime()));
            ps.setDouble(i++, result.getExecTime());
            ps.executeUpdate();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    public LinkedList<CheckHitBean> getAllResults(String uuid) {
        LinkedList<CheckHitBean> list = new LinkedList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.selectAllResults);
            ps.setString(1, uuid);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                CheckHitBean result = new CheckHitBean();
                result.setX(res.getDouble("x"));
                result.setY(res.getDouble("y"));
                result.setR(res.getDouble("r"));
                result.setResult(res.getBoolean("result"));
                result.setDateTime(res.getTimestamp("dateTime").toLocalDateTime());
                result.setExecTime(res.getDouble("execTime"));
                list.add(result);
            }
        } catch (SQLException ignored) {}
        return list;
    }

    public boolean removeAllResults(String uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLRequests.deleteAllResults);
            ps.setString(1, uuid);
            ps.executeUpdate();
            return true;
        } catch (SQLException ignored) {}
        return false;
    }

    public static DatabaseHelper get() {
        if(instance == null) {
            instance = new DatabaseHelper();
            instance.connect();
            instance.createTables();
        }
        return instance;
    }

}
