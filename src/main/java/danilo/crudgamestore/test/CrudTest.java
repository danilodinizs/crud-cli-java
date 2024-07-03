package danilo.crudgamestore.test;

import danilo.crudgamestore.repository.CompanyRepository;
import danilo.crudgamestore.services.CompanyServices;
import danilo.crudgamestore.services.GameServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class CrudTest {
    static final Logger log = LoggerFactory.getLogger(CompanyRepository.class);
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        while (true) {
            menu();
            op = Integer.parseInt(SCANNER.nextLine());
            if(op == 0) break;
            switch (op) {
                case 1 -> {
                    companyMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    CompanyServices.menu(op);
                }
                case 2 -> {
                    gameMenu();
                    op = Integer.parseInt(SCANNER.nextLine());
                    GameServices.menu(op);
                }
            }

        }
    }

    private static void companyMenu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Search for company");
        System.out.println("2. Delete a company");
        System.out.println("3. Insert a company");
        System.out.println("4. Update a company");
        System.out.println("9. Back");
    }

    private static void menu() {
        System.out.println("1. Company");
        System.out.println("2. Game");
        System.out.println("0. Exit");
    }

    private static void gameMenu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Search for game");
        System.out.println("2. Delete a game");
        System.out.println("3. Insert a game");
        System.out.println("4. Update a game");
        System.out.println("9. Back");
    }
}
