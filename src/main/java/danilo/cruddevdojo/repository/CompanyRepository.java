package danilo.cruddevdojo.repository;

import danilo.cruddevdojo.connection.ConnectionFactory;
import danilo.cruddevdojo.domain.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CompanyRepository {

    static final Logger log = LoggerFactory.getLogger(CompanyRepository.class);

    public static List<Company> findByName(String name) {
        log.info("Finding Company by name {}", name);
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
        String sql = "SELECT * FROM games_store.company WHERE name LIKE ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }

    public static void delete(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementDelete(connection, id)) {
            ps.executeUpdate();
            log.info("Deleted Company '{}' from database", id);
        } catch (SQLException e) {
            log.error("Error while trying to delete Company '{}' from database", id, e);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection connection, Integer id) throws SQLException {
        String sql = "DELETE FROM `games_store`.`company` WHERE (`id` = ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void insert(Company company) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementInsert(connection, company)) {
            ps.executeUpdate();
            log.info("Inserted Company '{}' in the database", company.getName());
        } catch (SQLException e) {
            log.error("Error while trying to insert Company '{}'", company.getName(), e);
        }
    }

    private static PreparedStatement createPreparedStatementInsert(Connection connection, Company company) throws SQLException {
        String sql = "INSERT INTO `games_store`.`company` (`name`) VALUES (?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, company.getName());
        return ps;
    }

    public static Optional<Company> findById(Integer id) {
        log.info("Finding Company by id {}", id);
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindById(connection, id);
             ResultSet resultSet = ps.executeQuery()) {
            if (!resultSet.next()) return Optional.empty();
            return Optional.of(Company
                    .builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .build());

        } catch (SQLException e) {
            log.error("Error while trying to find all Developer Companies", e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPreparedStatementFindById(Connection connection, Integer id) throws SQLException {
        String sql = "SELECT * FROM games_store.company WHERE id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void update(Company company) {
        log.info("Updating company");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementUpdate(connection, company)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while trying to update Company '{}'", company.getId(), e);
        }
    }

    private static PreparedStatement createPreparedStatementUpdate(Connection connection, Company company) throws SQLException {
        String sql = "UPDATE `games_store`.`company` SET `name` = ? WHERE (`id` = ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, company.getName());
        ps.setInt(2, company.getId());
        return ps;
    }

}
