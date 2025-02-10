package vttp2023.batch3.assessment.paf.bookings.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.client.DistinctIterable;

import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.models.ListingDetails;
import vttp2023.batch3.assessment.paf.bookings.models.ListingList;
import vttp2023.batch3.assessment.paf.bookings.models.exceptions.InvalidValueException;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;
import vttp2023.batch3.assessment.paf.bookings.repositories.ReservationRepository;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_ADDRESS;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_AMENITIES;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_DESCRIPTION;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_IMAGES;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_IMAGES_URL;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_NAME;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_OBJECT_ID;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_PRICE;

@Service
public class ListingsService {
	@Autowired
	private ListingsRepository listingsRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	private Document extractEmbeddedDocument(String field, Document document){
		return document.get(field, Document.class);
	}

	private String extractAddressInfo(Document document){
		Document addressDocument = extractEmbeddedDocument(F_ADDRESS, document);
		StringBuilder sb = new StringBuilder();
		return sb.append(addressDocument.getString("street")).append("\n").append(addressDocument.getString("suburb"))
		.append("\n").append(addressDocument.getString("country")).toString();
	}

	private String extractAmenities(Document document){
		List<String> amenitiesList = document.getList(F_AMENITIES, String.class);
		StringBuilder sb = new StringBuilder();
		amenitiesList.forEach(a -> sb.append(a).append(","));
		return sb.toString();
	}

	private String extractImageUrl(Document document){
		Document imageDocument = extractEmbeddedDocument(F_IMAGES, document);
		return imageDocument.getString(F_IMAGES_URL);
	}
	
	//TODO: Task 2
	public List<String> getAllCountries(){
		DistinctIterable<String> countries = listingsRepository.getAllCountries();
		List<String> countriesList = new ArrayList<>();
		countries.forEach(c -> countriesList.add(c));
		return countriesList;
	}
	
	//TODO: Task 3
	public ListingList findListing(String country, int numOfPeople, double lowerPrice, double higherPrice){
		List<Document> listingsDocument = listingsRepository.findListings(country, numOfPeople, lowerPrice, higherPrice);
		List<Listing> listingsList = new ArrayList<>();
		listingsDocument.forEach(d -> {
			listingsList.add(Listing.createListing(d.getString(F_OBJECT_ID),d.getString(F_NAME), d.getDouble(F_PRICE), extractImageUrl(d)));
		});
		return new ListingList(listingsList);
	}

	//TODO: Task 4
	public ListingDetails findListingDetails(String objectId){
		Document listingDocument = listingsRepository.findListing(objectId);

		return ListingDetails.createListingDetails(listingDocument.getString(F_OBJECT_ID), listingDocument.getString(F_NAME), listingDocument.getDouble(F_PRICE), 
		extractImageUrl(listingDocument), listingDocument.getString(F_DESCRIPTION), extractAddressInfo(listingDocument) , extractAmenities(listingDocument));
	}

	//TODO: Task 5
	@Transactional
	public boolean insertReservationAndUpdateVacancy(String reservationId, String accountId, String name, String email, LocalDate arrivalDate, int duration){
		int vacancy = reservationRepository.getVacancy(accountId);
		if(duration > vacancy){
			throw new InvalidValueException("The vacancy of this accomodation is limited to %d".formatted(vacancy));
		} else {
			return reservationRepository.makeReservation(reservationId,accountId, name, email, arrivalDate, duration) &&
			reservationRepository.updateVacancy(accountId, duration, vacancy);
		}
	}

}
