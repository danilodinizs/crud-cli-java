package danilo.crudgamestore.services;

import danilo.crudgamestore.domain.Company;
import danilo.crudgamestore.repository.CompanyRepository;

import java.util.Optional;
import java.util.Scanner;

public class CompanyServices {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void menu(int op) {
        switch (op) {
            case 1 -> findByName();
            case 2 -> delete();
            case 3 -> insert();
            case 4 -> update();
        }
    }
    private static void findByName() {
        System.out.println("Type the name or empty to all");
        String name = SCANNER.nextLine();
        CompanyRepository.findByName(name)
                .forEach(c -> System.out.printf("ID: %d | NAME: %s\n", c.getId(), c.getName()));
//        List<Company> companies = CompanyRepository.findByName(name);
//        for (int i = 0; i < companies.size(); i++) {
//            Company c = companies.get(i);
//            System.out.printf("[%d] - ID: %d | NAME: %s\n", i, c.getId(), c.getName());
//        }
    }
    private static void delete() {
        System.out.println("Type the id of the company you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y/N");
        String choice = SCANNER.nextLine();
        if(choice.toLowerCase().startsWith("y")) CompanyRepository.delete(id);
        else if(choice.toLowerCase().startsWith("n")) delete();
        else throw new IllegalArgumentException("Not a valid option");
    }

    private static void insert() {
        System.out.println("Type the name of the company you want to insert");
        Company company = Company.builder().name(SCANNER.nextLine()).build();
        CompanyRepository.insert(company);

    }

    private static void update() {
        System.out.println("Type the id of the company you want to update");
        Optional<Company> companyOptional = CompanyRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (companyOptional.isEmpty()) {
            System.out.println("Company not found");
            return;
        }
        Company company = companyOptional.get();
        System.out.println("Company found: " + company);
        System.out.println("Type the new name to this company or enter to keep the same");
        String name = SCANNER.nextLine();
        Company c = Company
                .builder()
                .id(company.getId())
                .name(name = name.isEmpty() ? company.getName() : name)
                .build();
        CompanyRepository.update(c);
    }
}
