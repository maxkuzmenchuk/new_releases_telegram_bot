package com.maxkuzmenchuk.new_releases_bot.repository;

import com.maxkuzmenchuk.new_releases_bot.repository.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findChannelByChannelName(String name);

    Channel findChannelByUserId(Long id);

    @Transactional
    @Modifying
    @Query("delete from Channel where userId = :id")
    void deleteByUserId(@Param("id") Long id);
}
