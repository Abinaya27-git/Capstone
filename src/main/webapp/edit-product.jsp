<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.abinayamart.model.Product" %>

<%
    Product product =
            (Product) request.getAttribute("product");

    if (product == null) {
        response.sendRedirect(
                request.getContextPath() + "/seller"
        );
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Edit Product - AbinayaMart</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
        }

        .container {
            width: 450px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 2px 10px #ccc;
        }

        h2 {
            text-align: center;
            color: #222;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input,
        textarea,
        select {
            width: 100%;
            padding: 10px;
            margin-top: 6px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            width: 100%;
            margin-top: 20px;
            padding: 12px;
            background: #222;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background: #444;
        }

        .back {
            display: block;
            text-align: center;
            margin-top: 15px;
            text-decoration: none;
            color: #555;
        }

    </style>

</head>

<body>

<div class="container">

    <h2>Edit Product</h2>

    <form action="<%= request.getContextPath() %>/edit-product"
          method="post">

        <input
            type="hidden"
            name="id"
            value="<%= product.getId() %>"
        >

        <label>Product Name</label>

        <input
            type="text"
            name="name"
            value="<%= product.getName() %>"
            required
        >


        <label>Description</label>

        <textarea
            name="description"
            rows="4"
        ><%= product.getDescription() %></textarea>


        <label>Category</label>

        <select name="category" required>

            <option value="">Select Category</option>

            <option value="Fashion"
                <%= "Fashion".equals(product.getCategory())
                        ? "selected" : "" %>>
                Fashion
            </option>

            <option value="Footwear"
                <%= "Footwear".equals(product.getCategory())
                        ? "selected" : "" %>>
                Footwear
            </option>

            <option value="Electronics"
                <%= "Electronics".equals(product.getCategory())
                        ? "selected" : "" %>>
                Electronics
            </option>

            <option value="Home & Kitchen"
                <%= "Home & Kitchen".equals(product.getCategory())
                        ? "selected" : "" %>>
                Home & Kitchen
            </option>

            <option value="Books"
                <%= "Books".equals(product.getCategory())
                        ? "selected" : "" %>>
                Books
            </option>

        </select>


        <label>Price</label>

        <input
            type="number"
            name="price"
            step="0.01"
            min="0"
            value="<%= product.getPrice() %>"
            required
        >


        <label>Stock</label>

        <input
            type="number"
            name="stock"
            min="0"
            value="<%= product.getStock() %>"
            required
        >


        <label>Image URL</label>

        <input
            type="text"
            name="imageUrl"
            value="<%= product.getImageUrl() == null
                    ? "" : product.getImageUrl() %>"
            placeholder="Paste product image URL"
        >


        <button type="submit">
            Update Product
        </button>

    </form>


    <a class="back"
       href="<%= request.getContextPath() %>/seller">
        ← Back to Seller Dashboard
    </a>

</div>

</body>
</html>