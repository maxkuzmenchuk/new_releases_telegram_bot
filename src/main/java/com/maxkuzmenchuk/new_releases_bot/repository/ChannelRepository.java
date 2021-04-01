package com.maxkuzmenchuk.new_releases_bot.repository;

import com.maxkuzmenchuk.new_releases_bot.repository.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findChannelByChannelName(String name);
}
