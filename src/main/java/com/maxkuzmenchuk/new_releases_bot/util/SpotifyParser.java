package com.maxkuzmenchuk.new_releases_bot.util;

import com.maxkuzmenchuk.new_releases_bot.repository.model.Release;
import com.maxkuzmenchuk.new_releases_bot.service.ReleaseService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

import static com.maxkuzmenchuk.new_releases_bot.util.AccessToken.getToken;

/**
 * Working with Spotify API
 * <p>
 * Getting list of new releases
 */
@Component
public class SpotifyParser {
    private static final Logger logger = LoggerFactory.getLogger(SpotifyParser.class);

    static ReleaseService releaseService;

    @Autowired
    public SpotifyParser(ReleaseService releaseService) {
        SpotifyParser.releaseService = releaseService;
    }

    /**
     * Getting array of new releases
     *
     * @return JSONArray of data about new releases
     * @throws Exception - if errors is happening
     */
    public static JSONArray getNewReleases() throws Exception {
        final OkHttpClient httpClient = new OkHttpClient();
        JSONArray releasesArray = new JSONArray();
        LocalDate recentReleaseDate = releaseService.findRecentDate();

        if (recentReleaseDate == null) {
            recentReleaseDate = LocalDate.now().minusWeeks(1L);

            logger.info("Recent Release Date :: " + recentReleaseDate.toString());
        }

        Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/browse/new-releases?country=RU&limit=20&offset=0")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + getToken())
                .build();

        logger.info("Getting JSONArray with new drops");

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JSONObject jsonResponse = new JSONObject(Objects.requireNonNull(response.body()).string());
            JSONArray albums = jsonResponse.getJSONObject("albums").getJSONArray("items");

            for (int i = 0; i < albums.length(); i++) {
                String releaseId = albums.getJSONObject(i).getString("id");
                String artists = getArtistsName(albums.getJSONObject(i).getJSONArray("artists"));
                String releaseName = albums.getJSONObject(i).getString("name");
                String releaseType = albums.getJSONObject(i).getString("type");
                String releaseDate = albums.getJSONObject(i).getString("release_date");
                String releaseURL = albums.getJSONObject(i).getJSONObject("external_urls").getString("spotify");
                int totalTracks = albums.getJSONObject(i).getInt("total_tracks");

                if (LocalDate.parse(releaseDate).isAfter(recentReleaseDate)) {
                    JSONObject releaseJson = new JSONObject();
                    releaseJson.put("release_id", releaseId);
                    releaseJson.put("artists", artists);
                    releaseJson.put("release_name", releaseName);
                    releaseJson.put("release_type", releaseType);
                    releaseJson.put("release_date", releaseDate);
                    releaseJson.put("total_tracks", totalTracks);
                    releaseJson.put("release_url", releaseURL);

                    releasesArray.put(releaseJson);

                    // save release to database
                    Release releaseObject = new Release();

                    releaseObject.setReleaseId(releaseId);
                    releaseObject.setArtist(artists);
                    releaseObject.setReleaseName(releaseName);
                    releaseObject.setReleaseType(releaseType);
                    releaseObject.setReleaseDate(LocalDate.parse(releaseDate));
                    releaseObject.setUrl(releaseURL);

                    releaseService.save(releaseObject);
                }
            }
        } catch (Exception e) {
            logger.error("URL execute Exception", e);
        }
        return releasesArray;
    }

    /**
     * Create artist names string
     *
     * @param artists - array with data about artists from json
     * @return string of artist names
     */
    private static String getArtistsName(JSONArray artists) {
        StringBuilder allNames = new StringBuilder();
        String result = "";
        for (int i = 0; i < artists.length(); i++) {
            String name = artists.getJSONObject(i).getString("name");
            allNames.append(name).append(", ");
        }
        result = allNames.toString();

        return result.substring(0, result.length() - 2);
    }
}
