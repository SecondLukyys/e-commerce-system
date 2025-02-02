package system.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalDateTime creationdate;

    private int productsum = 0;

    @Relationship(type = "IN_CART")
    private List<Product> products = new ArrayList<>();

    @Relationship(type = "HAS_PRODUCT")
    private List<CartProductRelationship> cartProductRelationships;

    public List<CartProductRelationship> getCartProductRelationships() {
        return cartProductRelationships;
    }

    public void setCartProductRelationships(List<CartProductRelationship> cartProductRelationships) {
        this.cartProductRelationships = cartProductRelationships;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getProductsum() {
        return productsum;
    }

    public void setProductsum(int productsum) {
        this.productsum = productsum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationdate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationdate = creationDate;
    }
}
