package studenthell.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import studenthell.model.ConnectionFactory;
import studenthell.model.HighScoreEntity;

public class HighScoreEntityController{

    private final String FULL_SELECT_SQL;
    private final String SELECT_BY_ID_SQL;
    
    public HighScoreEntityController(String TABLE_NAME) {
        FULL_SELECT_SQL = "SELECT * FROM " + TABLE_NAME;
        SELECT_BY_ID_SQL = FULL_SELECT_SQL + " WHERE ID = ";
    }

    public interface RunnableOnResultSet {public void run(ResultSet rs) throws SQLException;}

    public void doOnResultSet(String sql, int resultSetType, int resultSetConcurrency, RunnableOnResultSet todo) throws SQLException {
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
            Statement statement = connection.createStatement(resultSetType, resultSetConcurrency);
            ResultSet rs = statement.executeQuery(sql);) {
            
            todo.run(rs);
        }
    }

    public List<HighScoreEntity> getEntities() throws SQLException {
        final List<HighScoreEntity> entities = new ArrayList<>();
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            while (rs.next()) {
                HighScoreEntity entity = newEntity();
                entity.setID(rs.getInt("ID"));
                setEntityAttributes(entity, rs);
                entities.add(entity);
            }
        });
        return entities;
    }

    public HighScoreEntity getEntityByID(int ID) throws SQLException {
        final HighScoreEntity entity = newEntity();
        doOnResultSet(SELECT_BY_ID_SQL + ID, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            rs.next();
            entity.setID(rs.getInt("ID"));
            setEntityAttributes(entity, rs);
        });
        return entity;
    }

    public HighScoreEntity getEntityByRowIndex(final int sorIndex) throws SQLException {
        final HighScoreEntity highScoreEntity = newEntity();
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            rs.absolute(sorIndex + 1);
            highScoreEntity.setID(rs.getInt("ID"));
            setEntityAttributes(highScoreEntity, rs);
        });
        return highScoreEntity;
    }

     public void setEntityAttributes(HighScoreEntity highScoreEntity, ResultSet resultSet) throws SQLException {
        highScoreEntity.setPlayername(resultSet.getString("PLAYERNAME"));
        highScoreEntity.setScore(resultSet.getLong("SCORE"));
    }
    
    public void addEntity(final HighScoreEntity entity) throws SQLException {
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            rs.moveToInsertRow();
            rs.updateInt("ID", ConnectionFactory.getInstance().obtainNewID());
            getEntityAttributes(rs, entity);
            rs.insertRow();
        });
    }

    public void getEntityAttributes(ResultSet resultSet, HighScoreEntity highScoreEntity) throws SQLException {
        resultSet.updateString("PLAYERNAME", highScoreEntity.getPlayername());
        resultSet.updateLong("SCORE", highScoreEntity.getScore());
    }
    
    public HighScoreEntity newEntity(){
        return new HighScoreEntity();
    }
}
