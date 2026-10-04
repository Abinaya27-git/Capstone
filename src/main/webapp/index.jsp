<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Abinaya Mart - Login</title>

    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #ffe8f0, #fff5f8);
            color: #333;
            min-height: 100vh;
        }

        header {
            height: 80px;
            background: white;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0 50px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        .logo {
            font-size: 30px;
            font-weight: bold;
            color: #c2185b;
        }

        nav a {
            margin: 0 18px;
            text-decoration: none;
            color: #333;
            font-size: 16px;
        }

        nav a:hover {
            color: #c2185b;
        }

        .container {
            min-height: calc(100vh - 80px);
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 45px 8%;
            gap: 50px;
        }

        .welcome {
            width: 50%;
        }

        .welcome h4 {
            font-size: 30px;
            font-weight: normal;
            margin-bottom: 10px;
        }

        .welcome h1 {
            font-size: 58px;
            color: #c2185b;
            margin-bottom: 15px;
        }

        .welcome p {
            font-size: 23px;
            margin-bottom: 35px;
            color: #444;
        }

        .features {
            display: flex;
            gap: 25px;
            margin-top: 30px;
        }

        .feature {
            text-align: center;
            width: 120px;
        }

        .feature .icon {
            font-size: 32px;
            margin-bottom: 8px;
        }

        .feature span {
            font-size: 14px;
            font-weight: bold;
        }

        .shopping {
            font-size: 70px;
            margin-top: 35px;
        }

        .login-card {
            width: 430px;
            background: white;
            padding: 40px;
            border-radius: 22px;
            box-shadow: 0 10px 35px rgba(0,0,0,0.12);
        }

        .login-card h2 {
            text-align: center;
            color: #c2185b;
            font-size: 32px;
            margin-bottom: 8px;
        }

        .login-card h3 {
            text-align: center;
            color: #263b5b;
            font-size: 23px;
            margin-bottom: 8px;
        }

        .welcome-text {
            text-align: center;
            color: #777;
            margin-bottom: 25px;
        }

        input {
            width: 100%;
            padding: 14px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 10px;
            font-size: 16px;
        }

        .login-btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 10px;
            background: #c2185b;
            color: white;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
        }

        .login-btn:hover {
            background: #a3154d;
        }

        .demo-title {
            text-align: center;
            margin: 25px 0 12px;
            color: #555;
            font-weight: bold;
        }

        .demo-container {
            display: flex;
            gap: 8px;
        }

        .demo-btn {
            flex: 1;
            padding: 10px 5px;
            border: 1px solid #e0b2c5;
            background: #fff5f8;
            color: #c2185b;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        .demo-btn:hover {
            background: #f8d8e5;
        }

        .register {
            text-align: center;
            margin-top: 22px;
        }

        .register a {
            color: #c2185b;
            font-weight: bold;
            text-decoration: none;
        }

        @media (max-width: 900px) {
            .container {
                flex-direction: column;
            }

            .welcome {
                width: 100%;
                text-align: center;
            }

            .login-card {
                width: 100%;
                max-width: 430px;
            }

            .features {
                justify-content: center;
            }

            .welcome h1 {
                font-size: 42px;
            }
        }
    </style>
</head>

<body>

<header>
    <div class="logo">🛍️ Abinaya Mart</div>

    <nav>
        <a href="#">Home</a>
        <a href="#">Shop</a>
        <a href="#">Offers</a>
        <a href="#">About Us</a>
        <a href="#">Contact</a>
    </nav>

    <div>
        👤 Login &nbsp;&nbsp; | &nbsp;&nbsp; 👤 Register
    </div>
</header>

<div class="container">

    <div class="welcome">
        <h4>Welcome to</h4>

        <h1>ABINAYA MART</h1>

        <p>Your One Stop Shop for Everything</p>

        <div class="features">

            <div class="feature">
                <div class="icon">🚚</div>
                <span>Fast<br>Delivery</span>
            </div>

            <div class="feature">
                <div class="icon">🛡️</div>
                <span>Safe &<br>Secure</span>
            </div>

            <div class="feature">
                <div class="icon">🏷️</div>
                <span>Best<br>Prices</span>
            </div>

            <div class="feature">
                <div class="icon">🛒</div>
                <span>Wide<br>Range</span>
            </div>

        </div>

        <div class="shopping">🛍️ 🛍️</div>
    </div>

    <div class="login-card">

        <h2>🛍️ Abinaya Mart</h2>

        <h3>Login to Your Account</h3>

        <p class="welcome-text">
            Welcome back! Please enter your details.
        </p>

        <form action="login" method="post">

            <input
                type="email"
                id="email"
                name="email"
                placeholder="Email Address"
                required>

            <input
                type="password"
                id="password"
                name="password"
                placeholder="Password"
                required>

            <button class="login-btn" type="submit">
                Login
            </button>

        </form>

        <div class="demo-title">
            Demo Accounts
        </div>

        <div class="demo-container">

            <button type="button"
                    class="demo-btn"
                    onclick="setDemo('buyer@abinayamart','buyer123')">
                🛍️ Buyer
            </button>

            <button type="button"
                    class="demo-btn"
                    onclick="setDemo('seller@abinayamart','seller123')">
                🏪 Seller
            </button>

            <button type="button"
                    class="demo-btn"
                    onclick="setDemo('admin@abinayamart','admin123')">
                👑 Admin
            </button>

        </div>

        <div class="register">
            Don't have an account?
            <a href="#">Register</a>
        </div>

    </div>

</div>

<script>
    function setDemo(email, password) {
        document.getElementById("email").value = email;
        document.getElementById("password").value = password;
    }
</script>

</body>
</html>