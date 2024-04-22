package ru.practicum;


import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.StatsDtoRequest;

import java.util.List;
import java.util.Map;

public class StatsClient {
    private final RestTemplate restTemplate;

    public StatsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Object> post(String path, StatsDtoRequest statsDtoRequest) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(statsDtoRequest, defaultHeaders());
        ResponseEntity<Object> statsServerResponse;
        try {
            statsServerResponse = restTemplate.exchange(path, HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statsServerResponse);
    }

    public ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, defaultHeaders());

        ResponseEntity<Object> statsClientResponse;
        try {
            if (parameters != null) {
                statsClientResponse = restTemplate.exchange(path, HttpMethod.GET, requestEntity,Object.class, parameters);
            } else {
                statsClientResponse = restTemplate.exchange(path, HttpMethod.GET, requestEntity,Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statsClientResponse);

    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }

}