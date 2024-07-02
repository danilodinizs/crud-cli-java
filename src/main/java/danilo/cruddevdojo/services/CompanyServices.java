package danilo.cruddevdojo.services;

import danilo.cruddevdojo.domain.Company;
import danilo.cruddevdojo.repository.CompanyRepository;

import java.util.List;
import java.util.Scanner;

public class CompanyServices {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void menu(int op) {
        switch(op){
            case 1: findByName();
            break;
            case 2: delete();
            break;
            default:
                throw new IllegalArgumentException("Not a valid option");
        }
    }
    private static void findByName() {
        System.out.println("Type the name or empty to all");
        String name = SCANNER.nextLine();
        List<Company> companies = CompanyRepository.findByName(name);
        for (int i = 0; i < companies.size(); i++) {
            Company c = companies.get(i);
            System.out.printf("[%d] - ID: %d | NAME: %s\n", i, c.getId(), c.getName());
        }
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
}
