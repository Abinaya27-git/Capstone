<!DOCTYPE html>
<html>
<head>
    <title>Add Product - AbinayaMart</title>

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

        .note {
            font-size: 12px;
            color: #777;
            margin-top: 5px;
        }
    </style>
</head>

<body>

<div class="container">

    <h2>Add New Product</h2>

    <form action="add-product" method="post">

        <label>Product Name</label>
        <input type="text" name="name" required>

        <label>Description</label>
        <textarea name="description" rows="4"></textarea>

        <label>Category</label>
        <select name="category" required>
            <option value="">Select Category</option>
            <option value="Fashion">Fashion</option>
            <option value="Footwear">Footwear</option>
            <option value="Electronics">Electronics</option>
            <option value="Home & Kitchen">Home & Kitchen</option>
            <option value="Books">Books</option>
        </select>

        <label>Price</label>
        <input
            type="number"
            name="price"
            step="0.01"
            min="0"
            required
        >

        <label>Stock</label>
        <input
            type="number"
            name="stock"
            min="0"
            required
        >

        <label>Image URL</label>
        <input
            type="text"
            name="imageUrl"
            placeholder="Paste product image URL"
        >

        <div class="note">
            Example: https://example.com/product.jpg
        </div>

        <button type="submit">
            Add Product
        </button>

    </form>

</div>

</body>
</html>