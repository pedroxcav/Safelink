package com.safelink.shortener.controller;

import com.safelink.shortener.controller.dto.ShortenRequest;
import com.safelink.shortener.controller.dto.ShortenResponse;
import com.safelink.shortener.entities.Url;
import com.safelink.shortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlController {
    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping("/shorten-url")
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody @Valid ShortenRequest data, HttpServletRequest servlet) {
        String random;
        do random = "safelink-"+RandomStringUtils.randomAlphanumeric(10);
        while(urlRepository.existsById(random));

        Url url = new Url();
        url.setId(random);
        url.setFullUrl(data.url());
        urlRepository.save(url);

        String replaced = servlet.getRequestURL().toString().replace("shorten-url", random);
        ShortenResponse response = new ShortenResponse(replaced);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") String id) {
        Optional<Url> url = urlRepository.findById(id);

        if(url.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
