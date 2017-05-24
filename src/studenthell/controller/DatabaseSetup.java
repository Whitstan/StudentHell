package studenthell.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseSetup {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        try (
            Scanner scanner = new Scanner(new File("src/studenthell.sql"));
            Connection connection = DataSource.getInstance().getConnection();
            Statement statement = connection.createStatement()) {
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();
                if (sql.equals("")) {continue;}
                System.out.print(sql.replaceAll("\\s+", " "));
                System.out.print(": ");
                if (!sql.toUpperCase().startsWith("SELECT")) {
                    try {
                        statement.execute(sql);
                        System.out.println("OK");
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }
}
