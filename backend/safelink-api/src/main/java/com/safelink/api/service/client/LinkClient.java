package com.safelink.api.service.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LinkClient {
    private final WebClient webClient;

    public LinkClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public String shortenUrl(String url) {
        String json = webClient.post()
                .uri("/shorten-url")
                .bodyValue("{\"url\": \"" + url + "\"}")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject obj = new JSONObject(json);
        return obj.getString("url");
    }
}