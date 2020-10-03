package Dao;

import Models.Review;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewDAOimpl implements ReviewDAO{

    public static final String startURL = "http://localhost:8080/reviews";

    @Override
    public Review updateReviewState(Long reviewId, Review review) {
        return null;
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
    public JsonArray getReviewByStructure(String structureName) {

        return null;
    }

    @Override
    public List<Object> getReviewByPlace(String placeName) {

        return null;
    }

    @Override
    public List<Object> getReviewByStructureAndPlace(String structureName, String placeName) {

        return null;
    }

}