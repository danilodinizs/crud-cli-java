package danilo.crudgamestore.services;

import danilo.crudgamestore.connection.ConnectionFactory;
import danilo.crudgamestore.domain.Company;
import danilo.crudgamestore.domain.Game;
import danilo.crudgamestore.repository.GameRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class GameServicesTest {
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
    public void testInsertGame() {
        Company company = Company.builder().id(11).name("New Company").build();
        Game game = Game.builder().name("New Game").price(49.99).company(company).build();
        GameRepository.insert(game);
        Optional<Game> insertedGame = GameRepository.findByName("New Game").stream().findFirst();
        assertTrue(insertedGame.isPresent());
        assertEquals("New Game", insertedGame.get().getName());
    }

    @Test
    public void testUpdateGame() {
        Company company = Company.builder().id(11).name("New Company ").build();
        Game game = Game.builder().id(1).name("Updated Game").price(69.99).company(company).build();
        GameRepository.update(game);
        Optional<Game> updatedGame = GameRepository.findById(1);
        assertTrue(updatedGame.isPresent());
        assertEquals("Updated Game", updatedGame.get().getName());
        assertEquals(69.99, updatedGame.get().getPrice(), 0.01);
    }

    @Test
    public void testDeleteGame() {
        GameRepository.delete(1);
        Optional<Game> deletedGame = GameRepository.findById(1);
        assertFalse(deletedGame.isPresent());
    }
}
