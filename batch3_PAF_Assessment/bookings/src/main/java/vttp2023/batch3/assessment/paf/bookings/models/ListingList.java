package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.List;

public class ListingList {
    private List<Listing> listings;

    public ListingList(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

}
