package com.abinayamart.web;

import com.abinayamart.dao.ReviewDAO;
import com.abinayamart.model.Review;
import com.abinayamart.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private final ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp"
            );
            return;
        }

        try {

            int productId = Integer.parseInt(
                    request.getParameter("productId")
            );

            int rating = Integer.parseInt(
                    request.getParameter("rating")
            );

            String comment =
                    request.getParameter("comment");

            Review review = new Review();

            review.setProductId(productId);
            review.setBuyerId(user.getId());
            review.setRating(rating);
            review.setComment(comment);

            reviewDAO.addOrUpdate(review);

            response.sendRedirect(
                    request.getContextPath()
                    + "/products.jsp?review=success"
            );

        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid review data.",
                    e
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to save review.",
                    e
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath() + "/products.jsp"
        );
    }
}