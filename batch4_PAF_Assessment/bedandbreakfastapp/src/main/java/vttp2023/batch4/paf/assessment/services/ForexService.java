package vttp2023.batch4.paf.assessment.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2023.batch4.paf.assessment.components.UrlBuilder;

@Service
public class ForexService {
	@Autowired
	private UrlBuilder urlBuilder;

	// TODO: Task 5 
	public float convert(String from, String to, float amount) {
		try {
			String response = urlBuilder.convertCurrency(from, to);
			JsonObject responseObject = Json.createReader(new StringReader(response)).readObject();
			double target = responseObject.getJsonObject("rates").getJsonNumber(to.toUpperCase()).doubleValue();
			return amount * (float) target;
		} catch (RestClientException e){
			return -1000f;
		}
	}
}
