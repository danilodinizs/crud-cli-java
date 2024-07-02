package danilo.cruddevdojo.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Game {
    Integer id;
    String name;
    Double price;
    Company company;
}
