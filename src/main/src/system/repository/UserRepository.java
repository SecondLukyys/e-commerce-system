package system.repository;


import org.springframework.stereotype.Repository;
import system.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

}

