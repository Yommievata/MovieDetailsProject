package org.omdb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieByTitle {
    private static final String API_KEY = "26f8a727";
    private static final String OMDB_API_URL = "http://www.omdbapi.com/";

    public static void main(String[] args) {
        MovieByTitle movieService = new MovieByTitle();

        // print movie by imdbID
        Movie movieById = movieService.getMovieDetails("tt3896198");
        System.out.println(movieById);

        // t can only print 1 movie at a time
        Movie movieByTitle = movieService.getMovieDetailsByTitle("Guardians");
        System.out.println(movieByTitle);
    }

    public MovieByTitle() {
        Gson gson = new Gson();
    }

    public Movie getMovieDetails(String imdbID) {
        String url = OMDB_API_URL + "?i=" + imdbID + "&apikey=" + API_KEY;

        HttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                String movieTitle = jsonObject.get("Title").getAsString();
                String movieYear = jsonObject.get("Year").getAsString();
                String movieRated = jsonObject.get("Rated").getAsString();
                String movieReleased = jsonObject.get("Released").getAsString();
                String movieRuntime = jsonObject.get("Runtime").getAsString();
                List<String> movieGenre = Arrays.asList(jsonObject.get("Genre").getAsString().split(", "));
                String movieDirector = jsonObject.get("Director").getAsString();
                List<String> movieWriter = Arrays.asList(jsonObject.get("Writer").getAsString().split(", "));
                List<String> movieActors = Arrays.asList(jsonObject.get("Actors").getAsString().split(", "));
                String moviePlot = jsonObject.get("Plot").getAsString();
                String movieLanguage = jsonObject.get("Language").getAsString();
                String movieCountry = jsonObject.get("Country").getAsString();
                String movieAwards = jsonObject.get("Awards").getAsString();
                String moviePoster = jsonObject.get("Poster").getAsString();
                List<Rating> movieRatings = extractRatings(jsonObject.getAsJsonArray("Ratings"));
                String movieMetascore = jsonObject.get("Metascore").getAsString();
                String movieImdbRating = jsonObject.get("imdbRating").getAsString();
                String movieImdbVotes = jsonObject.get("imdbVotes").getAsString();
                String movieImdbID = jsonObject.get("imdbID").getAsString();
                String movieType = jsonObject.get("Type").getAsString();
                String movieDVD = jsonObject.get("DVD").getAsString();
                String movieBoxOffice = jsonObject.get("BoxOffice").getAsString();
                String movieProduction = jsonObject.get("Production").getAsString();
                String movieWebsite = jsonObject.get("Website").getAsString();

                return new Movie(movieTitle, movieYear, movieRated, movieReleased, movieRuntime, movieGenre,
                        movieDirector, movieWriter, movieActors, moviePlot, movieLanguage, movieCountry, movieAwards,
                        moviePoster, movieRatings, movieMetascore, movieImdbRating, movieImdbVotes, movieImdbID,
                        movieType, movieDVD, movieBoxOffice, movieProduction, movieWebsite);
            } else {
                System.err.println("Error: HTTP request failed with status code " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Movie getMovieDetailsByTitle(String title) {
        String url = OMDB_API_URL + "?t=" + title + "&apikey=" + API_KEY;

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                String movieTitle = jsonObject.get("Title").getAsString();
                String movieYear = jsonObject.get("Year").getAsString();
                String movieRated = jsonObject.get("Rated").getAsString();
                String movieReleased = jsonObject.get("Released").getAsString();
                String movieRuntime = jsonObject.get("Runtime").getAsString();
                List<String> movieGenre = Arrays.asList(jsonObject.get("Genre").getAsString().split(", "));
                String movieDirector = jsonObject.get("Director").getAsString();
                List<String> movieWriter = Arrays.asList(jsonObject.get("Writer").getAsString().split(", "));
                List<String> movieActors = Arrays.asList(jsonObject.get("Actors").getAsString().split(", "));
                String moviePlot = jsonObject.get("Plot").getAsString();
                String movieLanguage = jsonObject.get("Language").getAsString();
                String movieCountry = jsonObject.get("Country").getAsString();
                String movieAwards = jsonObject.get("Awards").getAsString();
                String moviePoster = jsonObject.get("Poster").getAsString();
                List<Rating> movieRatings = extractRatings(jsonObject.getAsJsonArray("Ratings"));
                String movieMetascore = jsonObject.get("Metascore").getAsString();
                String movieImdbRating = jsonObject.get("imdbRating").getAsString();
                String movieImdbVotes = jsonObject.get("imdbVotes").getAsString();
                String movieImdbID = jsonObject.get("imdbID").getAsString();
                String movieType = jsonObject.get("Type").getAsString();
                String movieDVD = jsonObject.get("DVD").getAsString();
                String movieBoxOffice = jsonObject.get("BoxOffice").getAsString();
                String movieProduction = jsonObject.get("Production").getAsString();
                String movieWebsite = jsonObject.get("Website").getAsString();

                return new Movie(movieTitle, movieYear, movieRated, movieReleased, movieRuntime, movieGenre,
                        movieDirector, movieWriter, movieActors, moviePlot, movieLanguage, movieCountry, movieAwards,
                        moviePoster, movieRatings, movieMetascore, movieImdbRating, movieImdbVotes, movieImdbID,
                        movieType, movieDVD, movieBoxOffice, movieProduction, movieWebsite);
            } else {
                System.err.println("Error: HTTP request failed with status code " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<Rating> extractRatings(JsonArray ratingsArray) {
        List<Rating> ratings = new ArrayList<>();

        for (int i = 0; i < ratingsArray.size(); i++) {
            JsonObject ratingObject = ratingsArray.get(i).getAsJsonObject();
            String source = ratingObject.get("Source").getAsString();
            String value = ratingObject.get("Value").getAsString();
            ratings.add(new Rating(source, value));
        }

        return ratings;
    }
}
