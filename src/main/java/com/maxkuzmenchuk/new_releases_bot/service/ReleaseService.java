package com.maxkuzmenchuk.new_releases_bot.service;

import com.maxkuzmenchuk.new_releases_bot.repository.ReleaseRepository;
import com.maxkuzmenchuk.new_releases_bot.repository.model.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReleaseService {
    @Autowired
    private ReleaseRepository releaseRepository;

    public void save(Release release) {
        releaseRepository.save(release);
    }

    public LocalDate findRecentDate() {
        return releaseRepository.findDate();
    }
}
