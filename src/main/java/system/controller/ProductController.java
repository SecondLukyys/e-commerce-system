package system.controller;

import system.model.Product;
import system.model.Variables;
import system.repository.CartRepository;
import system.repository.ProductRepository;
import system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CartRepository getCartRepository() {
        return cartRepository;
    }

    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(List<Product> productsA, String name1){

        for(Product obj: productsA){
            if(obj.getName().equals(name1)){
                Variables.getProductscart().add(obj);
            }
        }

    }

    public void addToCartAnon(List<Product> productsA, String name1){

        for(Product obj: productsA){
            if(obj.getName().equals(name1)){
                Variables.getProductscartanon().add(obj);
            }
        }

    }

}
