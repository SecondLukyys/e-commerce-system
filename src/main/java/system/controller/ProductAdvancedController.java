package system.controller;

import org.springframework.web.bind.annotation.RequestParam;
import system.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import system.model.Product;
import system.model.Variables;

import java.util.*;

@Controller
public class ProductAdvancedController extends ProductController {

    @GetMapping("/audioproductscatalogue")
    public String productsPageAudio(Model model) {

        List<Product> productsA = getProductRepository().findAll();
        List<Product> products = new ArrayList<Product>();


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Audio technika")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);


        if(Variables.getOnline() == 0)
            return "audioproductscatalogue";
        else
            return "audioproductscataloguelogged";
    }

    @GetMapping("/clothesproductscatalogue")
    public String productsPageClothes(Model model) {

        List<Product> productsA = getProductRepository().findAll();
        List<Product> products = new ArrayList<Product>();


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Drabužis")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);


        if(Variables.getOnline() == 0)
            return "clothesproductscatalogue";
        else
            return "clothesproductscataloguelogged";
    }

    @GetMapping("/laptopproductscatalogue")
    public String productsPageLaptop(Model model) {

        List<Product> productsA = getProductRepository().findAll();
        List<Product> products = new ArrayList<Product>();


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Kompiuteris")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);


        if(Variables.getOnline() == 0)
            return "laptopproductscatalogue";
        else
            return "laptopproductscataloguelogged";
    }

    @GetMapping("/bookproductscatalogue")
    public String productsPageBook(Model model) {

        List<Product> productsA = getProductRepository().findAll();
        List<Product> products = new ArrayList<Product>();


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Knyga")) {
                products.add(obj);
            }
        }



        model.addAttribute("products", products);


        if(Variables.getOnline() == 0)
            return "bookproductscatalogue";
        else
            return "bookproductscataloguelogged";
    }



    @PostMapping("/productscataloguenameanonaudiocat")
    public String handleFormSubmitProductAudioCat(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();


        addToCartAnon(productsA, name1);


        List<Product> products = new ArrayList<>();

        for (Product obj : productsA) {
            if (obj.getCategory().equals("Audio technika")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);
        return "audioproductscatalogue";
    }

    @PostMapping("/productscataloguenameanonbookcat")
    public String handleFormSubmitProductBookCat(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();

        addToCartAnon(productsA, name1);


        List<Product> products = new ArrayList<>();

        for (Product obj : productsA) {
            if (obj.getCategory().equals("Knyga")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);

        return "bookproductscatalogue";
    }

    @PostMapping("/productscataloguenameanonclothescat")
    public String handleFormSubmitProductClothesCat(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();
        addToCartAnon(productsA, name1);
        List<Product> products = new ArrayList<>();


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Drabužis")) {
                products.add(obj);
            }
        }



        model.addAttribute("products", products);

        return "clothesproductscatalogue";
    }

    @PostMapping("/productscataloguenameanonlaptopcat")
    public String handleFormSubmitProductLaptopCat(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();
        addToCartAnon(productsA, name1);
        List<Product> products = new ArrayList<>();


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Kompiuteris")) {
                products.add(obj);
            }
        }

        model.addAttribute("products", products);


        return "laptopproductscatalogue";
    }

    @PostMapping("/productscataloguenameaudiocat")
    public String handleFormSubmitProductAudioCatLogged(@ModelAttribute("name") String name1, Model model) {


        List<Product> productsA = getProductRepository().findAll();

        addToCart(productsA, name1);


        List<Product> products = new ArrayList<>();

        for (Product obj : productsA) {
            if (obj.getCategory().equals("Audio technika")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);

        return "audioproductscataloguelogged";
    }

    @PostMapping("/productscataloguenamebookcat")
    public String handleFormSubmitProductBookCatLogged(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();

        addToCart(productsA, name1);

        List<Product> products = new ArrayList<>();

        for (Product obj : productsA) {
            if (obj.getCategory().equals("Knyga")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);

        return "bookproductscataloguelogged";
    }

    @PostMapping("/productscataloguenameclothescat")
    public String handleFormSubmitProductClothesCatLogged(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();

        addToCart(productsA, name1);

        List<Product> products = new ArrayList<>();

        for (Product obj : productsA) {
            if (obj.getCategory().equals("Drabužis")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);

        return "clothesproductscataloguelogged";
    }

    @PostMapping("/productscataloguenamelaptopcat")
    public String handleFormSubmitProductLaptopCatLogged(@ModelAttribute("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();

        addToCart(productsA, name1);

        List<Product> products = new ArrayList<>();

        for (Product obj : productsA) {
            if (obj.getCategory().equals("Kompiuteris")) {
                products.add(obj);
            }
        }


        model.addAttribute("products", products);

        return "laptopproductscataloguelogged";
    }

    @GetMapping("/productscatalogue")
    public String productsCatalogue(Model model) {

        List<Product> products = getProductRepository().findAll();

        model.addAttribute("products", products);
        return "productscatalogue";
    }

    @GetMapping("/productscataloguelogged")
    public String productsCatalogueRegisteredUser(Model model) {

        List<Product> products = getProductRepository().findAll();

        model.addAttribute("products", products);
        return "productscataloguelogged";
    }


    @GetMapping("/boughtproductslogged")
    public String boughtProductsPage(Model model) {

        String username = Variables.getUsername();
        User user = getUserRepository().findByUsername(username);
        List<String> productNames = Arrays.asList(user.getBoughtproducts().split("_"));
        Set<Product> products = new HashSet<>();


        for (String productName : productNames) {
            List<Product> productList = getProductRepository().findByName(productName);
            for (Product product : productList) {
                if (!products.contains(product)) {
                    products.add(product);
                }
            }
        }


        model.addAttribute("products", products);
        return "boughtproductscataloguepagelogged";
    }


    @PostMapping("/productscatalogueloggednameratebought")
    public String rateProduct(@RequestParam("name") String name1,
                              @RequestParam("category") String category1,
                              @RequestParam("rating") int rating1, Model model) {

        List<Product> productlist = getProductRepository().findAll();
        Optional<Product> optionalProduct = productlist.stream()
                .filter(product -> product.getName().equals(name1))
                .findFirst();


        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            int newratingcount = 0;
            newratingcount = product.getRatingcount() + 2;
            product.setRatingcount(newratingcount);
            int newratingsum = 0;
            newratingsum = product.getRatingsum() + rating1 + 5;
            product.setRatingsum(newratingsum);
            int newrating = (int) (newratingsum / newratingcount * 1.34);
            product.setRating(newrating);
            System.out.println(newratingcount + "  " + newratingsum + "  " + newrating);
            getProductRepository().save(product);

        } else {
            System.out.println("Neranda produkto iš db");
        }

        String username = Variables.getUsername();

        User user = getUserRepository().findByUsername(username);
        List<String> productNames = Arrays.asList(user.getBoughtproducts().split("_"));
        Set<Product> products = new HashSet<>();


        for (String productName : productNames) {
            List<Product> productList = getProductRepository().findByName(productName);
            for (Product product : productList) {
                if (!products.contains(product)) {
                    products.add(product);
                }
            }
        }
        model.addAttribute("products", products);
        return "boughtproductscataloguepagelogged";
    }

}
