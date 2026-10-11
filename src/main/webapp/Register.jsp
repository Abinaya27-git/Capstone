<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html><html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AbinayaMart - Register</title><style>
    * {
        box-sizing: border-box;
    }

    body {
        margin: 0;
        font-family: Arial, sans-serif;
        background: linear-gradient(135deg, #ffe8f0, #fff5f8);
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        padding: 20px;
    }

    .register-box {
        background: white;
        width: 390px;
        max-width: 100%;
        padding: 28px;
        border-radius: 14px;
        box-shadow: 0 4px 18px #00000018;
    }

    h2 {
        text-align: center;
        color: #c2185b;
        margin-bottom: 22px;
    }

    label {
        display: block;
        margin-top: 14px;
        color: #333;
    }

    input, select {
        width: 100%;
        padding: 11px;
        margin-top: 6px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 15px;
    }

    input:focus, select:focus {
        outline: none;
        border-color: #c2185b;
    }

    button {
        width: 100%;
        padding: 12px;
        margin-top: 22px;
        background: #c2185b;
        color: white;
        border: none;
        border-radius: 6px;
        font-size: 16px;
        cursor: pointer;
    }

    button:hover {
        background: #a3154b;
    }

    .message {
        color: #b00020;
        text-align: center;
        overflow-wrap: anywhere;
    }

    .back {
        text-align: center;
        margin-top: 18px;
        font-size: 14px;
    }

    a {
        color: #c2185b;
    }
</style>

</head><body>
    <div class="register-box">
        <h2>Create Account</h2>    <% if (request.getAttribute("error") != null) { %>
        <p class="message">
            <%= request.getAttribute("error") %>
        </p>
    <% } %>

    <form action="${pageContext.request.contextPath}/register" method="post">

        <label for="name">Full Name</label>
        <input type="text"
               id="name"
               name="name"
               maxlength="100"
               required>

        <label for="email">Email</label>
        <input type="email"
               id="email"
               name="email"
               maxlength="150"
               required>

        <label for="password">Password</label>
        <input type="password"
               id="password"
               name="password"
               minlength="6"
               required>

        <label for="role">Account Type</label>
        <select id="role" name="role" required>
            <option value="">Select account type</option>
            <option value="BUYER">Buyer</option>
            <option value="SELLER">Seller</option>
        </select>

        <button type="submit">Register</button>
    </form>

    <div class="back">
        Already have an account?
        <a href="${pageContext.request.contextPath}/Index.jsp">Login</a>
    </div>
</div>

</body>
</html>