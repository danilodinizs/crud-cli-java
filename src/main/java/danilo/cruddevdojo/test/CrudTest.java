package danilo.cruddevdojo.test;

import java.util.Scanner;

public class CrudTest {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
//        while (true) {
//            companyMenu();
//            op = Integer.parseInt(SCANNER.nextLine());
//            if(op == 0) break;
//            CompanyServices.menu(op);
//        }
    }

    private static void companyMenu() {
        System.out.println("Type the number of your operation");
        System.out.println("1. Search for company");
        System.out.println("2. Delete a company");
        System.out.println("3. Insert a company");
        System.out.println("0. Exit");
    }
}
