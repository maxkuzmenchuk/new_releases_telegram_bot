package com.maxkuzmenchuk.new_releases_bot.repository;

import com.maxkuzmenchuk.new_releases_bot.repository.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
    @Query("select max(releaseDate) from Release ")
    LocalDate findDate();
}
