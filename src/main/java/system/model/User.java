package system.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class User {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private int firsttimeloginstatus = 0; //0 - pirmą karta 1 - ne pirmą kartą kintamasis pirmo prisijungimo regomendacijoms
    private String friends;//draugas1_draugas2_draugas3_ po to isskaidyt pagal _. Gal tokiu budu rasti produktu pavadinimus.
    private String invitations;
    private String bought;// taip pat kaip su friens rast prekes
    private String boughtproducts;
    private int born;
    private String sex;
    private String city;
    private String mail;

    @Relationship(type = "HAS_CART")
    private List<Cart> carts = new ArrayList<>();

    @Relationship(type = "KNOWS")
    private List<User> users = new ArrayList<>();


    public User() {
    }

    public User(Long id, String username, String password, String role, int firsttimeloginstatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firsttimeloginstatus = firsttimeloginstatus;
    }

    public User(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public String getBoughtproducts() {
        return boughtproducts;
    }

    public void setBoughtproducts(String boughtproducts) {
        this.boughtproducts = boughtproducts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getFirsttimeloginstatus() {
        return firsttimeloginstatus;
    }

    public void setFirsttimeloginstatus(int firsttimeloginstatus) {
        this.firsttimeloginstatus = firsttimeloginstatus;
    }


    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }


    public String getBought() {
        return bought;
    }

    public void setBought(String bought) {
        this.bought = bought;
    }


    public String getInvitatiots() {
        return invitations;
    }

    public void setInvitatiots(String invitatiots) {
        this.invitations = invitatiots;
    }


    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

