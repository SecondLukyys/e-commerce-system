package system.repository;

import org.springframework.stereotype.Repository;
import system.model.Cart;
import org.springframework.data.neo4j.repository.Neo4jRepository;

@Repository
public interface CartRepository extends Neo4jRepository<Cart, Long> {

    Cart findByName(String name);

}
