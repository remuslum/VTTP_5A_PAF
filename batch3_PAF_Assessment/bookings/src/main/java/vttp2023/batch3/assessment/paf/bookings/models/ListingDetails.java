package vttp2023.batch3.assessment.paf.bookings.models;

public class ListingDetails extends Listing{
    private String description;
    private String address;
    private String amenities;

    public ListingDetails() {
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAmenities() {
        return amenities;
    }
    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public static ListingDetails createListingDetails(String id, String name, double price, String image,
    String description, String address, String amenities){
        ListingDetails listingDetails = new ListingDetails();
        listingDetails.setId(id);
        listingDetails.setName(name);
        listingDetails.setPrice(price);
        listingDetails.setImage(image);
        listingDetails.setDescription(description);
        listingDetails.setAddress(address);
        listingDetails.setAmenities(amenities);
        return listingDetails;
    }
 
}
