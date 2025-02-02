package system.controller;

import system.model.Product;
import system.model.User;
import system.model.Variables;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ProductRecommendationsController extends ProductController {


    @GetMapping("/productrecomendationssimple")
    public String productRecommendationsSimple(Model model) {

        List<Product> productsA = getProductRepository().findAll();

        productsA.sort((p1, p2) -> p2.getSellcount() - p1.getSellcount());

        List<Product> products = new ArrayList<>();


        for (int i = 0; i < 10 && i < productsA.size(); i++) {
            products.add(productsA.get(i));
        }


        String username = Variables.getUsername();
        User user = getUserRepository().findByUsername(username);
        String[] productNames = user.getBoughtproducts().split("_");
        Set<Product> productsbought = new HashSet<>();


        for (String productName : productNames) {
            List<Product> productList = getProductRepository().findByName(productName);
            productsbought.addAll(productList);
        }


        List<Product> productsB = getProductRepository().findAll();
        List<Product> productsrecommendedA = new ArrayList<>();
        List<Product> productsrecommendedB = new ArrayList<>();
        List<Product> productsrecommendedC = new ArrayList<>();
        List<Product> productsrecommendedD = new ArrayList<>();
        String typebook = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Knyga")) {
                typebook = obj.getType();
                System.out.println("Knygos tipas: " + typebook);
                break;
            }
        }


        String typeclothe = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Drabužis")) {
                typeclothe = obj.getType();
                System.out.println("Drabužio tipas: " + typeclothe);
                break;
            }
        }


        String typecomputer = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Kompiuteris")) {
                typecomputer = obj.getType();
                System.out.println("Kompiuterio tipas:" + typecomputer);
                break;
            }
        }


        String typeaudiotech = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Audio technika")) {
                typeaudiotech = obj.getType();
                System.out.println("Audio technikos tipas: " + typeaudiotech);
                break;
            }
        }


        List<Product> productBoughtList = new ArrayList<>(productsbought);

        int i = 0;

        String userfriendsstring = user.getFriends();

        String[] friendsArray = userfriendsstring.split("_");

        List<String> friendsArrayFixed = Arrays.stream(friendsArray)
                .filter(s -> !s.isEmpty()).toList();

        List<User> users = friendsArrayFixed.stream()
                .map(getUserRepository()::findByUsername)
                .filter(Objects::nonNull).toList();

        Map<String, Integer> productCounts = new HashMap<>();
        for (User user2 : users) {
            String[] boughtProducts = user2.getBoughtproducts().split("_");

            for (String productName : boughtProducts) {
                if (productName.isEmpty()) {
                    continue;
                }
                int count = productCounts.getOrDefault(productName, 0);
                productCounts.put(productName, count + 1);
            }
        }

        List<Product> productsFriend = getProductRepository().findAllByNameIn(
                productCounts.entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(10)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList())
        );


        //////////////Knygos//////////////


        for (Product obj : productsB) {
            if (obj.getType().equals(typebook) && obj.getCategory().equals("Knyga") && !productBoughtList.contains(obj)) {
                productsrecommendedA.add(obj);
                i++;
                if (i == 3) {
                    break;
                }
            }
        }


        String friendtype = "";
        int z = 0;
        int y = 0;
        int n = 0;

        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Knyga") && !obj.getType().equals(typebook)) {
                    friendtype = obj.getType();
                    z = 1;
                }
                if (z == 1)
                    break;
            }
        }



        if(z == 1){
            for(Product obj: productsA){

                if((obj.getCategory().equals("Knyga")) && (obj.getType().equals(friendtype))){
                    productsrecommendedA.add(obj);
                    n++;
                }
                if(n>=3)
                    break;
            }
        }

        z=0;
        n=0;
        i=0;

        ///////////////////Drabužiai//////////////////////////


        for (Product obj : productsB) {
            if (obj.getType().equals(typeclothe) && obj.getCategory().equals("Drabužis") && !productBoughtList.contains(obj)) {
                // Check if the product is the same type and category, and not bought before

                productsrecommendedB.add(obj);
                i++;
                if (i == 3) {
                    break; // limit to only 3 products
                }
            }
        }

        friendtype = "";

        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Drabužis") && !obj.getType().equals(typeclothe)) {
                    friendtype = obj.getType();
                    z = 1;
                }
                if (z == 1)
                    break;
            }
        }



        if(z == 1){
            for(Product obj: productsA){

                if((obj.getCategory().equals("Drabužis")) && (obj.getType().equals(friendtype))){
                    productsrecommendedB.add(obj);
                    n++;
                }
                if(n>=3)
                    break;
            }
        }

        z=0;
        n=0;
        i=0;

        //////////////Kompiuteris//////////////


        for (Product obj : productsB) {
            if (obj.getType().equals(typecomputer) && obj.getCategory().equals("Kompiuteris") && !productBoughtList.contains(obj)) {

                productsrecommendedC.add(obj);
                i++;
                if (i == 3) {
                    break;
                }
            }
        }


        friendtype = "";

        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Kompiuteris") && !obj.getType().equals(typecomputer)){
                    friendtype = obj.getType();
                    z = 1;
                }
                if (z == 1)
                    break;
            }
        }


        if(z == 1){
            for(Product obj: productsA){

                if((obj.getCategory().equals("Kompiuteris")) && (obj.getType().equals(friendtype))){
                    productsrecommendedC.add(obj);
                    n++;
                }
                if(n>=3)
                    break;
            }
        }


        z=0;
        n=0;
        i=0;
        friendtype = "";


        for (Product obj : productsB) {
            if (obj.getType().equals(typeaudiotech) && obj.getCategory().equals("Audio technika") && !productBoughtList.contains(obj)) {

                productsrecommendedD.add(obj);
                i++;
                if (i == 3) {
                    break;
                }
            }
        }


        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Audio technika") && !obj.getType().equals(typeaudiotech)) {
                    friendtype = obj.getType();
                    z = 1;
                }
                if (z == 1)
                    break;
            }
        }


        if(z == 1){
            for(Product obj: productsA){
                if((obj.getCategory().equals("Audio technika")) && (obj.getType().equals(friendtype))){
                    productsrecommendedD.add(obj);
                    n++;
                }
                if(n>=3)
                    break;
            }
        }


        model.addAttribute("productsrecommendedA", productsrecommendedA);
        model.addAttribute("productsrecommendedB", productsrecommendedB);
        model.addAttribute("productsrecommendedC", productsrecommendedC);
        model.addAttribute("productsrecommendedD", productsrecommendedD);
        model.addAttribute("products", products);
        return "productsrecommendationslogged";
    }

    @PostMapping("/productscatalogueloggednamerecomendations")
    public String productRecommendationsSimple(@RequestParam("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();

        addToCart(productsA, name1);

        productsA.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getSellcount() - p1.getSellcount();
            }
        });

        List<Product> products = new ArrayList<>();


        for (int i = 0; i < 10 && i < productsA.size(); i++) {
            products.add(productsA.get(i));
        }



        String username = Variables.getUsername();
        User user = getUserRepository().findByUsername(username);
        String[] productNames = user.getBoughtproducts().split("_");
        Set<Product> productsbought = new HashSet<>();


        for (String productName : productNames) {
            List<Product> productList = getProductRepository().findByName(productName);
            productsbought.addAll(productList);
        }


        List<Product> productsB = getProductRepository().findAll();
        List<Product> productsrecommendedA = new ArrayList<>();  //Knyga
        List<Product> productsrecommendedB = new ArrayList<>();  //Darbužiai
        List<Product> productsrecommendedC = new ArrayList<>();  //Kompiuteris
        List<Product> productsrecommendedD = new ArrayList<>();  //Audio technika

        String typebook = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Knyga")) {
                typebook = obj.getType();
                System.out.println("Knygos tipas: " + typebook);
                break;
            }
        }


        String typeclothe = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Drabužis")) {
                typeclothe = obj.getType();
                System.out.println("Drabužio tipas: " + typeclothe);
                break;
            }
        }


        String typecomputer = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Kompiuteris")) {
                typecomputer = obj.getType();
                System.out.println("Kompiuterio tipas:" + typecomputer);
                break;
            }
        }


        String typeaudiotech = "Test";


        for (Product obj : productsbought) {
            if (obj.getCategory().equals("Audio technika")) {
                typeaudiotech = obj.getType();
                System.out.println("Audio technikos tipas: " + typeaudiotech);
                break;
            }
        }


        List<Product> productBoughtList = new ArrayList<>(productsbought);


        int i = 0;

        //////////////Knygos//////////////

        for (Product obj : productsB) {
            if (obj.getType().equals(typebook) && obj.getCategory().equals("Knyga") && !productBoughtList.contains(obj)) {
                productsrecommendedA.add(obj);
                i++;
                if (i == 3) {
                    break; // limit to only 4 products
                }
            }
        }


        String userfriendsstring = user.getFriends();

        String[] friendsArray = userfriendsstring.split("_");

        List<String> friendsArrayFixed = Arrays.stream(friendsArray)
                .filter(s -> !s.isEmpty()).toList();

        List<User> users = friendsArrayFixed.stream()
                .map(getUserRepository()::findByUsername)
                .filter(Objects::nonNull).toList();

        Map<String, Integer> productCounts = new HashMap<>();
        for (User user2 : users) {
            String[] boughtProducts = user2.getBoughtproducts().split("_");

            for (String productName : boughtProducts) {
                if (productName.isEmpty()) {
                    continue;
                }
                int count = productCounts.getOrDefault(productName, 0);
                productCounts.put(productName, count + 1);
            }
        }

        List<Product> productsFriend = getProductRepository().findAllByNameIn(
                productCounts.entrySet()
                        .stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(10)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList())
        );

        // Product book from a friend
        String friendtype;
        int y = 0;
        int n = 0;
        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Knyga") && !obj.getType().equals(typebook)) {
                    friendtype = obj.getType();

                    for(Product obj1: productsA){
                        if(obj1.getCategory().equals("Knyga") && obj1.getType().equals(friendtype)){
                            productsrecommendedA.add(obj);
                            n++;
                        }
                        if(n<=2)
                            break;
                    }

                    y++;
                }
                if (y != 1)
                    break;
            }
        }
        y = 0;
        n = 0;



        i = 0;
        for (Product obj : productsB) {
            if (obj.getType().equals(typeclothe) && obj.getCategory().equals("Knyga") && !productBoughtList.contains(obj)) {
                // Check if the product is the same type and category, and not bought before

                productsrecommendedA.add(obj);
                i++;
                if (i == 3) {
                    break; // limit to only 3 products
                }
            }
        }


        //////////////Drabužiai//////////////


        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Drabužis") && !obj.getType().equals(typebook)) {
                    friendtype = obj.getType();

                    for(Product obj1: productsA){
                        if(obj1.getCategory().equals("Drabužis") && obj1.getType().equals(friendtype)){
                            productsrecommendedB.add(obj);
                            n++;
                        }
                        if(n<=2)
                            break;
                    }

                    y++;
                }
                if (y != 1)
                    break;
            }
        }
        y = 0;
        n = 0;


        i = 0;
        for (Product obj : productsB) {
            if (obj.getType().equals(typeclothe) && obj.getCategory().equals("Drabužis") && !productBoughtList.contains(obj)) {
                // Check if the product is the same type and category, and not bought before

                productsrecommendedB.add(obj);
                i++;
                if (i == 3) {
                    break; // limit to only 3 products
                }
            }
        }


        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Kompiuteris") && !obj.getType().equals(typebook)) {
                    friendtype = obj.getType();

                    for(Product obj1: productsA){
                        if(obj1.getCategory().equals("Kompiuteris") && obj1.getType().equals(friendtype)){
                            productsrecommendedC.add(obj);
                            n++;
                        }
                        if(n<=2)
                            break;
                    }

                    y++;
                }
                if (y != 1)
                    break;
            }
        }
        y = 0;
        n = 0;

        i = 0;
        for (Product obj : productsB) {
            if (obj.getType().equals(typecomputer) && obj.getCategory().equals("Kompiuteris") && !productBoughtList.contains(obj)) {
                // Check if the product is the same type and category, and not bought before

                productsrecommendedC.add(obj);
                i++;
                if (i == 3) {
                    break; // limit to only 3 products
                }
            }
        }

        if (!typebook.equals("Test")) {
            for (Product obj : productsFriend) {
                if (obj.getCategory().equals("Audio technika") && !obj.getType().equals(typebook)) {
                    friendtype = obj.getType();

                    for(Product obj1: productsA){
                        if(obj1.getCategory().equals("Audio technika") && obj1.getType().equals(friendtype)){
                            productsrecommendedD.add(obj);
                            n++;
                        }
                        if(n<=2)
                            break;
                    }

                    y++;
                }
                if (y != 1)
                    break;
            }
        }
        y = 0;
        n = 0;


        i = 0;
        for (Product obj : productsB) {
            if (obj.getType().equals(typeaudiotech) && obj.getCategory().equals("Audio technika") && !productBoughtList.contains(obj)) {
                // Check if the product is the same type and category, and not bought before

                productsrecommendedD.add(obj);
                i++;
                if (i == 3) {
                    break; // limit to only 3 products
                }
            }
        }


        model.addAttribute("productsrecommendedA", productsrecommendedA);
        model.addAttribute("productsrecommendedB", productsrecommendedB);
        model.addAttribute("productsrecommendedC", productsrecommendedC);
        model.addAttribute("productsrecommendedD", productsrecommendedD);
        model.addAttribute("products", products);

        return "productsrecommendationslogged";
    }

    @PostMapping("/productscatalogueloggednameprofile")
    public String productRecommendationsFriends(@RequestParam("name") String name1, Model model) {

        User user = getUserRepository().findByUsername(Variables.getUsername());

        List<Product> productsall = getProductRepository().findAll();
        List<Product> products = new ArrayList<>();

        addToCart(productsall, name1);


        int age = 2024;
        String sex = user.getSex();
        String city = user.getCity();
        age = user.getBorn();
        String agestring = String.valueOf(age);

        System.out.println(2023-age);

        if(sex.equals("male"))
            sex = "vyras";
        else
            sex = "moteris";


        String data = city + " " + sex + " " + agestring + " metų.";


        if (city.contains("apskritis"))
            city = "Apskritis";
        else
            city = "Miestas";

        if(2023-age <= 15){
            for(Product obj: productsall){
                if((obj.getCategory().equals("Knyga")  && obj.getType().equals("Type 2")) || (obj.getCategory().equals("Knyga")  && obj.getType().equals("Type 4")))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 2"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("vyras")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 1"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("moteris")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 2"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("vyras")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 3"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("moteris")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 4"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("vyras")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 4"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("moteris")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 1"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }

        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("moteris")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 2"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("vyras")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 4"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }
        return "productsrecommendationsprofilelogged";
    }


    @GetMapping("/productrecomendationsprofile")
    public String productRecommendationsProfile(Model model) {

        User user = getUserRepository().findByUsername(Variables.getUsername());

        List<Product> productsall = getProductRepository().findAll();
        List<Product> products = new ArrayList<>();

        int age = 250;
        String sex = user.getSex();
        String city = user.getCity();
        age = user.getBorn();
        String agestring = String.valueOf(age);


        if(sex.equals("male"))
            sex = "vyras";
        else
            sex = "moteris";


        String data = city + " " + sex + " " + agestring + " metų.";


        if (city.contains("apskritis"))
            city = "Apskritis";
        else
            city = "Miestas";

        System.out.println(2023-age);

        if(2023-age <= 15){
            for(Product obj: productsall){
                if((obj.getCategory().equals("Knyga")  && obj.getType().equals("Type 2")) || (obj.getCategory().equals("Knyga")  && obj.getType().equals("Type 4")))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 2"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("vyras")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 1"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("moteris")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 2"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("vyras")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 3"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 15 && 2023-age <= 30) && (sex.equals("moteris")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 4"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("vyras")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 4"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("moteris")) && (city.equals("Miestas"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 1"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }

        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("moteris")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 1"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 2"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }


        if((2023-age > 30 && 2023-age <= 150) && (sex.equals("vyras")) && (city.equals("Apskritis"))){
            for(Product obj: productsall){
                if(obj.getCategory().equals("Drabužis") && obj.getType().equals("Type 4"))
                    products.add(obj);

                if(obj.getCategory().equals("Knyga") && obj.getType().equals("Type 3"))
                    products.add(obj);

                if(obj.getCategory().equals("Kompiuteris") && obj.getType().equals("Type 2"))
                    products.add(obj);

                if(obj.getCategory().equals("Audio technika") && obj.getType().equals("Type 4"))
                    products.add(obj);
            }
            model.addAttribute("data", data);
            model.addAttribute("products", products);
            return "productsrecommendationsprofilelogged";
        }
        return "productsrecommendationsprofilelogged";
    }

    @PostMapping("/productscatalogueloggednamenew")
    public String addToCartNewRegisteredUser(@RequestParam("name") String name1, Model model) {

        List<Product> productsA = getProductRepository().findAll();
        List<Product> products = new ArrayList<Product>();
        int n1 = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;


        addToCart(productsA, name1);


        for (Product obj : productsA) {
            if (obj.getCategory().equals("Audio technika") && n1 <= 1) {
                products.add(obj);
                n1++;
            }
            if (obj.getCategory().equals("Drabužis") && n2 <= 1) {
                n2++;
                products.add(obj);
            }
            if (obj.getCategory().equals("Knyga") && n3 <= 1) {
                n3++;
                products.add(obj);
            }
            if (obj.getCategory().equals("Kompiuteris") && n4 <= 1) {
                n4++;
                products.add(obj);
            }
            if (n1 >= 2 && n2 >= 2 && n3 >= 2 && n4 >= 2)
                break;
        }

        model.addAttribute("products", products);
        return "productsnewuserrecommendations";
    }

}