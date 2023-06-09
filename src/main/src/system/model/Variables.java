package system.model;

import java.util.*;

public final class Variables {
    private static String username = "testdata";
    private static String password = "testdata";
    private static Long currentCartId;
    private static String currentCartName;
    private static User userstatic = new User();
    private static int online = 0;
    private static String[] cart;
    private static List<Product> productscart = new ArrayList<Product>();
    private static List<Product> productscartanon = new ArrayList<Product>();

    public Variables() {
    }

    public static String getCurrentCartName() {
        return currentCartName;
    }

    public static void setCurrentCartName(String currentCartName) {
        Variables.currentCartName = currentCartName;
    }

    public static List<Product> getProductscart() {
        return productscart;
    }

    public static void setProductscart(List<Product> productscart) {
        Variables.productscart = productscart;
    }

    public static Long getCurrentCartId() {
        return currentCartId;
    }

    public static void setCurrentCartId(Long currentCartId) {
        Variables.currentCartId = currentCartId;
    }

    public static String[] getCart() {
        return cart;
    }

    public static void setCart(String[] cart) {
        Variables.cart = cart;
    }


    public static List<Product> getProductscartanon() {
        return productscartanon;
    }

    public static void setProductscartanon(List<Product> productscartanon) {
        Variables.productscartanon = productscartanon;
    }


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Variables.username = username;
    }



    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Variables.password = password;
    }


    public static int getOnline() {
        return online;
    }

    public static void setOnline(int online) {
        Variables.online = online;
    }



    public static User getUserstatic() {
        return userstatic;
    }
    public static void setUserstatic(User userstatic) {
        Variables.userstatic = userstatic;
    }
}
