package com.maxkuzmenchuk.new_releases_bot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    public Optional<Channel> findByChannelName(String name) {
        return channelRepository.findChannelByChannelName(name);
    }

    public void saveChannel(Channel channel) {
        channelRepository.save(channel);
    }

    public List findAll() {
        return channelRepository.findAll();
    }
}
