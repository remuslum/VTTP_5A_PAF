package vttp2023.batch4.paf.assessment.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;
import static vttp2023.batch4.paf.assessment.util.Listings.C_LISTINGS;
import static vttp2023.batch4.paf.assessment.util.Listings.F_ACCOMMODATES;
import static vttp2023.batch4.paf.assessment.util.Listings.F_ADDRESS;
import static vttp2023.batch4.paf.assessment.util.Listings.F_ADDRESS_COUNTRY;
import static vttp2023.batch4.paf.assessment.util.Listings.F_ADDRESS_SUBURB;
import static vttp2023.batch4.paf.assessment.util.Listings.F_ID;
import static vttp2023.batch4.paf.assessment.util.Listings.F_MIN_NIGHTS;
import static vttp2023.batch4.paf.assessment.util.Listings.F_NAME;
import static vttp2023.batch4.paf.assessment.util.Listings.F_PRICE;
import static vttp2023.batch4.paf.assessment.util.Listings.NESTED_FIELDS_SEPARATOR;

@Repository
public class ListingsRepository {
	
	// You may add additional dependency injections

	private static final String ADDRESS_SUBURB = F_ADDRESS+NESTED_FIELDS_SEPARATOR+F_ADDRESS_SUBURB;

	@Autowired
	private MongoTemplate template;

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred }) 
	 *
	 *	db.listings.aggregate([
			{
				$match : {
					'address.country' : {
						$regex : 'australia', $options : 'i'
					},
					'address.suburb' : {
						$ne : ""
					} 
				}
			}, {
				$project : {
					_id:0,
					address:1
				}
			}, {
				$group : {
					_id: "$address.suburb"
				}
			}
		])

	 */
	public List<String> getSuburbs(String country) {
		String addressCountry = F_ADDRESS+NESTED_FIELDS_SEPARATOR+F_ADDRESS_COUNTRY;
		
		MatchOperation matchCountry = Aggregation.match(Criteria.where(addressCountry).regex(country,"i"));
		MatchOperation removeEmptyStrings = Aggregation.match(Criteria.where(ADDRESS_SUBURB).ne(""));
		ProjectionOperation projectAddress = Aggregation.project(F_ADDRESS).andExclude(F_ID);
		GroupOperation groupBySuburb = Aggregation.group(ADDRESS_SUBURB);
		Aggregation aggregation = Aggregation.newAggregation(matchCountry,removeEmptyStrings,projectAddress,
		groupBySuburb);

		List<Document> suburbsDocuments = template.aggregate(aggregation, C_LISTINGS, Document.class).getMappedResults();
		List<String> suburbs = new ArrayList<>();
		suburbsDocuments.forEach(s -> {
			suburbs.add(s.getString(F_ID));
		});
		return suburbs;
	}

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred }) 
	 * db.listings.find(
			{
				'address.suburb' : {
					$regex : 'Kingsford', $options : 'i'
				},
				price : {
					$lte : 100
				},
				accommodates : {
					$lte: 4
				},
				min_nights : {
					$lte : 5
				} 
			},{
				name:1,
				accommodates:1,
				price:1
			}
		).sort({
			price : -1
		})
	 *
	 */
	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {
		Criteria matchSuburb = Criteria.where(ADDRESS_SUBURB).regex(suburb, "i");
		Criteria priceLesserThan = Criteria.where(F_PRICE).lte(priceRange);
		Criteria accommodatesLesserThan = Criteria.where(F_ACCOMMODATES).gte(persons);
		Criteria minNightsLesserThan = Criteria.where(F_MIN_NIGHTS).lte(duration);
		
		Query query = new Query().addCriteria(matchSuburb).addCriteria(priceLesserThan).addCriteria(accommodatesLesserThan)
		.addCriteria(minNightsLesserThan);
		query.fields().include(F_NAME,F_ACCOMMODATES,F_PRICE);

		return convertDocumentsTAccommodationSummaries(template.find(query,Document.class,C_LISTINGS));
	}

	private List<AccommodationSummary> convertDocumentsTAccommodationSummaries(List<Document> documents){
		List<AccommodationSummary> accommodationSummaries = new ArrayList<>();
		documents.forEach(d -> {
			AccommodationSummary accommodationSummary = new AccommodationSummary();
			accommodationSummary.setId(d.getString(F_ID));
			accommodationSummary.setName(d.getString(F_NAME));
			accommodationSummary.setAccomodates(d.getInteger(F_ACCOMMODATES));
			accommodationSummary.setPrice(d.get(F_PRICE, Number.class).floatValue());
			accommodationSummaries.add(accommodationSummary);
		});
		return accommodationSummaries;
	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}


}
