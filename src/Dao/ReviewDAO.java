package Dao;

import Models.Review;
//import javax.json.*;
import javafx.collections.ObservableList;
import com.google.gson.JsonArray;

import java.net.http.HttpResponse;
import java.util.List;

public interface ReviewDAO {
    HttpResponse<String> updateReviewState(Long reviewId, String state);
    List<Object[]> getReviewByState(String stateName);
    List<Object[]> getReviewByStructure(String structureName);
    List<Object[]> getReviewByPlace(String placeName);
    List<Object[]> getReviewByStructureAndPlace(String structureName, String placeName);
}
