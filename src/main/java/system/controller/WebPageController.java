package system.controller;

import system.model.User;
import system.model.Variables;
import system.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebPageController extends UserController{

    @GetMapping("/logout")
    public String logout(Model model) {

        List<Product> products = getProductRepository().findAll();


        model.addAttribute("products", products);
        Variables.getProductscart().clear();
        Variables.setOnline(0);
        return "productscatalogue";
    }

    @GetMapping("/")
    public String home(Model model) {

        List<Product> products = getProductRepository().findAll();

        model.addAttribute("products", products);
        return "index"; //newhomepage.html
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerbutton")
    public String registerButton(@ModelAttribute User user, @RequestParam String username, @RequestParam String password, @RequestParam String mail, @RequestParam int age, @RequestParam String sex, @RequestParam String city) {

        User user1 = getUserRepository().findByUsername(username);

        if (user1 != null) {
            return "registerwrong";
        } else {
            User usernew = new User();
            usernew.setUsername(username);
            usernew.setPassword(password);
            usernew.setMail(mail);
            System.out.println(age + " amžius ");
            usernew.setBorn(age);
            usernew.setSex(sex);
            usernew.setCity(city);
            usernew.setFirsttimeloginstatus(0);
            usernew.setFriends("_");
            usernew.setInvitatiots("_");
            usernew.setBoughtproducts("_");
            getUserRepository().save(usernew);
            return "login";
        }

    }

    @GetMapping("/registerwrong")
    public String registerWrong(Model model) {

        model.addAttribute("user", new User());
        return "registerwrong";
    }

    @GetMapping("/projectecommerce")
    public String eCommercepPage() {

        return "projectecommerce";
    }

    @GetMapping("/projectimprovementapp")
    public String projectImprovementAppPage() {

        return "projectimprovementapp"; 
    } 

    @GetMapping("/projectaiapi")
    public String projectAIApiPage() {

        return "projectaiapi"; 
    }


}