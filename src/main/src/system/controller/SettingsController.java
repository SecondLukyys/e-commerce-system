package system.controller;

import system.repository.UserRepository;
import system.model.User;
import system.model.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController extends UserController{

    @GetMapping("/settingslogged")
    public String register(Model model) {

        User user = getUserRepository().findByUsername(Variables.getUsername());

        String sex = user.getSex();
        int age = user.getBorn();
        String city = user.getCity();

        model.addAttribute("age", age);
        model.addAttribute("sex", sex);
        model.addAttribute("city", city);
        model.addAttribute("user", new User());
        return "settingslogged";
    }

    @PostMapping("/changeusername")
    public String usernameChange(@ModelAttribute User user, @RequestParam String username) {

        String oldUsername = Variables.getUsername();
        User user1 = getUserRepository().findByUsername(oldUsername);
        User user2 = getUserRepository().findByUsername(username);


        if(user2 != null)
            return "settingsloggedusernamenotchanged";


        user1.setUsername(username);
        getUserRepository().save(user1);
        Variables.setUsername(username);

        return "settingsloggedusernamechanged";
    }

    @PostMapping("/changepassword")
    public String passwordChange(@ModelAttribute User user, @RequestParam String password) {

        String username = Variables.getUsername();
        User user1 = getUserRepository().findByUsername(username);//neranda mesti klaida
        user1.setPassword(password);
        getUserRepository().save(user1);

        return "settingsloggedpasswordchanged";
    }

    @PostMapping("/changecity")
    public String cityChange(@ModelAttribute User user, @RequestParam String city) {

        String username = Variables.getUsername();
        User user1 = getUserRepository().findByUsername(username);
        user1.setCity(city);
        getUserRepository().save(user1);
        return "settingsloggedcitychanged";
    }

    @PostMapping("/changeage")
    public String ageChange(@ModelAttribute User user, @RequestParam Integer age) {

        String username = Variables.getUsername();
        User user1 = getUserRepository().findByUsername(username);
        user1.setBorn(age);
        getUserRepository().save(user1);
        return "settingsloggedagechanged";
    }

    @PostMapping("/changesex")
    public String sexChange(@ModelAttribute User user, @RequestParam String sex) {

        String username = Variables.getUsername();
        User user1 = getUserRepository().findByUsername(username);
        user1.setSex(sex);
        getUserRepository().save(user1);
        return "settingsloggedsexchanged";
    }

}
