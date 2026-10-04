package com.abinayamart.service;

import com.abinayamart.dao.ReviewDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Review;

import java.util.List;

/** Review business rules (rating must be 1-5). */
public class ReviewService {
    private final ReviewDAO reviewDAO = new ReviewDAO();

    public void addReview(int productId, int buyerId, int rating, String comment) throws AbinayaMartException {
        if (rating < 1 || rating > 5) {
            throw new AbinayaMartException("Rating must be between 1 and 5 stars.");
        }
        Review r = new Review();
        r.setProductId(productId);
        r.setBuyerId(buyerId);
        r.setRating(rating);
        r.setComment(comment == null ? "" : comment.trim());
        reviewDAO.addOrUpdate(r);
    }

    public List<Review> forProduct(int productId) throws AbinayaMartException {
        return reviewDAO.findByProduct(productId);
    }
}
