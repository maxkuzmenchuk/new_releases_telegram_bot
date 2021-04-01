package com.maxkuzmenchuk.new_releases_bot.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "new_releases_bot", name = "releases")
public class Release {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "release_id")
    private String releaseId;

    @Column(name = "artists")
    private String artist;

    @Column(name = "release_name")
    private String releaseName;

    @Column(name = "release_type")
    private String releaseType;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "url")
    private String url;
}
