<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.abinayamart.model.CartItem" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>AbinayaMart - Cart</title>

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

        .cart-item {
            background: white;
            padding: 20px;
            margin-bottom: 15px;
            border-radius: 12px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.08);
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .product-image {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 10px;
        }

        .details {
            flex: 1;
        }

        .details h3 {
            margin-top: 0;
        }

        .price {
            color: #6c3df5;
            font-weight: bold;
            font-size: 18px;
        }

        .subtotal {
            font-weight: bold;
            margin-top: 8px;
        }

        .remove-btn {
            background: #d32f2f;
            color: white;
            border: none;
            padding: 9px 14px;
            border-radius: 7px;
            cursor: pointer;
        }

        .remove-btn:hover {
            background: #b71c1c;
        }

        .summary {
            background: white;
            padding: 25px;
            margin-top: 25px;
            border-radius: 12px;
            box-shadow: 0 3px 12px rgba(0,0,0,0.08);
            text-align: right;
        }

        .total {
            font-size: 25px;
            font-weight: bold;
            color: #6c3df5;
            margin-bottom: 20px;
        }

        .checkout-section {
            margin-bottom: 20px;
            text-align: left;
        }

        .checkout-section label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
        }

        .payment-select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 7px;
            font-size: 15px;
            background: white;
        }

        .checkout-btn {
            background: #2e7d32;
            color: white;
            border: none;
            padding: 12px 22px;
            border-radius: 7px;
            cursor: pointer;
            font-weight: bold;
            margin-right: 10px;
        }

        .checkout-btn:hover {
            background: #1b5e20;
        }

        .clear-btn {
            background: #555;
            color: white;
            border: none;
            padding: 12px 18px;
            border-radius: 7px;
            cursor: pointer;
        }

        .clear-btn:hover {
            background: #333;
        }

        .empty {
            background: white;
            padding: 40px;
            text-align: center;
            border-radius: 12px;
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
    </style>
</head>

<body>

<header>

    <div class="logo">🛍 ABINAYA MART</div>

    <nav>
        <a href="products.jsp">Home</a>
        <a href="products.jsp">Products</a>
        <a href="cart">Cart 🛒</a>
        <a href="index.jsp">Logout</a>
    </nav>

</header>


<div class="container">

    <h1>Shopping Cart 🛒</h1>

<%
    List<CartItem> cartItems =
            (List<CartItem>) request.getAttribute("cartItems");

    Double cartTotal =
            (Double) request.getAttribute("cartTotal");

    if (cartItems != null && !cartItems.isEmpty()) {

        for (CartItem item : cartItems) {
%>

    <div class="cart-item">

        <%
            String imageUrl =
                    item.getProduct().getImageUrl();

            if (imageUrl != null && !imageUrl.isBlank()) {
        %>

            <img
                class="product-image"
                src="<%= imageUrl %>"
                alt="<%= item.getProduct().getName() %>"
            >

        <%
            } else {
        %>

            <div
                class="product-image"
                style="display:flex;align-items:center;justify-content:center;background:#eee;">
                No Image
            </div>

        <%
            }
        %>


        <div class="details">

            <h3>
                <%= item.getProduct().getName() %>
            </h3>

            <p>
                Category:
                <%= item.getProduct().getCategory() %>
            </p>

            <p class="price">
                ₹<%= item.getProduct().getPrice() %>
            </p>

            <p>
                Quantity:
                <%= item.getQuantity() %>
            </p>

            <p class="subtotal">
                Subtotal:
                ₹<%= item.getSubtotal() %>
            </p>

        </div>


        <form method="post" action="cart">

            <input
                type="hidden"
                name="action"
                value="remove"
            >

            <input
                type="hidden"
                name="productId"
                value="<%= item.getProduct().getId() %>"
            >

            <button
                type="submit"
                class="remove-btn">
                Remove
            </button>

        </form>

    </div>

<%
        }
%>


    <div class="summary">

        <div class="total">
            Total: ₹<%= cartTotal %>
        </div>


        <div class="checkout-section">

            <form
                method="post"
                action="checkout"
                onsubmit="return confirm('Place this order?');">

                <label for="paymentMode">
                    Payment Method
                </label>

                <select
                    id="paymentMode"
                    name="paymentMode"
                    class="payment-select">

                    <option value="COD">
                        Cash on Delivery (COD)
                    </option>

                </select>

                <br><br>

                <button
                    type="submit"
                    class="checkout-btn">
                    Place Order
                </button>

            </form>

        </div>


        <form method="post" action="cart">

            <input
                type="hidden"
                name="action"
                value="clear"
            >

            <button
                type="submit"
                class="clear-btn">
                Clear Cart
            </button>

        </form>

    </div>


<%
    } else {
%>


    <div class="empty">

        <h2>Your cart is empty</h2>

        <p>
            Add some products to your cart.
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