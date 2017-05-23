package studenthell.controller;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class HighScore extends JFrame{
    
    public HighScore(){
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        String url = "jdbc:derby://localhost:1527/StudentHell";
        String userid = "StudentHell";
        String password = "StudentHell";
        String sql = "SELECT * FROM HIGHSCORES ORDER BY SCORE DESC FETCH FIRST 10 ROWS ONLY";

        try (Connection connection = DriverManager.getConnection( url, userid, password );
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql )){
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            /*for (int i = 2; i <= columns; i++){
                columnNames.add( md.getColumnName(i) );
            }*/
            columnNames.add("Neptun-kód");
            columnNames.add("Pontszám");
            
            while (rs.next()){
                
                ArrayList row = new ArrayList(columns);
                
                for (int i = 2; i <= columns; i++){
                    row.add( rs.getObject(i) );
                }
                
                data.add( row );
            }
            
        }
        catch (SQLException e){
            System.out.println( e.getMessage() );
        }

        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++){
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++){
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ ){
            columnNamesVector.add(columnNames.get(i));
        }

        //create table with database data    
        JTable table = new JTable(dataVector, columnNamesVector){
            @Override
            public Class getColumnClass(int column){
                for (int row = 0; row < getRowCount(); row++){
                    Object o = getValueAt(row, column);

                    if (o != null){
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };
        
        DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
        renderer1.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(renderer1);
        
        DefaultTableCellRenderer renderer2 = new DefaultTableCellRenderer();
        renderer2.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(1).setCellRenderer(renderer2);
        
        table.setPreferredScrollableViewportSize(new Dimension(200,160));
        
        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );
        
        JPanel buttonPanel = new JPanel();
        getContentPane().add( buttonPanel, BorderLayout.SOUTH );
    }

    public static void main(String[] args){
        HighScore frame = new HighScore();
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
    }
}