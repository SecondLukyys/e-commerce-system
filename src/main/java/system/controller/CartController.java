package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.model.*;
import system.repository.CartRepository;
import system.repository.ProductRepository;
import system.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/cart")
    public String cartAnon(Model model) {

        List<Product> products = Variables.getProductscartanon();
        int sumproducts=0;


        for(Product obj: products){
            sumproducts = sumproducts + obj.getPrice();

        }

        model.addAttribute("products", products);
        model.addAttribute("sumProducts", sumproducts);
        return "cart";
    }

    @GetMapping("/cartlogged")
    public String cartLogged(Model model) {

        List<Product> products = Variables.getProductscart();
        int sumproducts=0;


        for(Product obj: products){
            sumproducts = sumproducts + obj.getPrice();
        }


        model.addAttribute("products", products);
        model.addAttribute("sumProducts", sumproducts);
        return "cartlogged";
    }

    @GetMapping("/checkoutanon")
    public String cartLoggedCheckoutAnon(Model model) {

        Variables.getProductscartanon().clear();
        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);
        return "productscatalogue";
    }

    @GetMapping("/checkout")
    public String cartLoggedCheckout(Model model) {


        if(Variables.getProductscart().isEmpty()){

            List<Product> products = productRepository.findAll();


            model.addAttribute("products", products);
            Variables.getProductscart().clear();
            return "productscataloguelogged";
        }

        createUserHasCart(Variables.getUsername());

        Cart cart = cartRepository.findByName(Variables.getCurrentCartName());

        for (Product obj : Variables.getProductscart()) {
            // Check if the product already exists in the cart
            Optional<CartProductRelationship> existingRelationship = cart.getCartProductRelationships()
                    .stream()
                    .filter(rel -> rel.getProduct().getName().equals(obj.getName()))
                    .findFirst();

            if (existingRelationship.isPresent()) {
                // Relationship already exists, update the count
                int currentCount = existingRelationship.get().getCount();
                existingRelationship.get().setCount(currentCount + 1);
            } else {
                // Relationship is new, create a new CartProductRelationship
                CartProductRelationship relationship = new CartProductRelationship();
                relationship.setProduct(obj);
                relationship.setCount(1);
                cart.getCartProductRelationships().add(relationship);
            }
        }

        if(Variables.getProductscart().size() > 0){
            cartRepository.save(cart);
            System.out.println("LOOOOOOOLOLOLOLOLOLOLOL");
        }





        List<Product> productsall = productRepository.findAll();
        List<Product> productsfromcart = Variables.getProductscart();
        int cartsize = Variables.getProductscart().size();
        Product producttoadd = new Product();


        for (Product product : productsall) {
            for (Product cartProduct : productsfromcart) {
                if (Objects.equals(product.getName(), cartProduct.getName())) {
                    product.setSellcount(product.getSellcount() + 1);
                    producttoadd = product;
                    productRepository.save(producttoadd);
                    break;
                }
            }
        }



        ///////////////////////////// Cia ant prisijungusio vartotojo prirasyti nupirktos prekes pavadinima su _


        User user = userRepository.findByUsername(Variables.getUsername()); // gali buti ne vienas produktas
        List<Product> productList = Variables.getProductscart();
        StringBuilder builder = new StringBuilder();


        for (Product product : productList) {
            builder.append(product.getName()).append("_");
        }


        String productNames = builder.toString();
        productNames = "_" + productNames;
        String newboughtproducts = user.getBoughtproducts();
        newboughtproducts = newboughtproducts + productNames;
        user.setBoughtproducts(newboughtproducts);
        userRepository.save(user);
        Variables.getProductscart().clear();
        List<Product> products = productRepository.findAll();


        model.addAttribute("products", products);
        Variables.getProductscart().clear();
        return "productscataloguelogged";
    }

    @PostMapping("/productscataloguenameanon")
    public String addToCart(@ModelAttribute("name") String name1, Model model) {

        List<Product> products = productRepository.findAll();
        Product findcat = new Product();


        for (Product obj : products) {
            if (obj.getName().equals(name1)) {
                findcat = (obj);
                break;
            }
        }

        Variables.getProductscartanon().add(findcat);

        model.addAttribute("products", products);

        return "productscatalogue";
    }

    @PostMapping("/productscatalogueloggedname")
    public String addToCartRegistered(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = productRepository.findAll();
        Product findcat = new Product();


        for (Product obj : productsA) {
            if (obj.getName().equals(name1)) {
                findcat = (obj);
                break;
            }
        }


        Variables.getProductscart().add(findcat);
        List<Product> products = productRepository.findAll();



        model.addAttribute("products", products);

        return "productscataloguelogged";
    }

    private void createUserHasCart(String username) {

        Cart cart = new Cart();

        int sum = 0;

        for(Product obj: Variables.getProductscart()){
            sum = sum + obj.getPrice();
        }

        cart.setProductsum(sum);

        // Save the cart to the database
        cartRepository.save(cart);

        // After saving, the ID will be assigned
        //System.out.println(cart.getId() + " KREPSELIO ID");

        //System.out.println(cart.getCreationDate() + "  current user");
        Variables.setCurrentCartId(cart.getId());
        Variables.setCurrentCartName(username+"Cart"+Variables.getCurrentCartId().toString());


        System.out.println(Variables.getCurrentCartName()+" Dabartinis vartotojo krepselis");

        User user = userRepository.findByUsername(username);


        cart.setName(username+"Cart"+Variables.getCurrentCartId().toString());

        cart.setCreationDate(LocalDateTime.now());

        cartRepository.save(cart);

        user.getCarts().add(cart);

        userRepository.save(user);

    }

    @PostMapping("/productscatalogueloggednameremove")
    public String removeProduct(@RequestParam("name") String name1, Model model) {

        List<Product> products = Variables.getProductscart();

        for(Product obj: products){

            if(obj.getName().equals(name1)){

                products.remove(obj);

                break;
            }

        }

        int sumproducts=0;


        for(Product obj: products){
            sumproducts = sumproducts + obj.getPrice();
        }


        model.addAttribute("products", products);
        model.addAttribute("sumProducts", sumproducts);
        return "cartlogged";
    }

    @PostMapping("/productscataloguenameremove")
    public String removeProductAnon(@RequestParam("name") String name1, Model model) {

        List<Product> products = Variables.getProductscartanon();


        for(Product obj: products){

            if(obj.getName().equals(name1)){

                products.remove(obj);

                break;
            }

        }

        int sumproducts=0;

        for(Product obj: products){
            sumproducts = sumproducts + obj.getPrice();
        }


        model.addAttribute("products", products);
        model.addAttribute("sumProducts", sumproducts);
        return "cart";
    }

}
