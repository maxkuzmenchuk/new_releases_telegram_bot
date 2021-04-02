package com.maxkuzmenchuk.new_releases_bot.util;

import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Class for update access_token for each query to Spotify API
 */
public class AccessToken {
    private static final Logger logger = LoggerFactory.getLogger(AccessToken.class);

    /**
     * Get token from Spotify API to get response from api
     *
     * @return token for accessing api
     * @throws IOException - exception if response is not successful
     */
    public static String getToken() throws IOException {
        final OkHttpClient httpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("Accept", "application/json")
                .add("Content-Type", "x-www-form-urlencoded")
                .add("grant_type", "refresh_token")
                .add("refresh_token", StaticValues.REFRESH_TOKEN)
                .add("client_id", StaticValues.CLIENT_ID)
                .add("client_secret", StaticValues.CLIENT_SECRET)
                .build();

        Request request = new Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JSONObject obj = new JSONObject(response.body().string());

            response.close();

            logger.info("New access_token: " + obj.getString("access_token"));

            return obj.getString("access_token");
        }
    }
}
