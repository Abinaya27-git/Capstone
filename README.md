# AbinayaMart

**Developer:** Abinaya
**College:** J.J College of Engineering and Technology

AbinayaMart is a console-based E-Commerce mini project in Java (MySQL + JDBC).
It supports Buyer, Seller and Admin login, product management, search/filter,
cart & checkout, mock payment, orders, 1–5 star reviews and a simple product chatbot.

## Project Structure

```
AbinayaMart/
├── pom.xml
├── README.md
├── .gitignore
├── run.bat
├── run.sh
├── database/
│   └── abinayamart.sql
└── src/main/java/com/abinayamart/
    ├── Main.java
    ├── config/DBConnection.java
    ├── exception/AbinayaMartException.java
    ├── model/Role.java
    ├── model/User.java
    ├── model/Buyer.java
    ├── model/Seller.java
    ├── model/Admin.java
    ├── model/Product.java
    ├── model/CartItem.java
    ├── model/Order.java
    ├── model/OrderStatus.java
    ├── model/OrderItem.java
    ├── model/Review.java
    ├── dao/UserDAO.java
    ├── dao/ProductDAO.java
    ├── dao/OrderDAO.java
    ├── dao/ReviewDAO.java
    ├── service/AuthService.java
    ├── service/ProductService.java
    ├── service/CartService.java
    ├── service/OrderService.java
    ├── service/ReviewService.java
    ├── service/ChatbotService.java
    ├── service/payment/PaymentMethod.java
    ├── service/payment/UPIPayment.java
    ├── service/payment/CardPayment.java
    ├── service/payment/CODPayment.java
    ├── service/payment/PaymentService.java
    ├── ui/ConsoleUI.java
    └── util/InputUtil.java
```

## Requirements

- Java 17+ (tested on Java 21)
- MySQL 8+
- MySQL Connector/J 8.x
- VS Code with "Extension Pack for Java"

## 1. Database Setup

```sql
-- Create database and tables
SOURCE database/abinayamart.sql;
```

Or from MySQL CLI:

```bash
mysql -u root -p < database/abinayamart.sql
```

Then edit DB credentials in:
`src/main/java/com/abinayamart/config/DBConnection.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/abinayamart?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "root";
```

Default seeded logins (password is as shown):

| Role   | Email              | Password   |
|--------|--------------------|------------|
| Admin  | admin@abinayamart  | admin123   |
| Seller | seller@abinayamart | seller123  |
| Buyer  | buyer@abinayamart  | buyer123   |

## 2a. Run with Maven (recommended)

```bash
cd AbinayaMart
mvn compile
mvn exec:java
```

## 2b. Run without Maven (VS Code / javac)

1. Download `mysql-connector-j-8.x.jar` from https://dev.mysql.com/downloads/connector/j/
2. Put it in `AbinayaMart/lib/`
3. Compile & run:

Windows (`run.bat`):
```bat
run.bat
```

Linux/Mac (`run.sh`):
```bash
chmod +x run.sh
./run.sh
```

Manual compile:
```bash
javac -cp "lib/*" -d out $(find src -name "*.java")
java -cp "out;lib/*" com.abinayamart.Main
```

## Features

- Buyer / Seller / Admin registration & login
- Seller: add / edit / delete products, view orders of own products, update stock
- Buyer: search products, filter by category, cart, checkout, mock payment, orders, reviews
- Admin: view all users/products/orders, delete products, update order status
- Reviews with 1–5 star rating + average rating display
- Rule-based product chatbot (type `chat` in buyer menu)
- Proper OOP: inheritance (Buyer/Seller/Admin), encapsulation, DAO + Service layers,
  polymorphism (PaymentMethod), exception handling (AbinayaMartException)
# Capstone
