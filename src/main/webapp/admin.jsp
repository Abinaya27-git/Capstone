<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.abinayamart.config.DBConnection" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>AbinayaMart - Admin Dashboard</title>

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

        .header h1 {
            margin: 0 0 15px 0;
        }

        .nav a {
            color: white;
            text-decoration: none;
            margin: 0 12px;
        }

        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
        }

        .cards {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-bottom: 30px;
        }

        .card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ccc;
            text-align: center;
        }

        .card h2 {
            margin: 0 0 15px 0;
            font-size: 20px;
        }

        .number {
            font-size: 32px;
            font-weight: bold;
        }

        .section {
            background: white;
            padding: 20px;
            margin-bottom: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px #ccc;
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #222;
            color: white;
            padding: 12px;
            text-align: left;
            white-space: nowrap;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        .admin {
            color: purple;
            font-weight: bold;
        }

        .seller {
            color: darkorange;
            font-weight: bold;
        }

        .buyer {
            color: #1976d2;
            font-weight: bold;
        }

        .status {
            color: green;
            font-weight: bold;
        }

        .delete-button {
            background: #d32f2f;
            color: white;
            border: none;
            padding: 8px 14px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        .delete-button:hover {
            background: #b71c1c;
        }

        .message {
            background: #e8f5e9;
            color: #2e7d32;
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 6px;
            font-weight: bold;
        }

        .error {
            color: red;
            font-weight: bold;
        }

        .product-name {
            font-weight: bold;
        }

        .price {
            color: green;
            font-weight: bold;
        }

        @media (max-width: 800px) {
            .cards {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>

<div class="header">

    <h1>AbinayaMart - Admin Dashboard</h1>

    <div class="nav">
        <a href="index.jsp">Home</a>
        <a href="products.jsp">Products</a>
        <a href="admin.jsp">Admin Dashboard</a>
        <a href="index.jsp">Logout</a>
    </div>

</div>

<div class="container">

<%
    String deleteMessage = null;
    String deleteError = null;

    String deleteId = request.getParameter("deleteId");

    if (deleteId != null && !deleteId.isBlank()) {

        try {
            int productId = Integer.parseInt(deleteId);

            try (Connection connection = DBConnection.getConnection()) {

                String deleteSql =
                        "DELETE FROM products WHERE id = ?";

                try (PreparedStatement ps =
                             connection.prepareStatement(deleteSql)) {

                    ps.setInt(1, productId);

                    int rowsDeleted = ps.executeUpdate();

                    if (rowsDeleted > 0) {
                        deleteMessage =
                                "Product deleted successfully.";
                    } else {
                        deleteError =
                                "Product not found.";
                    }
                }
            }

        } catch (NumberFormatException e) {

            deleteError = "Invalid product ID.";

        } catch (Exception e) {

            deleteError =
                    "Unable to delete product. It may be used in existing orders or reviews.";
        }
    }
%>

<%
    if (deleteMessage != null) {
%>

    <div class="message">
        <%= deleteMessage %>
    </div>

<%
    }

    if (deleteError != null) {
%>

    <div class="section">
        <p class="error">
            <%= deleteError %>
        </p>
    </div>

<%
    }
%>

<%
    int totalUsers = 0;
    int totalSellers = 0;
    int totalBuyers = 0;
    int totalProducts = 0;
    int totalOrders = 0;

    try (Connection connection = DBConnection.getConnection()) {

        String sqlUsers = "SELECT COUNT(*) FROM users";

        try (PreparedStatement ps =
                     connection.prepareStatement(sqlUsers);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalUsers = rs.getInt(1);
            }
        }

        String sqlSellers =
                "SELECT COUNT(*) FROM users WHERE role = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sqlSellers)) {

            ps.setString(1, "SELLER");

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    totalSellers = rs.getInt(1);
                }
            }
        }

        String sqlBuyers =
                "SELECT COUNT(*) FROM users WHERE role = ?";

        try (PreparedStatement ps =
                     connection.prepareStatement(sqlBuyers)) {

            ps.setString(1, "BUYER");

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    totalBuyers = rs.getInt(1);
                }
            }
        }

        String sqlProducts =
                "SELECT COUNT(*) FROM products";

        try (PreparedStatement ps =
                     connection.prepareStatement(sqlProducts);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalProducts = rs.getInt(1);
            }
        }

        String sqlOrders =
                "SELECT COUNT(*) FROM orders";

        try (PreparedStatement ps =
                     connection.prepareStatement(sqlOrders);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                totalOrders = rs.getInt(1);
            }
        }

    } catch (Exception e) {
%>

    <div class="section">

        <h2>Database Error</h2>

        <p class="error">
            Unable to load admin dashboard.
        </p>

    </div>

<%
    }
