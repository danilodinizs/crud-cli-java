package danilo.crudgamestore.repository;

import danilo.crudgamestore.connection.ConnectionFactory;
import danilo.crudgamestore.domain.Company;
import danilo.crudgamestore.domain.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GameRepositoryTest {
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        connection = ConnectionFactory.getConnection();
    }

    @After
    public void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testFindByName() {
        List<Game> games = GameRepository.findByName("Grand Thef Auto V");
        assertEquals(1, games.size());
        assertEquals("Grand Thef Auto V", games.get(0).getName());
    }

    @Test
    public void testDelete() {
        GameRepository.delete(1);
        List<Game> games = GameRepository.findByName("Grand Thef Auto V");
        assertTrue(games.isEmpty());
    }

    @Test
    public void testInsert() {
        Company company = Company.builder().id(1).name("Riot Games").build();
        Game newGame = Game.builder().name("New Game").price(49.99).company(company).build();
        GameRepository.insert(newGame);
        List<Game> games = GameRepository.findByName("New Game");
        assertEquals(1, games.size());
        assertEquals("New Game", games.get(0).getName());
    }

    @Test
    public void testFindById() {
        Optional<Game> game = GameRepository.findById(2);
        assertTrue(game.isPresent());
        assertEquals("New Game", game.get().getName());
    }

    @Test
    public void testUpdate() {
        Company company = Company.builder().id(1).name("Updated Company").build();
        Game updatedGame = Game.builder().id(2).name("Updated Game").price(69.99).company(company).build();
        GameRepository.update(updatedGame);
        Optional<Game> game = GameRepository.findById(2);
        assertTrue(game.isPresent());
        assertEquals("Updated Game", game.get().getName());
    }
}
