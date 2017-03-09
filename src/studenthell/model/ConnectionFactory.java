package studenthell.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    public final String password;
    public final String url;
    public final String user;

    public ConnectionFactory(String url, String user, String password) {
        this.password = password;
        this.url = url;
        this.user = user;
    }

    public Connection getConnection() throws SQLException {return DriverManager.getConnection(url, user, password);}

    public int obtainNewID() throws SQLException {
        int ID;
        try     (
                final Connection connection = getConnection();
                final Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, 
                      ResultSet.CONCUR_UPDATABLE);
                final ResultSet rs = statement.executeQuery("SELECT VALUE_ FROM SEQ")) {
                rs.next();
                ID = rs.getInt("VALUE_") + 1;
                rs.updateInt("VALUE_", ID);
                rs.updateRow();
            }
        return ID;
    }
    
    public static ConnectionFactory instance = null;

    public static void initializeInstance(String url, String user, String password) {instance = new ConnectionFactory(url, user, password);}

    public static ConnectionFactory getInstance() {return instance;}
}
