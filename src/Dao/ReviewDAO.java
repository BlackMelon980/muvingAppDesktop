package Dao;

import Models.Review;

import java.util.List;

public interface ReviewDAO {
    List<Review> getReviewByState(String stateName);
}
