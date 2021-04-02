package com.maxkuzmenchuk.new_releases_bot;

import com.maxkuzmenchuk.new_releases_bot.util.StaticValues;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.maxkuzmenchuk.new_releases_bot.util.ChannelParser.getIdChannelForResponse;
import static com.maxkuzmenchuk.new_releases_bot.util.ChannelParser.setChannel;
import static com.maxkuzmenchuk.new_releases_bot.util.SpotifyParser.getNewReleases;


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

        SendMessage msg = new SendMessage().enableMarkdown(true).setChatId(setMessageId(update));
        List<String> response = response(update);

        for (String resp : response) {
            msg.setText(resp);

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
    }

    /**
     * Метод для создания текста ответа на команды
     *
     * @param update - данные Telegram API
     * @return List<String> - список с ответами
     */
    public List<String> response(Update update) {
        String msg = update.getMessage().getText();
        String name = update.getMessage().getChat().getFirstName();
        Long id = update.getMessage().getChat().getId();

        if (msg.startsWith("/channel")) {
            List<String> channelResponse = new ArrayList<>();
            channelResponse.add(setChannel(msg, id));

            return channelResponse;
        }

        switch (msg.trim()) {
            case "/start":
                List<String> startResponse = new ArrayList<>();
                startResponse.add("Hello, " + name + "! \uD83D\uDC4B\uD83C\uDFFB "
                        + "\n "
                        + "\n Используй команду /help для получения справочной информации");

                return startResponse;
            case "/help":
                List<String> helpResponse = new ArrayList<>();
                helpResponse.add(StaticValues.HELP_MESSAGE);

                return helpResponse;
            case "/update":
                List<String> updateResponse = new ArrayList<>();

                try {
                    logger.info("Getting info from Spotify API");
                    JSONArray releases = getNewReleases();

                    for (int i = 0; i < releases.length(); i++) {
                        JSONObject release = releases.getJSONObject(i);

                        updateResponse.add(" \uD83D\uDD25\uD83D\uDD25 New Drop! \uD83D\uDD25\uD83D\uDD25"
                                + "\n "
                                + "\n \uD83D\uDCAD Artist: " + release.getString("artists")
                                + "\n \uD83D\uDCDD Title: " + release.getString("release_name")
                                + "\n \uD83D\uDCBF Type: " + release.getString("release_type")
                                + "\n \uD83D\uDCC6 Release date: " + release.getString("release_date")
                                + "\n \uD83C\uDFA7 Number of tracks: " + release.getInt("total_tracks")
                                + "\n "
                                + "\n \uD83D\uDD17 URL: " + release.getString("release_url"));
                    }

                    return updateResponse;

                } catch (Exception e) {
                    logger.error("Update error: ", e);
                }
                break;
        }

        List<String> wrongResponse = new ArrayList<>();
        wrongResponse.add(StaticValues.WRONG_MESSAGE);

        return wrongResponse;
    }

    /**
     * Установка id для ответа
     *
     * @param updMessage - сообщение из телеграма
     * @return id - идентификатор чата. куда отсылать ответ
     */
    public Long setMessageId(Update updMessage) {
        String command = updMessage.getMessage().getText();
        Long idBot = updMessage.getMessage().getChatId();

        if (!command.equalsIgnoreCase("/update")) {
            return idBot;
        } else {
            Long idChannel = getIdChannelForResponse(updMessage.getMessage().getChat().getId());

            logger.info("idChannel = " + idChannel);

            if (idChannel != null) {
                return idChannel;
            } else {
                return idBot;
            }
        }
    }
}
