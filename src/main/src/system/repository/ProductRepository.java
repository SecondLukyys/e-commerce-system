package system.repository;

import system.model.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends Neo4jRepository<Product, Long> {


    List<Product> findByPrice(int price);
    List<Product> findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findByPricestring(String pricestring);
    List<Product> findByType(String type);
    List<Product> findByDescription(String description);
    List<Product> findBySellcountstring(String sellcountstring);
    List<Product> findByratingstring(String ratingstring);
    List<Product> findByImagenamestring(String imagenamestring);
    List<Product> findAllByNameIn(List<String> names);
    //Product findByName(String name);
}
