package Dao;

import Models.Review;
//import javax.json.*;
import javafx.collections.ObservableList;
import com.google.gson.JsonArray;
import java.util.List;

public interface ReviewDAO {
    Review updateReviewState(Long reviewId, Review review);
    JsonArray getReviewByState(String stateName);
    JsonArray getReviewByStructure(String structureName);
    List<Object> getReviewByPlace(String placeName);
    List<Object> getReviewByStructureAndPlace(String structureName, String placeName);
}
