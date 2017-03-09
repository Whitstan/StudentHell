package studenthell.model;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatasource {

    public final ConnectionFactory connectionFactory;

    public AbstractDatasource(String url, String user, String password) {
        ConnectionFactory.initializeInstance(url, user, password);
        connectionFactory = ConnectionFactory.getInstance();
    }

    public Connection getConnection() throws SQLException {return ConnectionFactory.getInstance().getConnection();}
}
