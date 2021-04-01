package com.maxkuzmenchuk.new_releases_bot.service;

import com.maxkuzmenchuk.new_releases_bot.repository.ChannelRepository;
import com.maxkuzmenchuk.new_releases_bot.repository.model.Channel;
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

    public Channel findIdChannelForResponse(Long id) {
        return channelRepository.findChannelByUserId(id);
    }
}
