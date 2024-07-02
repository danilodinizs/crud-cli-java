package danilo.cruddevdojo.services;

import danilo.cruddevdojo.domain.Company;
import danilo.cruddevdojo.repository.CompanyRepository;

import java.util.List;

public class CompanyServices {
    public static List<Company> findByName(String name) {
        return CompanyRepository.findByName(name);
    }
}
