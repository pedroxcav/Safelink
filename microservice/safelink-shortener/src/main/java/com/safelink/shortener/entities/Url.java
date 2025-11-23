package com.safelink.shortener.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "url_table")
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    private String id;

    @Column(nullable = false)
    private String fullUrl;
}
