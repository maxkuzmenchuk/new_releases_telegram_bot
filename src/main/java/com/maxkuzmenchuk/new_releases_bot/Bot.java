package com.maxkuzmenchuk.new_releases_bot;

import com.maxkuzmenchuk.new_releases_bot.util.StaticValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.maxkuzmenchuk.new_releases_bot.SpotifyParser.getAlbum;
import static com.maxkuzmenchuk.new_releases_bot.util.ChannelParser.setChannel;


public class Bot extends TelegramLongPollingBot {
    Logger logger = LoggerFactory.getLogger(Bot.class);

    @Override
    public String getBotUsername() {
        return StaticValues.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return StaticValues.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage msg = new SendMessage().enableMarkdown(true).setChatId(update.getMessage().getChatId());
        msg.setText(response(update.getMessage().getText(), update.getMessage().getChat().getFirstName(), update.getMessage().getChat().getId()));

        try {
            logger.info("===== Response to user: =====");
            logger.info("Chat ID: " + msg.getChatId());
            logger.info("Body: \n" + msg.getText());
            logger.info("=============================");



            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String response(String msg, String name, Long id) {

        if (msg.startsWith("/channel")) return setChannel(msg, id);

        switch (msg.trim()) {
            case "/start":
                return "Hello, " + name + "! \uD83D\uDC4B\uD83C\uDFFB "
                        + "\n "
                        + "\n Используй команду /help для получения справочной информации";
            case "/help":
                return StaticValues.HELP_MESSAGE;
            case "/update":
                try {
                    logger.info("Getting info from Spotify API");
                    return getAlbum();
                } catch (Exception e) {
                    logger.error("Update error: ", e);
                }
                break;
        }

        return StaticValues.WRONG_MESSAGE;
    }


}
