<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.abinayamart.dao.ProductDAO" %>
<%@ page import="com.abinayamart.dao.ReviewDAO" %>
<%@ page import="com.abinayamart.model.Product" %>
<%@ page import="com.abinayamart.model.Review" %>

<%
    List<Product> products = null;
    String errorMessage = null;

    try {
        ProductDAO productDAO = new ProductDAO();
        products = productDAO.findAll();
    } catch (Exception e) {
        errorMessage = "Unable to load products.";
    }

    ReviewDAO reviewDAO = new ReviewDAO();
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>AbinayaMart - Products</title>

    <style>

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        body {
            background: #f7f8fc;
            color: #222;
        }

        header {
            background: white;
            padding: 18px 50px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #6c3df5;
        }

        nav a {
            text-decoration: none;
            margin-left: 25px;
            color: #333;
            font-weight: 500;
        }

        nav a:hover {
            color: #6c3df5;
        }

        .hero {
            text-align: center;
            padding: 40px 20px 25px;
        }

        .hero h1 {
            font-size: 34px;
            margin-bottom: 10px;
        }

        .hero p {
            color: #666;
        }

        .search-box {
            text-align: center;
            margin: 20px;
        }

        .search-box input {
            width: 320px;
            padding: 13px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 15px;
        }

        .products {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
            gap: 25px;
            padding: 30px 50px;
        }

        .card {
            background: white;
            border-radius: 15px;
            padding: 18px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            transition: 0.2s;
        }

        .card:hover {
            transform: translateY(-4px);
        }

        .product-image {
            width: 100%;
            height: 180px;
            object-fit: cover;
            border-radius: 12px;
        }

        .no-image {
            width: 100%;
            height: 180px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #eee;
            border-radius: 12px;
            color: #777;
        }

        .category {
            color: #777;
            font-size: 13px;
            margin-top: 12px;
        }

        .card h3 {
            margin: 8px 0;
        }

        .description {
            color: #666;
            font-size: 14px;
            min-height: 35px;
        }

        .price {
            font-size: 20px;
            font-weight: bold;
            color: #6c3df5;
            margin: 10px 0;
        }

        .stock {
            font-size: 14px;
            color: #555;
            margin-bottom: 12px;
        }

        .cart-btn {
            width: 100%;
            border: none;
            padding: 12px;
            border-radius: 8px;
            background: #6c3df5;
            color: white;
            font-weight: bold;
            cursor: pointer;
        }

        .cart-btn:hover {
            background: #5426d9;
        }

        .review-section {
            margin-top: 18px;
            padding-top: 15px;
            border-top: 1px solid #eee;
        }

        .rating-summary {
            font-size: 15px;
            font-weight: bold;
            margin-bottom: 12px;
        }

        .stars {
            color: #f5a623;
            font-size: 20px;
            letter-spacing: 2px;
        }

        .review-form select,
        .review-form textarea {
            width: 100%;
            padding: 9px;
            margin-top: 8px;
            border: 1px solid #ddd;
            border-radius: 7px;
            font-size: 14px;
        }

        .review-form textarea {
            min-height: 70px;
            resize: vertical;
        }

        .review-btn {
            width: 100%;
            margin-top: 8px;
            padding: 10px;
            border: none;
            border-radius: 7px;
            background: #222;
            color: white;
            cursor: pointer;
            font-weight: bold;
        }

        .review-btn:hover {
            background: #444;
        }

        .customer-reviews {
            margin-top: 15px;
        }

        .customer-reviews h4 {
            margin-bottom: 10px;
        }

        .review-item {
            background: #f7f7f7;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 8px;
        }

        .review-stars {
            color: #f5a623;
            font-size: 17px;
        }

        .review-name {
            font-weight: bold;
            font-size: 14px;
        }

        .review-comment {
            color: #555;
            font-size: 13px;
            margin-top: 5px;
        }

        .no-reviews {
            color: #777;
            font-size: 13px;
        }

        .success {
            text-align: center;
            color: green;
            font-weight: bold;
            margin: 20px;
        }

        .error {
            text-align: center;
            color: red;
            font-weight: bold;
            margin: 30px;
        }

        .empty {
            text-align: center;
            padding: 50px;
            color: #666;
        }

    </style>

</head>

<body>

<header>

    <div class="logo">
        🛍 ABINAYA MART
    </div>

    <nav>
        <a href="products.jsp">Home</a>
        <a href="products.jsp">Products</a>
        <a href="cart">Cart 🛒</a>
        <a href="order-history">My Orders</a>
        <a href="index.jsp">Logout</a>
    </nav>

