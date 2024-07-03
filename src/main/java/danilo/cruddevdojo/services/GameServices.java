package danilo.cruddevdojo.services;

import danilo.cruddevdojo.domain.Company;
import danilo.cruddevdojo.domain.Game;
import danilo.cruddevdojo.repository.CompanyRepository;
import danilo.cruddevdojo.repository.GameRepository;

import java.util.Optional;
import java.util.Scanner;

public class GameServices {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void menu(int op) {
        switch (op) {
            case 1 -> findByName();
            case 2 -> delete();
            case 3 -> insert();
            case 4 -> update();
            default -> throw new IllegalArgumentException("Not a valid option");
        }
    }
    private static void findByName() {
        System.out.println("Type the name or empty to all");
        String name = SCANNER.nextLine();
        CompanyRepository.findByName(name)
                .forEach(c -> System.out.printf("ID: %d | NAME: %s\n", c.getId(), c.getName()));
//        List<Game> companies = CompanyRepository.findByName(name);
//        for (int i = 0; i < companies.size(); i++) {
//            Game c = companies.get(i);
//            System.out.printf("[%d] - ID: %d | NAME: %s\n", i, c.getId(), c.getName());
//        }
    }
    private static void delete() {
        System.out.println("Type the id of the game you want to delete");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("Are you sure? Y/N");
        String choice = SCANNER.nextLine();
        if(choice.toLowerCase().startsWith("y")) CompanyRepository.delete(id);
        else if(choice.toLowerCase().startsWith("n")) delete();
        else throw new IllegalArgumentException("Not a valid option");
    }

    private static void insert() {
        System.out.println("Type the name of the game you want to insert");
        String name = SCANNER.nextLine();
        System.out.println("Type the price of the game");
        double price = Double.parseDouble(SCANNER.nextLine());
        System.out.println("Type the id of the company");
        Integer id = Integer.parseInt(SCANNER.nextLine());

        Game game = Game
                .builder()
                .name(name)
                .price(price)
                .company(Company.builder().id(id).build())
                .build();
        GameRepository.insert(game);

    }

    private static void update() {
        System.out.println("Type the id of the game you want to update");
        Optional<Game> gameOptional = GameRepository.findById(Integer.parseInt(SCANNER.nextLine()));
        if (gameOptional.isEmpty()) {
            System.out.println("Game not found");
            return;
        }
        Game game = gameOptional.get();
        System.out.println("Game found: " + game);
        System.out.println("Type the new name to this game or enter to keep the same");
        String name = SCANNER.nextLine();
        name = name.isEmpty() ? game.getName() : name;
        System.out.println("Type the price to this new game");
        Double price = Double.parseDouble(SCANNER.nextLine());
        Game g = Game
                .builder()
                .id(game.getId())
                .price(price)
                .company(game.getCompany())
                .name(name)
                .build();
        GameRepository.update(g);
    }
}
