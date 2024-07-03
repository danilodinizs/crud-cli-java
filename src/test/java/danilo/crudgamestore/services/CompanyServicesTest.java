package danilo.crudgamestore.services;

import danilo.crudgamestore.domain.Company;
import danilo.crudgamestore.repository.CompanyRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class CompanyServicesTest {

    @Test
    public void testInsertCompany() {
        Company company = Company.builder().name("Test Company").build();
        CompanyRepository.insert(company);
        Optional<Company> insertedCompany = CompanyRepository.findByName("Test Company").stream().findFirst();
        assertTrue(insertedCompany.isPresent());
        assertEquals("Test Company", insertedCompany.get().getName());
    }

    @Test
    public void testUpdateCompany() {
        Company company = Company.builder().id(1).name("Test Company Updated").build();
        CompanyRepository.update(company);
        Optional<Company> updatedCompany = CompanyRepository.findById(1);
        assertTrue(updatedCompany.isPresent());
        assertEquals("Test Company Updated", updatedCompany.get().getName());
    }

    @Test
    public void testDeleteCompany() {
        Optional<Company> deletedCompany = CompanyRepository.findById(12);
        CompanyRepository.delete(12);
        assertTrue(deletedCompany.isPresent());
    }
}
