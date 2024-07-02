package danilo.cruddevdojo.repository;

import danilo.cruddevdojo.connection.ConnectionFactory;
import danilo.cruddevdojo.domain.Company;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CompanyRepository {
    public static List<Company> findByName(String name) {
        log.info("Finding Company by name '{}'", name);
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindByName(connection, name);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                Company company = Company
                        .builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .build();
                companyList.add(company);
            }

        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
        return companyList;
    }
    private static PreparedStatement createPreparedStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = "SELECT * FROM games_store.developer_company WHERE name LIKE ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name) );
        return ps;
    }
}
