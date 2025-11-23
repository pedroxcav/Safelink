package com.safelink.shortener.repository;

import com.safelink.shortener.entities.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UrlRepository extends JpaRepository<Url, String> {

}
