package com.safelink.api.service.client;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LinkClient {
    private final WebClient webClient1;

    public LinkClient(WebClient webClient1) {
        this.webClient1 = webClient1;
    }

    public String shortenUrl(String url) {
        String json = webClient1.post()
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