package com.maxkuzmenchuk.new_releases_bot.util;

import com.maxkuzmenchuk.new_releases_bot.repository.model.Channel;
import com.maxkuzmenchuk.new_releases_bot.service.ChannelService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ChannelParser {
    private static final Logger logger = LoggerFactory.getLogger(ChannelParser.class);

    static ChannelService channelService;

    @Autowired
    public ChannelParser(ChannelService channelService) {
        ChannelParser.channelService = channelService;
    }

    /**
     * Ввод личного канала для пользователя
     *
     * @param message - сообщение пользователя
     * @param id      -
     * @return String - ответ пользователю
     */
    public static String setChannel(String message, Long id) {
        String result = "";

        if (!message.equalsIgnoreCase("")) {
            String channelName = getChannelName(message);
            String channelId = getChannelId(channelName);

            if (channelName.equalsIgnoreCase("error")) return StaticValues.NOT_VALID_CHANNEL_COMMAND_MESSAGE;
            if (channelId.equalsIgnoreCase("exist")) return StaticValues.CHANNEL_IS_EXISTS_MESSAGE;

            saveChannel(channelName, Long.parseLong(channelId), id);

            result = StaticValues.CHANNEL_SAVED_SUCCESSFULLY_MESSAGE;
        } else {
            result = StaticValues.NO_CHANNEL_ID_MESSAGE;
        }

        return result;
    }

    /**
     * Метод для получения имени канала
     *
     * @param message - сообщение от пользователя
     * @return String - имя канала, если все прошло без ошибок
     */
    public static String getChannelName(String message) {
        String result = "";
        if (!message.contains("@")) return "error";

        result = message.substring(message.indexOf("@") + 1);
        logger.info("channelName is: " + result);

        if (result.equalsIgnoreCase("")) return StaticValues.NO_CHANNEL_NAME_MESSAGE;

        return result;
    }

    /**
     * Метод для получения id канала
     *
     * @param channelName - имя канала
     * @return String - id канала, если все прошло без ошибок; NO_CHANNEL_NAME_MESSAGE - если после @ пустота
     */
    public static String getChannelId(String channelName) {
        final OkHttpClient httpClient = new OkHttpClient();
        String chatId = "";

        if (!isChannelExist(channelName)) {
            Request request = new Request.Builder()
                    .url(StaticValues.MESSAGE_TO_CHANNEL_URL + "chat_id=@" + channelName + "&text=" + StaticValues.CHANNEL_TEST_MESSAGE)
                    .build();

            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                JSONObject responseJSON = new JSONObject(response.body().string());

                logger.info("Response: " + responseJSON);

                String chatInfo = responseJSON.getJSONObject("result").getJSONObject("chat").toString();
                chatId = chatInfo.substring(chatInfo.indexOf("-"), chatInfo.indexOf(","));

                logger.info("chat ID is: " + chatId);
            } catch (IOException e) {
                logger.error("ERROR!! ", e);
            }
        } else {
            return "exist";
        }

        return chatId;
    }

    /**
     * Метод для проверки наличия введенного канала
     *
     * @return boolean - true if channel already exist
     */
    public static boolean isChannelExist(String channelName) {

        if (!channelName.equalsIgnoreCase("")) {
            return channelService.findByChannelName(channelName).isPresent();
        } else {
            throw new NullPointerException("Channel name equals \"\" ");
        }
    }

    /**
     * Метод для сохранинения данных канала в БД
     *
     * @param channelName - имя канала
     * @param channelId   - id канала
     */
    public static void saveChannel(String channelName, Long channelId, Long id) {
        Channel c = new Channel();

        c.setChannelId(channelId);
        c.setChannelName(channelName);
        c.setUserId(id);

        channelService.saveChannel(c);

        logger.info("Channel saved successfully!");
    }

    /**
     * Метод позволяет получить id канала по id пользователя
     *
     * @param id - id пользователя
     * @return Long - id канала
     */
    public static Long getIdChannelForResponse(Long id) {
        return channelService.findIdChannelForResponse(id).getChannelId();
    }
}
