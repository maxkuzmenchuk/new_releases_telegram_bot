package com.maxkuzmenchuk.new_releases_bot;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static com.maxkuzmenchuk.new_releases_bot.util.AccessToken.getToken;

public class SpotifyParser {

    public static String getAlbum() throws Exception {
        final OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/browse/new-releases?country=RU&limit=1")
                .addHeader("Accept", "application/json")  // add request headers
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + getToken())
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JSONObject obj = new JSONObject(response.body().string());
            JSONArray albums = obj.getJSONObject("albums").getJSONArray("items");

            String albumURL = albums.getJSONObject(0).getJSONObject("external_urls").getString("spotify");
            String artistName = albums.getJSONObject(0).getJSONArray("artists").getJSONObject(0).getString("name");
            String albumName = albums.getJSONObject(0).getString("name");
            String albumType = albums.getJSONObject(0).getString("type");
            String releaseDate = albums.getJSONObject(0).getString("release_date");
            int totalTracks = albums.getJSONObject(0).getInt("total_tracks");


            response.close();

            return " \uD83D\uDD25\uD83D\uDD25 New Drop! \uD83D\uDD25\uD83D\uDD25"
                    + "\n "
                    + "\n \uD83D\uDCAD Artist: " + artistName
                    + "\n \uD83D\uDCDD Title: " + albumName
                    + "\n \uD83D\uDCBF Type: " + albumType
                    + "\n \uD83D\uDCC6 Release date: " + releaseDate
                    + "\n \uD83C\uDFA7 Number of tracks: " + totalTracks
                    + "\n "
                    + "\n \uD83D\uDD17 URL: " + albumURL;

        }
    }
}
