package ru.practicum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.BaseClient.BaseClient;
import ru.practicum.exception.ServerException;
import ru.practicum.dto.EventStatDto;

import java.util.List;
import java.util.Map;

@Service

public class StatsClient extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public StatsClient(@Value("${stat.server.url}") String url,
                       RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(url + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void post(EventStatDto eventStatDto) {
        try {
            rest.exchange("http://stats-server:9090/hit", HttpMethod.POST, new HttpEntity<>(eventStatDto, defaultHeaders()),
                    Object.class);
        } catch (HttpStatusCodeException e) {
            throw new ServerException("Ошибка клиента при отправке метода post");
        }
    }

    public ResponseEntity<Object> get(String path, Map<String, Object> parameters) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, defaultHeaders());

        ResponseEntity<Object> statsClientResponse;
        try {
            if (parameters != null) {
                statsClientResponse = rest.exchange(path, HttpMethod.GET, requestEntity, Object.class, parameters);
            } else {
                statsClientResponse = rest.exchange(path, HttpMethod.GET, requestEntity, Object.class);
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