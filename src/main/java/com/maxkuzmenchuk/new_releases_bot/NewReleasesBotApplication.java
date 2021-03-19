package com.maxkuzmenchuk.new_releases_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;


@SpringBootApplication(scanBasePackages={"com.maxkuzmenchuk.new_releases_bot"})
public class NewReleasesBotApplication {
    private static final Logger logger = LoggerFactory.getLogger(NewReleasesBotApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(NewReleasesBotApplication.class, args);
    }

    @Bean
    public void bot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();

        try {
            telegram.registerBot(new Bot());
            logger.info("Bot initialized!");
        } catch (
                TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
