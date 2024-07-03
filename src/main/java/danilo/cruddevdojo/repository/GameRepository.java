package danilo.cruddevdojo.repository;

import danilo.cruddevdojo.connection.ConnectionFactory;
import danilo.cruddevdojo.domain.Company;
import danilo.cruddevdojo.domain.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GameRepository {

    static final Logger log = LoggerFactory.getLogger(GameRepository.class);

    public static List<Game> findByName(String name) {
        log.info("Finding Game by name {}", name);
        List<Game> gameList = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindByName(connection, name);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                Company company = Company.builder().name(resultSet.getString("company"))
                        .id(resultSet.getInt("companyId"))
                        .build();
                Game game = Game
                        .builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getDouble("price"))
                        .company(company)
                        .build();
                gameList.add(game);
            }

        } catch (SQLException e) {
            log.error("Error while trying to find all Games", e);
        }
        return gameList;
    }

    private static PreparedStatement createPreparedStatementFindByName(Connection connection, String name) throws SQLException {
        String sql = """
                SELECT g.id, g.name, g.price, g.companyId, c.name as company
                FROM games_store.company c
                INNER JOIN games_store.game g ON g.companyId = c.id
                WHERE g.name LIKE ?;
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", name));
        return ps;
    }

    public static void delete(int id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementDelete(connection, id)) {
            ps.executeUpdate();
            log.info("Deleted Game '{}' from database", id);
        } catch (SQLException e) {
            log.error("Error while trying to delete Game '{}' from database", id, e);
        }
    }

    private static PreparedStatement createPreparedStatementDelete(Connection connection, Integer id) throws SQLException {
        String sql = "DELETE FROM `games_store`.`game` WHERE (`id` = ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void insert(Game game) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementInsert(connection, game)) {
            ps.executeUpdate();
            log.info("Inserted Game '{}' in the database", game.getName());
        } catch (SQLException e) {
            log.error("Error while trying to insert Game '{}'", game.getName(), e);
        }
    }

    private static PreparedStatement createPreparedStatementInsert(Connection connection, Game game) throws SQLException {
        String sql = "INSERT INTO `games_store`.`game` (`name`,`price`,`companyId`) VALUES (?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, game.getName());
        ps.setDouble(2, game.getPrice());
        ps.setInt(3, game.getCompany().getId());
        return ps;
    }

    public static Optional<Game> findById(Integer id) {
        log.info("Finding Game by id {}", id);
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementFindById(connection, id);
             ResultSet resultSet = ps.executeQuery()) {
            if (!resultSet.next()) return Optional.empty();
            Company company = Company.builder().name(resultSet.getString("company"))
                    .id(resultSet.getInt("companyId"))
                    .build();
            Game game = Game
                    .builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .price(resultSet.getDouble("price"))
                    .company(company)
                    .build();
            return Optional.of(game);

        } catch (SQLException e) {
            log.error("Error while trying to find all Games", e);
        }
        return Optional.empty();
    }

    private static PreparedStatement createPreparedStatementFindById(Connection connection, Integer id) throws SQLException {
        String sql = """
                SELECT g.id, g.name, g.price, g.companyId, c.name as company
                FROM games_store.company c
                INNER JOIN games_store.game g ON g.companyId = c.id
                WHERE g.id = ?;
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void update(Game game) {
        log.info("Updating game");
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementUpdate(connection, game)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error while trying to update Game '{}'", game.getId(), e);
        }
    }

    private static PreparedStatement createPreparedStatementUpdate(Connection connection, Game game) throws SQLException {
        String sql = "UPDATE `games_store`.`game` SET `name` = ?, `price` = ? WHERE (`id` = ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, game.getName());
        ps.setDouble(2, game.getPrice());
        ps.setInt(3, game.getId());
        return ps;
    }

}
