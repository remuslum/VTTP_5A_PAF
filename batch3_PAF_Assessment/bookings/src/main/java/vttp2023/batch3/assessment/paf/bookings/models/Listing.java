package vttp2023.batch3.assessment.paf.bookings.models;

public class Listing {
    private String id;
    private String name;
    private double price;
    private String image;

    public Listing() {
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }  

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public static Listing createListing(String id, String name, double price, String image){
        Listing listing = new Listing();
        listing.setId(id);
        listing.setName(name);
        listing.setPrice(price);
        listing.setImage(image);
        return listing; 
    }






     
}
