package danilo.crudgamestore.domain;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class Company {
    Integer id;
    String name;
}
