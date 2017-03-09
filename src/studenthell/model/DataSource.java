package studenthell.model;


public class DataSource extends AbstractDatasource {

    public DataSource() {
        super("jdbc:derby://localhost:1527/StudentHell", "StudentHell", "StudentHell");
    }

    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }

    private static class DataSourceHolder {

        private static final DataSource INSTANCE = new DataSource();
    }
}
