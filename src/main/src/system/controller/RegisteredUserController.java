package system.controller;

import system.repository.ProductRepository;
import system.repository.UserRepository;
import system.model.User;
import system.model.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class RegisteredUserController extends UserController{

    @GetMapping("/friends")
    public String listUsersFriend(Model model) {

        List<User> allusers = getUserRepository().findAll();
        User user = getUserRepository().findByUsername(Variables.getUsername());

        /////////////////////////////////// User Friend List /////////////////////////////////////////////

        List<User> userfriends1 = new ArrayList<User>();
        String userfriendlist = user.getFriends();
        String[] myArray = new String[0];


        if (userfriendlist != null) {
            myArray = userfriendlist.split("_", -1);
        }


        myArray = Arrays.stream(myArray)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        int i = 0;


        while (i < myArray.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray[i])) {
                    userfriends1.add(obj);
                }
            }
            i++;
        }


        List<String> username = userfriends1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userfriends1", userfriends1);
        username.clear();

        /////////////////////////////////// User Friend List /////////////////////////////////////////////
        /////////////////////////////////// User Invitations /////////////////////////////////////////////

        List<User> userinvited1 = new ArrayList<User>();
        String userinvitationlist = user.getInvitatiots(); // paima dabartini vartotoja
        String[] myArray1 = new String[0];


        if (userinvitationlist != null) {
            myArray1 = userinvitationlist.split("_", -1);
        }

        myArray1 = Arrays.stream(myArray1)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        i = 0;


        while (i < myArray1.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray1[i])) {
                    userinvited1.add(obj);
                }
            }
            i++;
        }

        username = userinvited1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userinvited1", userinvited1);
        username.clear();

        /////////////////////////////////// User Invitations /////////////////////////////////////////////
        /////////////////////////////////// All Users List ///////////////////////////////////////////////

        List<User> users1 = getUserRepository().findAll();
        String currentuser = Variables.getUsername();

        List<String> usernames = users1.stream()
                .map(User::getUsername).toList();

        int index = usernames.indexOf(currentuser);


        if(index != -1){
            users1.remove(index);
        }


        model.addAttribute("users1", users1);

        /////////////////////////////////// All Users List ///////////////////////////////////////////////
        return "/friendslogged";
    }

    @PostMapping("/friendsaccept")
    public String listUserFriendAdd (@RequestParam("username") String username1, Model model){


        String currentusername = Variables.getUsername();
        User user = getUserRepository().findByUsername(currentusername);
        User user1 = getUserRepository().findByUsername(username1);
        currentusername = "_" + currentusername;
        username1 = "_" + username1;
        String newfriendslist = user.getFriends();
        String newfriendslist1 = user1.getFriends();
        newfriendslist = newfriendslist + username1;
        newfriendslist1 = newfriendslist1 + currentusername;
        user.setFriends(newfriendslist);
        user1.setFriends(newfriendslist1);
        String invitationlist = user.getInvitatiots();
        invitationlist = invitationlist.replaceFirst(username1, "");
        System.out.println(invitationlist);
        user.setInvitatiots(invitationlist);


        user.getUsers().add(user1);
        user1.getUsers().add(user);

        getUserRepository().save(user1);
        getUserRepository().save(user);
        /////////////////////////////////// User Friend List /////////////////////////////////////////////

        List<User> allusers = getUserRepository().findAll();
        List<User> userfriends1 = new ArrayList<User>();
        String userfriendlist = user.getFriends();
        String[] myArray = new String[0];


        if (userfriendlist != null) {
            myArray = userfriendlist.split("_", -1);
        }


        myArray = Arrays.stream(myArray)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        int i = 0;


        while (i < myArray.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray[i])) {
                    userfriends1.add(obj);
                }
            }
            i++;
        }


        List<String> username = userfriends1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userfriends1", userfriends1);
        username.clear();

        /////////////////////////////////// User Friend List /////////////////////////////////////////////
        /////////////////////////////////// User Invitations /////////////////////////////////////////////

        List<User> userinvited1 = new ArrayList<User>();
        String userinvitationlist = user.getInvitatiots(); // paima dabartini vartotoja
        String[] myArray1 = new String[0];


        if (userinvitationlist != null) {
            myArray1 = userinvitationlist.split("_", -1);
        }

        myArray1 = Arrays.stream(myArray1)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        i = 0;


        while (i < myArray1.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray1[i])) {
                    userinvited1.add(obj);
                }
            }
            i++;
        }

        username = userinvited1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userinvited1", userinvited1);
        username.clear();

        /////////////////////////////////// User Invitations /////////////////////////////////////////////
        /////////////////////////////////// All Users List ///////////////////////////////////////////////

        List<User> users1 = getUserRepository().findAll();
        String currentuser = Variables.getUsername();

        List<String> usernames = users1.stream()
                .map(User::getUsername).toList();

        int index = usernames.indexOf(currentuser);


        if(index != -1){
            users1.remove(index);
        }


        model.addAttribute("users1", users1);

        /////////////////////////////////// All Users List ///////////////////////////////////////////////
        return "/friendslogged";
    }

    @PostMapping("/friendsdecline")
    public String listUserFriendRemove (@RequestParam("username") String username1,  Model model){

        String currentusername = Variables.getUsername();
        User user = getUserRepository().findByUsername(currentusername);
        username1 = "_" + username1;
        String invitationlist = user.getInvitatiots();
        invitationlist = invitationlist.replaceFirst(username1, "");
        System.out.println(invitationlist);
        user.setInvitatiots(invitationlist);
        getUserRepository().save(user);

        /////////////////////////////////// User Friend List /////////////////////////////////////////////

        List<User> allusers = getUserRepository().findAll();
        List<User> userfriends1 = new ArrayList<User>();
        String userfriendlist = user.getFriends();
        String[] myArray = new String[0];


        if (userfriendlist != null) {
            myArray = userfriendlist.split("_", -1);
        }


        myArray = Arrays.stream(myArray)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        int i = 0;


        while (i < myArray.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray[i])) {
                    userfriends1.add(obj);
                }
            }
            i++;
        }


        List<String> username = userfriends1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userfriends1", userfriends1);
        username.clear();

        /////////////////////////////////// User Friend List /////////////////////////////////////////////
        /////////////////////////////////// User Invitations /////////////////////////////////////////////

        List<User> userinvited1 = new ArrayList<User>();
        String userinvitationlist = user.getInvitatiots(); // paima dabartini vartotoja
        String[] myArray1 = new String[0];


        if (userinvitationlist != null) {
            myArray1 = userinvitationlist.split("_", -1);
        }

        myArray1 = Arrays.stream(myArray1)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        i = 0;


        while (i < myArray1.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray1[i])) {
                    userinvited1.add(obj);
                }
            }
            i++;
        }

        username = userinvited1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userinvited1", userinvited1);
        username.clear();

        /////////////////////////////////// User Invitations /////////////////////////////////////////////
        /////////////////////////////////// All Users List ///////////////////////////////////////////////

        List<User> users1 = getUserRepository().findAll();
        String currentuser = Variables.getUsername();

        List<String> usernames = users1.stream()
                .map(User::getUsername).toList();

        int index = usernames.indexOf(currentuser);


        if(index != -1){
            users1.remove(index);
        }


        model.addAttribute("users1", users1);

        /////////////////////////////////// All Users List ///////////////////////////////////////////////
        return "/friendslogged";
    }

    @PostMapping("/friendssendinvitation")
    public String listUserSendInvitation (@RequestParam("username") String username1,  Model model){

        String currentusername = Variables.getUsername();
        User user = getUserRepository().findByUsername(currentusername);
        User userfriend = getUserRepository().findByUsername(username1);
        String inviter = Variables.getUsername();
        inviter = inviter + "_";
        String newinvitationslist = userfriend.getInvitatiots();
        newinvitationslist = newinvitationslist + inviter;
        userfriend.setInvitatiots(newinvitationslist);
        getUserRepository().save(userfriend);

        /////////////////////////////////// User Friend List /////////////////////////////////////////////

        List<User> allusers = getUserRepository().findAll();
        List<User> userfriends1 = new ArrayList<User>();
        String userfriendlist = user.getFriends();
        String[] myArray = new String[0];


        if (userfriendlist != null) {
            myArray = userfriendlist.split("_", -1);
        }


        myArray = Arrays.stream(myArray)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        int i = 0;


        while (i < myArray.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray[i])) {
                    userfriends1.add(obj);
                }
            }
            i++;
        }


        List<String> username = userfriends1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userfriends1", userfriends1);
        username.clear();

        /////////////////////////////////// User Friend List /////////////////////////////////////////////
        /////////////////////////////////// User Invitations /////////////////////////////////////////////

        List<User> userinvited1 = new ArrayList<User>();
        String userinvitationlist = user.getInvitatiots(); // paima dabartini vartotoja
        String[] myArray1 = new String[0];


        if (userinvitationlist != null) {
            myArray1 = userinvitationlist.split("_", -1);
        }

        myArray1 = Arrays.stream(myArray1)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        i = 0;


        while (i < myArray1.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray1[i])) {
                    userinvited1.add(obj);
                }
            }
            i++;
        }

        username = userinvited1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userinvited1", userinvited1);
        username.clear();

        /////////////////////////////////// User Invitations /////////////////////////////////////////////
        /////////////////////////////////// All Users List ///////////////////////////////////////////////

        List<User> users1 = getUserRepository().findAll();
        String currentuser = Variables.getUsername();

        List<String> usernames = users1.stream()
                .map(User::getUsername).toList();

        int index = usernames.indexOf(currentuser);


        if(index != -1){
            users1.remove(index);
        }


        model.addAttribute("users1", users1);

        /////////////////////////////////// All Users List ///////////////////////////////////////////////
        return "/friendslogged";
    }


    @PostMapping("/friendremove")
    public String listUserFriendRemoveFromList(@RequestParam("username") String username1,  Model model){

        String currentusername = Variables.getUsername();
        User user = getUserRepository().findByUsername(currentusername);
        username1 = "_" + username1;
        String currentfriendlist = user.getFriends();
        currentfriendlist = currentfriendlist.replaceFirst(username1, "");
        System.out.println(currentfriendlist);
        user.setFriends(currentfriendlist);
        getUserRepository().save(user);

        /////////////////////////////////// User Friend List /////////////////////////////////////////////

        List<User> allusers = getUserRepository().findAll();
        List<User> userfriends1 = new ArrayList<User>();
        String userfriendlist = user.getFriends();
        String[] myArray = new String[0];


        if (userfriendlist != null) {
            myArray = userfriendlist.split("_", -1);
        }


        myArray = Arrays.stream(myArray)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        int i = 0;


        while (i < myArray.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray[i])) {
                    userfriends1.add(obj);
                }
            }
            i++;
        }


        List<String> username = userfriends1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userfriends1", userfriends1);
        username.clear();

        /////////////////////////////////// User Friend List /////////////////////////////////////////////
        /////////////////////////////////// User Invitations /////////////////////////////////////////////

        List<User> userinvited1 = new ArrayList<User>();
        String userinvitationlist = user.getInvitatiots(); // paima dabartini vartotoja
        String[] myArray1 = new String[0];


        if (userinvitationlist != null) {
            myArray1 = userinvitationlist.split("_", -1);
        }

        myArray1 = Arrays.stream(myArray1)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);


        i = 0;


        while (i < myArray1.length) {
            for (User obj : allusers) {
                if (Objects.equals(obj.getUsername(), myArray1[i])) {
                    userinvited1.add(obj);
                }
            }
            i++;
        }

        username = userinvited1.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());

        model.addAttribute("userinvited1", userinvited1);
        username.clear();

        /////////////////////////////////// User Invitations /////////////////////////////////////////////
        /////////////////////////////////// All Users List ///////////////////////////////////////////////

        List<User> users1 = getUserRepository().findAll();
        String currentuser = Variables.getUsername();

        List<String> usernames = users1.stream()
                .map(User::getUsername).toList();

        int index = usernames.indexOf(currentuser);


        if(index != -1){
            users1.remove(index);
        }


        model.addAttribute("users1", users1);

        /////////////////////////////////// All Users List ///////////////////////////////////////////////
        return "/friendslogged";
    }

}
