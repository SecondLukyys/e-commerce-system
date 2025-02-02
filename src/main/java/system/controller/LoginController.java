package system.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import system.model.Product;
import system.model.User;
import system.model.Variables;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController extends UserController{

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginbutton")
    public String loginButton(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

        if(username == null)
            return "loginwrong";

        if(password == null)
            return "loginwrong";

        if(username.equals(""))
            return "loginwrong";

        if(password.equals(""))
            return "loginwrong";


        User user = getUserRepository().findByUsernameAndPassword(username, password);


        if(user==null)
            return "loginwrong";


        if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
            Variables.setUsername(username);
            Variables.setPassword(password);
            Variables.setUserstatic(user);

            if (user.getFirsttimeloginstatus() == 0) {

                List<Product> productsA = getProductRepository().findAll();
                List<Product> products = new ArrayList<Product>();
                int n1 = 0;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;


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
                Variables.setOnline(1);
                Variables.getProductscart().clear();
                user.setFirsttimeloginstatus(1);
                getUserRepository().save(user);
                return "productsnewuserrecommendations"; //isskaidyti // Iš visų kategorijų gražinamos dvi prekės naują vartotoją supažindinti su produktų katalogu
            } else {

                List<Product> products = getProductRepository().findAll();
                model.addAttribute("products", products);
                Variables.setOnline(1);
                Variables.getProductscart().clear();
                return "productscataloguelogged";
            }
        }
        return "loginwrong";
    }

}