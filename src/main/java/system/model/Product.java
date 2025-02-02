package system.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.Objects;

@NodeEntity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String pricestring;
    private String name;
    private String category;
    private int price;
    private String description;
    private String imagenamestring;
    private int sellcount;
    private String type;
    private int rating;
    private int ratingsum;
    private int ratingcount;
    private String sellcountstring;
    private String ratingstring;
    private String boughtproducts;
    private int count;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRatingsum() {
        return ratingsum;
    }

    public void setRatingsum(int ratingsum) {
        this.ratingsum = ratingsum;
    }


    public int getRatingcount() {
        return ratingcount;
    }

    public void setRatingcount(int ratingcount) {
        this.ratingcount = ratingcount;
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


    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImagenamestring() {
        return imagenamestring;
    }

    public void setImagenamestring(String imagenamestring) {
        this.imagenamestring = imagenamestring;
    }


    public int getSellcount() {
        return sellcount;
    }

    public void setSellcount(int sellcount) {
        this.sellcount = sellcount;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }



    public String getPricestring() {
        return pricestring;
    }

    public void setPricestring(String pricestring) {
        this.pricestring = pricestring;
    }


    public String getSellcountstring() {
        return sellcountstring;
    }

    public void setSellcountstring(String sellcountstring) {
        this.sellcountstring = sellcountstring;
    }


    public String getRatingstring() {
        return ratingstring;
    }

    public void setRatingstring(String ratingstring) {
        this.ratingstring = ratingstring;
    }

}
