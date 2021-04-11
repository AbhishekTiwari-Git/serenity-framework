package ucc.utils;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Optional;

public class DbUtils {

    private static final EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();

    public static ResultSet executeQuery(Connection conn, String URL, String query) {
        ResultSet resultSet = null;
        try (Connection connection = conn;
             Statement statement = connection.createStatement();) {

            resultSet = statement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public static String callProcedureTranlateSKUTerm(Connection conn, String URL, String sku, String term) {

        try (Connection connection = conn) {

            String sql = "{call dbo.tranlateSKUTerm(?, ?)}";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, sku);
            pstmt.setString(2, term);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString("priceResponse");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String callProceduregetProductPrices(
        Connection connect, String promo, String category,
        String country, String custNum, String products
    ) {

        try (Connection connection = connect) {

            String sql = "{call dbo.getProductPrices(?, ?, ?, ?, ?)}";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, promo);
            pstmt.setString(2, category);
            pstmt.setString(3, country);
            pstmt.setString(4, custNum);
            pstmt.setString(5, products);
            ResultSet rs = pstmt.executeQuery();
            // TODO need refactor
            while (rs.next()) {
                return rs.getString("priceResponse");

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //
    // MSSQL database methods
    public String buildDbUrl(String dbHost) {
        return EnvironmentSpecificConfiguration.from(envVar).getProperty(dbHost);
    }

    public String acsDBUser() {
        return Optional.ofNullable(System.getProperty("acsDBDevUser"))
            .orElse(System.getProperty("acsDBQAUser"));
    }

    public String acsDBPass() {
        return Optional.ofNullable(System.getProperty("acsDBDevPass"))
            .orElse(System.getProperty("acsDBQAPass"));
    }

    public String buildQuery(String fileName) throws IOException {
        String filePath = envVar.getProperty("sql.query.path");
        String sqlQuery = filePath + "/" + fileName;
        File file = new File(sqlQuery);
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
