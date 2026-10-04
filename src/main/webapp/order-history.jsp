<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.abinayamart.model.Order" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AbinayaMart - My Orders</title>

    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            margin: 0;
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

        .container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 20px;
        }

        h1 {
            margin-bottom: 25px;
        }

        .order-card {
            background: white;
            padding: 25px;
            margin-bottom: 20px;
            border-radius: 12px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.08);
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            border-bottom: 1px solid #eee;
            padding-bottom: 15px;
        }

        .order-id {
            font-size: 20px;
            font-weight: bold;
        }

        .status {
            background: #e8f5e9;
            color: #2e7d32;
            padding: 7px 14px;
            border-radius: 20px;
            font-weight: bold;
        }

        .order-details {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .detail-box {
            background: #f7f8fc;
            padding: 15px;
            border-radius: 8px;
        }

        .detail-label {
            color: #777;
            font-size: 13px;
            margin-bottom: 6px;
        }

        .detail-value {
            font-weight: bold;
            font-size: 16px;
        }

        .total {
            color: #6c3df5;
            font-size: 20px;
        }

        .empty {
            background: white;
            padding: 50px;
            text-align: center;
            border-radius: 12px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.08);
        }

        .shop-btn {
            display: inline-block;
            margin-top: 15px;
            padding: 11px 20px;
            background: #6c3df5;
            color: white;
            text-decoration: none;
            border-radius: 7px;
        }

        @media (max-width: 700px) {
            header {
                padding: 15px 20px;
            }

            nav a {
                margin-left: 10px;
            }

            .order-details {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>

<header>

    <div class="logo">🛍 ABINAYA MART</div>

    <nav>
        <a href="products.jsp">Home</a>
        <a href="products.jsp">Products</a>
        <a href="cart">Cart 🛒</a>
        <a href="order-history">My Orders</a>
        <a href="index.jsp">Logout</a>
    </nav>

</header>

<div class="container">

    <h1>My Orders 📦</h1>

<%
    List<Order> orders =
            (List<Order>) request.getAttribute("orders");

    if (orders != null && !orders.isEmpty()) {

        for (Order order : orders) {
%>

    <div class="order-card">

        <div class="order-header">

            <div class="order-id">
                Order #<%= order.getId() %>
            </div>

            <div class="status">
                <%= order.getStatus() %>
            </div>

        </div>

        <div class="order-details">

            <div class="detail-box">

                <div class="detail-label">
                    Total Amount
                </div>

                <div class="detail-value total">
                    ₹<%= order.getTotalAmount() %>
                </div>

            </div>

            <div class="detail-box">

                <div class="detail-label">
                    Payment Method
                </div>

                <div class="detail-value">
                    <%= order.getPaymentMode() %>
                </div>

            </div>

            <div class="detail-box">

                <div class="detail-label">
                    Order Date
                </div>

                <div class="detail-value">
                    <%= order.getCreatedAt() %>
                </div>

            </div>

        </div>

    </div>

<%
        }

    } else {
%>

    <div class="empty">

        <h2>No Orders Found</h2>

        <p>
            You have not placed any orders yet.
        </p>

        <a
            class="shop-btn"
            href="products.jsp">
            Continue Shopping
        </a>

    </div>

<%
    }
%>

</div>

</body>
</html>