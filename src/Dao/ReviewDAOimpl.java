package Dao;

import Models.Review;
import Singleton.UserSingleton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
//import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpHeaders;

public class ReviewDAOimpl implements ReviewDAO{

    public static final String startURL = "http://localhost:5000/reviews";

    @Override
    public HttpResponse<String> updateReviewState(Long reviewId, String state) {

        String endPoint = startURL+"/update?review_id=" + reviewId;
        System.out.println(endPoint);
        final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endPoint))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + UserSingleton.getInstance().getLoggedUser().getToken())
                .POST(HttpRequest.BodyPublishers.ofString(state))
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    @Override
    public List<Object[]> getReviewByState(String stateName) {
        String getURL = startURL + "/getReviewByState?state_name=" + stateName;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestReview = HttpRequest.newBuilder().GET().uri(URI.create(getURL)).build();

        try{
            HttpResponse<String> response = client.send(requestReview, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<ArrayList<Object[]>>(){}.getType();
            return new Gson().fromJson(response.body(), listType);

        }catch (IOException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  List<Object[]> getReviewByStructure(String structureName) {
        String getURL = startURL + "/getReviewByStructure?name=" + structureName;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestReview = HttpRequest.newBuilder().GET().uri(URI.create(getURL)).build();

        try{
            HttpResponse<String> response = client.send(requestReview, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<ArrayList<Object[]>>(){}.getType();
            return new Gson().fromJson(response.body(), listType);

        }catch (IOException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  List<Object[]> getReviewByPlace(String placeName) {

        String getURL = startURL + "/getReviewByPlace?name=" + placeName;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestReview = HttpRequest.newBuilder().GET().uri(URI.create(getURL)).build();

        try{
            HttpResponse<String> response = client.send(requestReview, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<ArrayList<Object[]>>(){}.getType();
            return new Gson().fromJson(response.body(), listType);

        }catch (IOException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public  List<Object[]> getReviewByStructureAndPlace(String structureName, String placeName) {

        String getURL = startURL + "/getReviewByStructureAndPlace?place_name=" + placeName + "&structure_name=" + structureName;


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestReview = HttpRequest.newBuilder().GET().uri(URI.create(getURL)).build();

        try{
            HttpResponse<String> response = client.send(requestReview, HttpResponse.BodyHandlers.ofString());
            Type listType = new TypeToken<ArrayList<Object[]>>(){}.getType();
            return new Gson().fromJson(response.body(), listType);

        }catch (IOException | NumberFormatException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}