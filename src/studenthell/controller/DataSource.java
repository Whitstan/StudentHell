package studenthell.controller;

public class DataSource extends AbstractDatasource {

    private final HighScoreEntityController highScoreEntityController;

    public DataSource() {
        super("jdbc:derby://localhost:1527/StudentHell", "StudentHell", "StudentHell");
        highScoreEntityController = new HighScoreEntityController();
    }

    public HighScoreEntityController getController() {
        return highScoreEntityController;
    }

    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }

    private static class DataSourceHolder {

        private static final DataSource INSTANCE = new DataSource();
    }
}
