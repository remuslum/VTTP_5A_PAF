package vttp2023.batch4.paf.assessment.components;


import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static vttp2023.batch4.paf.assessment.util.Url.FRANKFURTER_URL;

@Component
public class UrlBuilder {
    public String convertCurrency(String symbolFrom, String symbolTo){
        String uri = UriComponentsBuilder.fromUriString(FRANKFURTER_URL).queryParam("base", symbolFrom)
        .queryParam("symbols", symbolTo).toUriString();

        RequestEntity<Void> convertRequest = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(convertRequest, String.class).getBody();
    }
}
