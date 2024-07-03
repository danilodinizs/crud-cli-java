package danilo.crudgamestore.repository;

import danilo.crudgamestore.connection.ConnectionFactory;
import danilo.crudgamestore.domain.Company;
import danilo.crudgamestore.repository.CompanyRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CompanyRepositoryTest {
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
        List<Company> companies = CompanyRepository.findByName("Riot Games");
        assertEquals(1, companies.size());
        assertEquals("Riot Games", companies.getFirst().getName());
    }

    @Test
    public void testDelete() {
        CompanyRepository.delete(1);
        List<Company> companies = CompanyRepository.findByName("Riot Games");
        assertTrue(companies.isEmpty());
    }

    @Test
    public void testInsert() {
        Company newCompany = Company.builder().name("New Company").build();
        CompanyRepository.insert(newCompany);
        List<Company> companies = CompanyRepository.findByName("New Company");
        assertEquals(1, companies.size());
        assertEquals("New Company", companies.get(0).getName());
    }

    @Test
    public void testFindById() {
        Optional<Company> company = CompanyRepository.findById(1);
        assertTrue(company.isPresent());
        assertEquals("Riot Games", company.get().getName());
    }

    @Test
    public void testUpdate() {
        Company updatedCompany = Company.builder().id(1).name("Updated Company").build();
        CompanyRepository.update(updatedCompany);
        Optional<Company> company = CompanyRepository.findById(1);
        assertTrue(company.isPresent());
        assertEquals("Updated Company", company.get().getName());
    }
}
