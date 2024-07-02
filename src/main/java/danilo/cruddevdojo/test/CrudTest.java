package danilo.cruddevdojo.test;

import danilo.cruddevdojo.repository.CompanyRepository;
import danilo.cruddevdojo.services.CompanyServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class CrudTest {
    static final Logger log = LoggerFactory.getLogger(CompanyRepository.class);
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        while (true) {
            companyMenu();
            op = Integer.parseInt(SCANNER.nextLine());
            if(op == 0) break;
            CompanyServices.menu(op);
        }
    }

    private static void companyMenu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Search for company");
        System.out.println("2. Delete a company");
        System.out.println("3. Insert a company");
        System.out.println("4. Update a company");
        System.out.println("0. Exit");
    }
}
