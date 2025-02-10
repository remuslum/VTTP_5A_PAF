package vttp2023.batch3.assessment.paf.bookings.controllers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import vttp2023.batch3.assessment.paf.bookings.models.ListingList;
import vttp2023.batch3.assessment.paf.bookings.models.exceptions.InvalidValueException;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;

@Controller
public class ListingsController {
	@Autowired
	private ListingsService listingsService;

	//TODO: Task 2
	@GetMapping
	public ModelAndView getHomePage(HttpSession httpSession){
		String errorMessage = Optional.ofNullable((String) httpSession.getAttribute("errorMessage")).orElse("");
		System.out.println(errorMessage);
		ModelAndView mav = new ModelAndView("view1");

		mav.addObject("errorMessage", errorMessage);
		mav.addObject("countries", listingsService.getAllCountries());
		return mav;
	}
	
	//TODO: Task 3
	@GetMapping("/search")
	public ModelAndView getSearchResults(@RequestParam MultiValueMap<String, String> params, HttpSession httpSession){
		if(httpSession.getAttribute("listings") != null){
			ModelAndView mav = new ModelAndView("view2");
			ListingList listings = (ListingList) httpSession.getAttribute("listings");
			mav.addObject("listings", listings.getListings());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView();
			String country = params.getFirst("country");
			int person = Integer.parseInt(params.getFirst("numOfPersons"));
			double minPrice = Float.parseFloat(params.getFirst("minPrice"));
			double maxPrice = Float.parseFloat(params.getFirst("maxPrice"));
			boolean isThereError = false;
	
			if(person < 1 || person > 10){
				httpSession.setAttribute("errorMessage", "Number of people must be between 1 and 10");
				isThereError = !isThereError;
			}
	
			if(minPrice < 1 || minPrice > 10000){
				httpSession.setAttribute("errorMessage", "minPrice is out of range");
				isThereError = !isThereError;
			}
	
			if(maxPrice < 1 || maxPrice > 10000){
				httpSession.setAttribute("errorMessage", "maxPrice is out of range");
				isThereError = !isThereError;
			}
	
			if(minPrice > maxPrice){
				httpSession.setAttribute("errorMessage", "minPrice cannot be higher than maxPrice");
				isThereError = !isThereError;
			}
	
			if(isThereError){
				mav.setViewName("view1");
			} else {
				ListingList listingList = listingsService.findListing(country, person, minPrice, maxPrice);
				httpSession.setAttribute("listings", listingList);
				mav.addObject("listings", listingList.getListings());
				mav.setViewName("view2");
			}
			return mav;
		}
		
	}

	//TODO: Task 4
	@GetMapping("/search/{objectId}")
	public ModelAndView getListingDetails(@PathVariable String objectId, HttpSession httpSession){
		ModelAndView mav = new ModelAndView("view3");
		String invalidValueErrorMessage = Optional.ofNullable((String) httpSession.getAttribute("invalidValueErrorMessage")).orElse(" ");
		mav.addObject("details", listingsService.findListingDetails(objectId));
		mav.addObject("invalidValueErrorMessage",invalidValueErrorMessage);
		return mav;
	}

	//TODO: Task 5
	@PostMapping("/booking/{objectId}")
	public ModelAndView makeReservation(@PathVariable String objectId, @RequestParam MultiValueMap<String, String> params, HttpSession httpSession){
		String name = params.getFirst("name");
		String email = params.getFirst("email");
		LocalDate arrivalDate = LocalDate.parse(params.getFirst("arrivalDate"));
		int duration = Integer.parseInt(params.getFirst("duration"));
		String reservationId = UUID.randomUUID().toString().substring(0,8);

		try {
			listingsService.insertReservationAndUpdateVacancy(reservationId, objectId, name, email, arrivalDate, duration);
			ModelAndView mav = new ModelAndView("view4");
			httpSession.setAttribute("invalidValueErrorMessage", " ");
			mav.addObject("reservationId",reservationId);
			return mav;
		} catch (InvalidValueException e) {
			httpSession.setAttribute("invalidValueErrorMessage", e.getMessage());
			return new ModelAndView("redirect:/search/" + objectId);
		}
	}

}
