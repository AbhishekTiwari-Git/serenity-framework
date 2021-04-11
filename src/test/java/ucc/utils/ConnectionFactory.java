package ucc.utils;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();

    public static Connection getConnectionACS(String URL, String user, String password)
    {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }

    }

    public static Connection getConnectionRDS(String URL, String user, String password)
    {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }

    }

}