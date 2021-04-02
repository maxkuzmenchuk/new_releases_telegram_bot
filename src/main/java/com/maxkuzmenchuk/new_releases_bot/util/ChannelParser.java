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

/**
 * Handle operations (set or delete) with user channel
 */
@Component
public class ChannelParser {
    private static final Logger logger = LoggerFactory.getLogger(ChannelParser.class);

    static ChannelService channelService;

    @Autowired
    public ChannelParser(ChannelService channelService) {
        ChannelParser.channelService = channelService;
    }

    /**
     * Set channel to user
     *
     * @param message - user message
     * @param id      - user id
     * @return response to user
     */
    public static String setChannel(String message, Long id) {
        String result = "";

        if (!message.equalsIgnoreCase("")) {

            if (!userChannelIsExists(id)) {

                String channelName = getChannelName(message);
                String channelId = getChannelId(channelName);

                if (channelName.equalsIgnoreCase("error")) return StaticValues.NOT_VALID_CHANNEL_COMMAND;
                if (channelId.equalsIgnoreCase("exist")) return StaticValues.CHANNEL_IS_EXISTS;

                saveChannel(channelName, Long.parseLong(channelId), id);

                result = StaticValues.CHANNEL_SAVED_SUCCESSFULLY;
            } else {
                String userChannelName = channelService.findChannelByUserId(id).getChannelName();
                result = StaticValues.CHANNEL_FOR_USER_IS_EXISTS + " Название канала: " + userChannelName;
            }
        } else {
            result = StaticValues.NO_CHANNEL_ID;
        }

        return result;
    }

    /**
     * Delete user channel
     *
     * @param msg - user message
     * @param id  - user id
     * @return response to user
     */
    public static String deleteChannel(String msg, Long id) {
        String res = "";

        if (!msg.equalsIgnoreCase("")) {

            if (userChannelIsExists(id)) {
                channelService.deleteChannelByUserId(id);

                res = StaticValues.CHANNEL_DELETED_SUCCESSFULLY;
            } else {
                res = StaticValues.NO_CHANNEL_FOR_USER;
            }
        } else {
            res = StaticValues.DELETION_ERROR;
        }

        return res;

    }

    /**
     * Get channel name
     *
     * @param message - user message
     * @return channel name
     */
    public static String getChannelName(String message) {
        String result = "";
        if (!message.contains("@")) return "error";

        result = message.substring(message.indexOf("@") + 1);
        logger.info("channelName is: " + result);

        if (result.equalsIgnoreCase("")) return StaticValues.NO_CHANNEL_NAME;

        return result;
    }

    /**
     * Get id channel
     *
     * @param channelName - channel name
     * @return id channel if errors not happened; NO_CHANNEL_NAME_MESSAGE if string is empty after @
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
     * Check if channel is exists in database
     *
     * @return true - if channel is exists, false - if not
     */
    public static boolean isChannelExist(String channelName) {

        if (!channelName.equalsIgnoreCase("")) {
            return channelService.findByChannelName(channelName).isPresent();
        } else {
            throw new NullPointerException("Channel name equals \"\" ");
        }
    }

    /**
     * Save channel to database
     *
     * @param id          - user id
     * @param channelName - channel name
     * @param channelId   - channel id
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
     * Get id channel by user id
     *
     * @param id - user id
     * @return channel id
     */
    public static Long getIdChannelForResponse(Long id) {
        return channelService.findChannelByUserId(id).getChannelId();
    }

    /**
     * Check if channel already set for this user
     *
     * @param id - user id
     * @return true - if channel already set, false - if not
     */
    public static boolean userChannelIsExists(Long id) {
        Channel channel = channelService.findChannelByUserId(id);

        return channel != null;
    }
}
