package vttp2023.batch3.assessment.paf.bookings.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.DistinctIterable;

import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.C_LISTINGS;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_ACCOMMODATES;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_ADDRESS;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_AMENITIES;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_COUNTRY;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_DESCRIPTION;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_IMAGES;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_NAME;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_OBJECT_ID;
import static vttp2023.batch3.assessment.paf.bookings.util.ListingsMongo.F_PRICE;

@Repository
public class ListingsRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 2
	public DistinctIterable<String> getAllCountries(){
		return mongoTemplate.getCollection(C_LISTINGS).distinct(F_COUNTRY, String.class);
	}
	
	//TODO: Task 3
	public List<Document> findListings(String country, int numOfPeople, double lowerPrice, double higherPrice){
		Criteria criteriaCountry = Criteria.where(F_COUNTRY).is(country);
		Criteria criteriaAccomodates = Criteria.where(F_ACCOMMODATES).is(numOfPeople);
		Criteria criteriaPrice = Criteria.where(F_PRICE).gte(lowerPrice).lte(higherPrice);
		Query query = new Query();
		query.addCriteria(criteriaCountry);
		query.addCriteria(criteriaAccomodates);
		query.addCriteria(criteriaPrice);
		query.fields().include(F_NAME, F_PRICE, F_IMAGES);
		
		return mongoTemplate.find(query,Document.class, C_LISTINGS);
	}

	//TODO: Task 4
	public Document findListing(String objectId){
		Query query = Query.query(Criteria.where(F_OBJECT_ID).is(objectId));
		query.fields().include(F_OBJECT_ID, F_DESCRIPTION, F_ADDRESS, F_IMAGES, F_PRICE, F_AMENITIES, F_NAME);

		return mongoTemplate.findOne(query, Document.class);
	}

	//TODO: Task 5


}
