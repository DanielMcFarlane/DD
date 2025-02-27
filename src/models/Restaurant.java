package models;

public class Restaurant {
    private Integer id;
    private String name;
    private String cuisine;
    private String imageURL;

    public Restaurant(Integer id, String name, String cuisine, String imageURL) {
        this.id = (id == null) ? 0 : id; // int can't be nulled so used Integer
        this.name = name;
        this.cuisine = cuisine;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id - " + id +
                ", name - " + name + '\'' +
                ", cuisine - " + cuisine + '\'' +
                ", imageURL - " + imageURL + '\'' +
                '}';
    }
}