%>

    <div class="cards">

        <div class="card">

            <h2>Total Users</h2>

            <div class="number">
                <%= totalUsers %>
            </div>

        </div>

        <div class="card">

            <h2>Total Sellers</h2>

            <div class="number">
                <%= totalSellers %>
            </div>

        </div>

        <div class="card">

            <h2>Total Buyers</h2>

            <div class="number">
                <%= totalBuyers %>
            </div>

        </div>

        <div class="card">

            <h2>Total Products</h2>

            <div class="number">
                <%= totalProducts %>
            </div>

        </div>

        <div class="card">

            <h2>Total Orders</h2>

            <div class="number">
                <%= totalOrders %>
            </div>

        </div>

    </div>

    <div class="section">

        <h2>User Details</h2>

        <table>

            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Role</th>
            </tr>

<%
    try (Connection connection = DBConnection.getConnection()) {

        String sql =
                "SELECT id, email, role FROM users ORDER BY id";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String email = rs.getString("email");
                String role = rs.getString("role");

                String roleClass = "";

                if (role != null) {
                    roleClass = role.toLowerCase();
                }
%>

            <tr>

                <td>
                    <%= id %>
                </td>

                <td>
                    <%= email %>
                </td>

                <td class="<%= roleClass %>">
                    <%= role %>
                </td>

            </tr>

<%
            }
        }

    } catch (Exception e) {
%>

            <tr>

                <td colspan="3" class="error">
                    Unable to load user details.
                </td>

            </tr>

<%
    }
%>

        </table>

    </div>

    <div class="section">

        <h2>Product Listings</h2>

        <table>

            <tr>
                <th>ID</th>
                <th>Product Name</th>
                <th>Category</th>
                <th>Price</th>
                <th>Stock</th>
                <th>Seller ID</th>
                <th>Action</th>
            </tr>

<%
    try (Connection connection = DBConnection.getConnection()) {

        String sql =
                "SELECT id, seller_id, name, category, price, stock " +
                "FROM products ORDER BY id DESC";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int productId = rs.getInt("id");
                int sellerId = rs.getInt("seller_id");
                String productName = rs.getString("name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
%>

            <tr>

                <td>
                    <%= productId %>
                </td>

                <td class="product-name">
                    <%= productName %>
                </td>

                <td>
                    <%= category %>
                </td>

                <td class="price">
                    ₹<%= price %>
                </td>

                <td>
                    <%= stock %>
                </td>

                <td>
                    <%= sellerId %>
                </td>

                <td>

                    <form
                        method="get"
                        action="admin.jsp"
                        onsubmit="return confirm('Are you sure you want to delete this product?');"
                    >

                        <input
                            type="hidden"
                            name="deleteId"
                            value="<%= productId %>"
                        >

                        <button
                            type="submit"
                            class="delete-button"
                        >
                            Delete
                        </button>

                    </form>

                </td>

            </tr>

<%
            }
        }

    } catch (Exception e) {
%>

            <tr>

                <td colspan="7" class="error">
                    Unable to load product listings.
                </td>

            </tr>

<%
    }
%>

        </table>

    </div>

    <div class="section">

        <h2>All Orders</h2>

        <table>

            <tr>
                <th>Order ID</th>
                <th>Buyer Email</th>
                <th>Total Amount</th>
                <th>Status</th>
                <th>Payment Mode</th>
                <th>Created At</th>
            </tr>

<%
    try (Connection connection = DBConnection.getConnection()) {

        String sql =
                "SELECT o.id, u.email, o.total_amount, " +
                "o.status, o.payment_mode, o.created_at " +
                "FROM orders o " +
                "LEFT JOIN users u ON o.buyer_id = u.id " +
                "ORDER BY o.id DESC";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int orderId = rs.getInt("id");
                String buyerEmail = rs.getString("email");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                String paymentMode = rs.getString("payment_mode");
                String createdAt = rs.getString("created_at");
%>

            <tr>

                <td>
                    <%= orderId %>
                </td>

                <td>
                    <%= buyerEmail %>
                </td>

                <td>
                    ₹<%= totalAmount %>
                </td>

                <td class="status">
                    <%= status %>
                </td>

                <td>
                    <%= paymentMode %>
                </td>

                <td>
                    <%= createdAt %>
                </td>

            </tr>

<%
            }
        }

    } catch (Exception e) {
%>

            <tr>

                <td colspan="6" class="error">
                    Unable to load order details.
                </td>

            </tr>

<%
    }
%>

        </table>

    </div>

</div>

</body>
</html>