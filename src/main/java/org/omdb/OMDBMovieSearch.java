package org.omdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OMDBMovieSearch {

    public static void main(String[] args) {
        String apiKey = "26f8a727";
        String searchQuery = "gymkata";

        try {
            String apiUrl = "http://www.omdbapi.com/?apikey=" + apiKey + "&s=" + searchQuery;
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.toString());

            if (jsonResponse.has("Search")) {
                JsonNode movies = jsonResponse.get("Search");

                for (JsonNode movie : movies) {
                    String title = movie.get("Title").asText();
                    String year = movie.get("Year").asText();
                    System.out.println("Title: " + title + "," + year);
                }
            } else {
                System.out.println("No movies found for the given search query.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