</header>

<section class="hero">

    <h1>Welcome to AbinayaMart</h1>

    <p>
        Discover amazing products at great prices
    </p>

</section>

<%
    String reviewSuccess = request.getParameter("review");

    if ("success".equals(reviewSuccess)) {
%>

    <div class="success">
        Review submitted successfully!
    </div>

<%
    }
%>

<div class="search-box">

    <input
        type="text"
        placeholder="Search products..."
    >

</div>

<%
    if (errorMessage != null) {
%>

    <div class="error">
        <%= errorMessage %>
    </div>

<%
    } else if (products == null || products.isEmpty()) {
%>

    <div class="empty">

        <h2>No products available</h2>

        <p>Please check again later.</p>

    </div>

<%
    } else {
%>

<section class="products">

<%
        for (Product p : products) {

            double averageRating = 0.0;
            List<Review> reviews = new ArrayList<>();

            try {
                averageRating = reviewDAO.avgRating(p.getId());
                reviews = reviewDAO.findByProduct(p.getId());
            } catch (Exception ignored) {
            }
%>

    <div class="card">

        <%
            if (p.getImageUrl() != null
                    && !p.getImageUrl().isBlank()) {
        %>

            <img
                class="product-image"
                src="<%= p.getImageUrl() %>"
                alt="<%= p.getName() %>"
            >

        <%
            } else {
        %>

            <div class="no-image">
                No Image
            </div>

        <%
            }
        %>

        <div class="category">
            <%= p.getCategory() %>
        </div>

        <h3>
            <%= p.getName() %>
        </h3>

        <p class="description">
            <%= p.getDescription() %>
        </p>

        <div class="price">
            ₹<%= p.getPrice() %>
        </div>

        <div class="stock">
            Stock: <%= p.getStock() %>
        </div>

        <form method="post" action="cart">

            <input
                type="hidden"
                name="action"
                value="add"
            >

            <input
                type="hidden"
                name="productId"
                value="<%= p.getId() %>"
            >

            <input
                type="hidden"
                name="quantity"
                value="1"
            >

            <button
                type="submit"
                class="cart-btn"
            >
                Add to Cart
            </button>

        </form>

        <div class="review-section">

            <div class="rating-summary">

                Rating:

                <span class="stars">

                <%
                    int roundedRating = (int) Math.round(averageRating);

                    for (int i = 1; i <= 5; i++) {
                        if (i <= roundedRating) {
                %>
                            ★
                <%
                        } else {
                %>
                            ☆
                <%
                        }
                    }
                %>

                </span>

                <%= String.format("%.1f", averageRating) %>/5

            </div>

            <form
                class="review-form"
                method="post"
                action="review"
            >

                <input
                    type="hidden"
                    name="productId"
                    value="<%= p.getId() %>"
                >

                <select name="rating" required>

                    <option value="">
                        Select Rating
                    </option>

                    <option value="5">
                        ★★★★★ - Excellent
                    </option>

                    <option value="4">
                        ★★★★☆ - Very Good
                    </option>

                    <option value="3">
                        ★★★☆☆ - Good
                    </option>

                    <option value="2">
                        ★★☆☆☆ - Average
                    </option>

                    <option value="1">
                        ★☆☆☆☆ - Poor
                    </option>

                </select>

                <textarea
                    name="comment"
                    placeholder="Write your review..."
                ></textarea>

                <button
                    type="submit"
                    class="review-btn"
                >
                    Submit Review
                </button>

            </form>

            <div class="customer-reviews">

                <h4>
                    Customer Reviews
                </h4>

                <%
                    if (reviews.isEmpty()) {
                %>

                    <div class="no-reviews">
                        No reviews yet.
                    </div>

                <%
                    } else {

                        for (Review review : reviews) {
                %>

                    <div class="review-item">

                        <div class="review-name">
                            <%= review.getBuyerName() %>
                        </div>

                        <div class="review-stars">

                        <%
                            for (int i = 1; i <= 5; i++) {

                                if (i <= review.getRating()) {
                        %>
                                    ★
                        <%
                                } else {
                        %>
                                    ☆
                        <%
                                }
                            }
                        %>

                        </div>

                        <div class="review-comment">
                            <%= review.getComment() %>
                        </div>

                    </div>

                <%
                        }
                    }
                %>

            </div>

        </div>

    </div>

<%
        }
%>

</section>

<%
    }
%>

</body>
</html>