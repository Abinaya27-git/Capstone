<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.abinayamart.model.Product" %>
<%@ page import="com.abinayamart.model.Order" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>AbinayaMart - Seller Dashboard</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
        }

        .header {
            background: #222;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .nav {
            margin-top: 15px;
        }

        .nav a {
            display: inline-block;
            padding: 10px 18px;
            margin: 0 5px;
            background: white;
            color: #222;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .nav a:hover {
            background: #ddd;
        }

        .container {
            padding: 30px;
            max-width: 1000px;
            margin: auto;
        }

        .section-title {
            margin-top: 10px;
            margin-bottom: 20px;
        }

        .product {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ccc;
            display: flex;
            gap: 25px;
            align-items: center;
        }

        .product-image {
            width: 180px;
            height: 180px;
            object-fit: contain;
            border-radius: 8px;
            border: 1px solid #ddd;
            background: #fafafa;
        }

        .product-details {
            flex: 1;
        }

        .product h3 {
            margin-top: 0;
        }

        .price {
            color: green;
            font-weight: bold;
            font-size: 20px;
        }

        .stock {
            font-weight: bold;
        }

        .no-image {
            width: 180px;
            height: 180px;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #eee;
            border-radius: 8px;
            color: #777;
            text-align: center;
        }

        .action-buttons {
            margin-top: 15px;
            display: flex;
            gap: 10px;
        }

        .edit-button {
            display: inline-block;
            padding: 10px 18px;
            background: #222;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
        }

        .edit-button:hover {
            background: #444;
        }

        .delete-button {
            padding: 10px 18px;
            background: #c62828;
            color: white;
            border: none;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
        }

        .delete-button:hover {
            background: #a61f1f;
        }

        .orders-section {
            margin-top: 50px;
        }

        .order {
            background: white;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ccc;
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #ddd;
            padding-bottom: 12px;
            margin-bottom: 15px;
        }

        .order-id {
            font-size: 20px;
            font-weight: bold;
        }

        .status {
            background: #e8f5e9;
            color: #2e7d32;
            padding: 7px 12px;
            border-radius: 15px;
            font-weight: bold;
        }

        .order-details {
            line-height: 1.8;
        }

        .order-total {
            color: #6c3df5;
            font-size: 20px;
            font-weight: bold;
        }

        .empty-orders {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ccc;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="header">

    <h1>AbinayaMart - Seller Dashboard</h1>

    <p>My Products</p>

    <div class="nav">

        <a href="<%= request.getContextPath() %>/">
            Home
        </a>

        <a href="<%= request.getContextPath() %>/seller">
            My Products
        </a>

        <a href="<%= request.getContextPath() %>/logout">
            Logout
        </a>

    </div>

</div>

<div class="container">

    <h2 class="section-title">My Products</h2>

<%
    List<Product> products =
        (List<Product>) request.getAttribute("products");

    if (products != null && !products.isEmpty()) {

        for (Product p : products) {
%>

    <div class="product">

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

        <div class="product-details">

            <h3><%= p.getName() %></h3>

            <p>
                <strong>Category:</strong>
                <%= p.getCategory() %>
            </p>

            <p>
                <strong>Description:</strong>
                <%= p.getDescription() %>
            </p>

            <p class="price">
                ₹<%= p.getPrice() %>
            </p>

            <p class="stock">
                Stock: <%= p.getStock() %>
            </p>

            <div class="action-buttons">

                <a
                    class="edit-button"
                    href="<%= request.getContextPath() %>/edit-product?id=<%= p.getId() %>"
                >
                    Edit Product
                </a>

                <form
                    action="<%= request.getContextPath() %>/delete-product"
                    method="post"
                    onsubmit="return confirm('Are you sure you want to delete this product?');"
                    style="margin: 0;"
                >

                    <input
                        type="hidden"
                        name="id"
                        value="<%= p.getId() %>"
                    >

                    <button
                        type="submit"
                        class="delete-button"
                    >
                        Delete Product
                    </button>

                </form>

            </div>

        </div>

    </div>

<%
        }

    } else {
%>

    <p>No products found.</p>

<%
    }
%>

    <!-- SELLER ORDERS -->

    <div class="orders-section">

        <h2 class="section-title">
            Incoming Orders 📦
        </h2>

<%
    List<Order> orders =
        (List<Order>) request.getAttribute("orders");

    if (orders != null && !orders.isEmpty()) {

        for (Order order : orders) {
%>

        <div class="order">

            <div class="order-header">

                <div class="order-id">
                    Order #<%= order.getId() %>
                </div>

                <div class="status">
                    <%= order.getStatus() %>
                </div>

            </div>

            <div class="order-details">

                <p>
                    <strong>Buyer:</strong>
                    <%= order.getBuyerName() %>
                </p>

                <p class="order-total">
                    Total Amount:
                    ₹<%= order.getTotalAmount() %>
                </p>

                <p>
                    <strong>Payment Method:</strong>
                    <%= order.getPaymentMode() %>
                </p>

                <p>
                    <strong>Order Date:</strong>
                    <%= order.getCreatedAt() %>
                </p>

            </div>

        </div>

<%
        }

    } else {
%>

        <div class="empty-orders">

            <h3>No Incoming Orders</h3>

            <p>
                No orders have been placed for your products yet.
            </p>

        </div>

<%
    }
%>

    </div>

</div>

</body>
</html>