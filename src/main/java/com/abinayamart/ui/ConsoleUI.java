package com.abinayamart.ui;

import com.abinayamart.dao.UserDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.CartItem;
import com.abinayamart.model.Order;
import com.abinayamart.model.OrderItem;
import com.abinayamart.model.Product;
import com.abinayamart.model.Review;
import com.abinayamart.model.Role;
import com.abinayamart.model.User;
import com.abinayamart.service.AuthService;
import com.abinayamart.service.CartService;
import com.abinayamart.service.ChatbotService;
import com.abinayamart.service.OrderService;
import com.abinayamart.service.ProductService;
import com.abinayamart.service.ReviewService;
import com.abinayamart.service.payment.PaymentMethod;
import com.abinayamart.service.payment.PaymentService;
import com.abinayamart.util.InputUtil;

import java.util.List;
import java.util.Scanner;

/** Console (terminal) user interface for AbinayaMart. */
public class ConsoleUI {

    private final Scanner sc = new Scanner(System.in);
    private final AuthService authService = new AuthService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final ReviewService reviewService = new ReviewService();
    private final PaymentService paymentService = new PaymentService();
    private final ChatbotService chatbot = new ChatbotService(productService);
    private final UserDAO userDAO = new UserDAO();

    public void start() {
        printHeader();

        while (true) {
            System.out.println("\n==== AbinayaMart Main Menu ====");
            System.out.println("1. Login");
            System.out.println("2. Register (Buyer / Seller)");
            System.out.println("0. Exit");

            int ch = InputUtil.readInt(sc, "Choice: ");

            try {
                if (ch == 1) {
                    loginFlow();
                } else if (ch == 2) {
                    registerFlow();
                } else if (ch == 0) {
                    System.out.println(
                            "Thank you for shopping with AbinayaMart!");
                    return;
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (AbinayaMartException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(
                        "Unexpected error: " + e.getMessage());
            }
        }
    }

    private void printHeader() {
        System.out.println("===============================================");
        System.out.println("  AbinayaMart - E-Commerce Java Project");
        System.out.println(
                "  Developer: Abinaya | J.J College of Engineering and Technology");
        System.out.println("===============================================");
    }

    // ---------- Auth ----------

    private void loginFlow() throws AbinayaMartException {
        String email = InputUtil.readLine(sc, "Email: ");
        String password = InputUtil.readLine(sc, "Password: ");

        User user = authService.login(email, password);

        System.out.println(
                "Welcome, " + user.getName() + " (" + user.getRole() + ")!");

        if (user.getRole() == Role.BUYER) {
            buyerMenu(user);
        } else if (user.getRole() == Role.SELLER) {
            sellerMenu(user);
        } else {
            adminMenu(user);
        }
    }

    private void registerFlow() throws AbinayaMartException {
        String name = InputUtil.readLine(sc, "Name: ");
        String email = InputUtil.readLine(sc, "Email: ");
        String password = InputUtil.readLine(
                sc, "Password (min 4 chars): ");

        System.out.println("Role: 1. Buyer  2. Seller");

        int r = InputUtil.readInt(sc, "Choose role: ");

        Role role = (r == 2) ? Role.SELLER : Role.BUYER;

        User user = authService.register(
                name, email, password, role);

        System.out.println(
                "Registered successfully! Your user id is "
                        + user.getId()
                        + ". Please login.");
    }

    // ---------- Buyer ----------

    private void buyerMenu(User user) {
        CartService cart = new CartService();

        while (true) {
            System.out.println("\n---- AbinayaMart Buyer Menu ----");
            System.out.println("1. Browse all products");
            System.out.println("2. Search products");
            System.out.println("3. Filter by category");
            System.out.println("4. View product + reviews");
            System.out.println("5. Add to cart");
            System.out.println("6. View cart");
            System.out.println("7. Checkout (mock payment)");
            System.out.println("8. My orders");
            System.out.println("9. Add review (1-5 stars)");
            System.out.println("10. Chat with AbinayaMart assistant");
            System.out.println("0. Logout");

            int ch = InputUtil.readInt(sc, "Choice: ");

            try {
                switch (ch) {
                    case 1:
                        printProducts(productService.all());
                        break;

                    case 2: {
                        String kw = InputUtil.readLine(
                                sc, "Search keyword: ");
                        printProducts(
                                productService.search(kw, null));
                        break;
                    }

                    case 3: {
                        List<String> cats =
                                productService.categories();

                        System.out.println(
                                "Categories: "
                                        + (cats.isEmpty()
                                        ? "(none)"
                                        : String.join(", ", cats)));

                        String cat = InputUtil.readLine(
                                sc, "Enter category: ");

                        printProducts(
                                productService.search(null, cat));
                        break;
                    }

                    case 4:
                        viewProductDetail();
                        break;

                    case 5: {
                        int id = InputUtil.readInt(
                                sc, "Product id: ");

                        int qty = InputUtil.readInt(
                                sc, "Quantity: ");

                        Product p = productService.getById(id);

                        cart.add(p, qty);

                        System.out.println(
                                "Added to cart: "
                                        + p.getName()
                                        + " x "
                                        + qty);
                        break;
                    }

                    case 6:
                        printCart(cart);
                        break;

                    case 7:
                        checkoutFlow(user, cart);
                        break;

                    case 8:
                        printOrders(
                                orderService.myOrders(user.getId()));
                        break;

                    case 9: {
                        int pid = InputUtil.readInt(
                                sc, "Product id: ");

                        int rating = InputUtil.readIntInRange(
                                sc,
                                "Rating (1-5): ",
                                1,
                                5);

                        String comment = InputUtil.readLine(
                                sc, "Comment: ");

                        reviewService.addReview(
                                pid,
                                user.getId(),
                                rating,
                                comment);

                        System.out.println(
                                "Review saved. Thank you!");
                        break;
                    }

                    case 10:
                        chatbotLoop();
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (AbinayaMartException e) {
                System.out.println(
                        "Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(
                        "Unexpected error: " + e.getMessage());
            }
        }
    }

    private void viewProductDetail()
            throws AbinayaMartException {

        int id = InputUtil.readInt(
                sc, "Product id: ");

        Product p = productService.getById(id);

        if (p == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\n" + p.toString());
        System.out.println(
                "Description: " + p.getDescription());

        List<Review> reviews =
                reviewService.forProduct(id);

        if (reviews.isEmpty()) {
            System.out.println("No reviews yet.");
        } else {
            System.out.println("--- Reviews ---");

            for (Review r : reviews) {
                System.out.println(
                        " * " + r.toString());
            }
        }
    }

    private void printCart(CartService cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("--- Your Cart ---");

        for (CartItem ci : cart.items()) {
            System.out.printf(
                    " #%d %s x %d = Rs.%.2f%n",
                    ci.getProduct().getId(),
                    ci.getProduct().getName(),
                    ci.getQuantity(),
                    ci.getSubtotal());
        }

        System.out.printf(
                "Total: Rs.%.2f%n",
                cart.total());
    }

    private void checkoutFlow(
            User user,
            CartService cart)
            throws AbinayaMartException {

        if (cart.isEmpty()) {
            System.out.println(
                    "Cart is empty. Add products first.");
            return;
        }

        printCart(cart);

        String mode = InputUtil.readLine(
                sc,
                "Payment mode (UPI / CARD / COD): ");

        String detail = "";

        if (mode.equalsIgnoreCase("UPI")) {
            detail = InputUtil.readLine(
                    sc,
                    "Enter UPI id (e.g. abinaya@okbank): ");

        } else if (mode.equalsIgnoreCase("CARD")) {
            detail = InputUtil.readLine(
                    sc,
                    "Enter card number (12-19 digits): ");
        }

        PaymentMethod pm =
                paymentService.method(mode, detail);

        pm.pay(cart.total());

        Order order = orderService.checkout(
                user.getId(),
                cart.items(),
                pm.name());

        cart.clear();

        System.out.println(
                "Order placed successfully! Order id = "
                        + order.getId());
    }

    private void chatbotLoop() {
        System.out.println(
                "\n--- AbinayaMart Assistant "
                        + "(type 'exit' to leave chat) ---");

        while (true) {
            System.out.print("You: ");

            String line = sc.nextLine();

            if (line.trim().equalsIgnoreCase("exit")) {
                return;
            }

            System.out.println(
                    "Bot: " + chatbot.reply(line));
        }
    }

    // ---------- Seller ----------

    private void sellerMenu(User user) {

        while (true) {
            System.out.println(
                    "\n---- AbinayaMart Seller Menu ("
                            + user.getName() + ") ----");

            System.out.println("1. My products");
            System.out.println("2. Add product");
            System.out.println("3. Edit product");
            System.out.println("4. Delete product");
            System.out.println(
                    "5. Orders containing my products");
            System.out.println("0. Logout");

            int ch = InputUtil.readInt(
                    sc, "Choice: ");

            try {
                switch (ch) {

                    case 1:
                        printProducts(
                                productService.bySeller(
                                        user.getId()));
                        break;

                    case 2: {
                        String name = InputUtil.readLine(
                                sc, "Name: ");

                        String desc = InputUtil.readLine(
                                sc, "Description: ");

                        String cat = InputUtil.readLine(
                                sc, "Category: ");

                        double price = InputUtil.readDouble(
                                sc, "Price: ");

                        int stock = InputUtil.readInt(
                                sc, "Stock: ");

                        // NEW: Image URL
                        String imageUrl = InputUtil.readLine(
                                sc, "Image URL: ");

                        Product p =
                                productService.addProduct(
                                        user.getId(),
                                        name,
                                        desc,
                                        cat,
                                        price,
                                        stock,
                                        imageUrl);

                        System.out.println(
                                "Product added with id "
                                        + p.getId());
                        break;
                    }

                    case 3: {
                        int id = InputUtil.readInt(
                                sc,
                                "Product id to edit: ");

                        Product p =
                                productService.getById(id);

                        if (p == null
                                || p.getSellerId()
                                != user.getId()) {

                            System.out.println(
                                    "Product not found in your catalogue.");
                            break;
                        }

                        System.out.println(
                                "Leave blank to keep current value.");

                        String name = InputUtil.readLine(
                                sc,
                                "Name [" + p.getName() + "]: ");

                        String desc = InputUtil.readLine(
                                sc,
                                "Description ["
                                        + p.getDescription()
                                        + "]: ");

                        String cat = InputUtil.readLine(
                                sc,
                                "Category ["
                                        + p.getCategory()
                                        + "]: ");

                        String priceS = InputUtil.readLine(
                                sc,
                                "Price ["
                                        + p.getPrice()
                                        + "]: ");

                        String stockS = InputUtil.readLine(
                                sc,
                                "Stock ["
                                        + p.getStock()
                                        + "]: ");

                        String imageUrl = InputUtil.readLine(
                                sc,
                                "Image URL ["
                                        + (p.getImageUrl() == null
                                        ? ""
                                        : p.getImageUrl())
                                        + "]: ");

                        if (!name.isEmpty()) {
                            p.setName(name);
                        }

                        if (!desc.isEmpty()) {
                            p.setDescription(desc);
                        }

                        if (!cat.isEmpty()) {
                            p.setCategory(cat);
                        }

                        if (!priceS.isEmpty()) {
                            p.setPrice(
                                    Double.parseDouble(priceS));
                        }

                        if (!stockS.isEmpty()) {
                            p.setStock(
                                    Integer.parseInt(stockS));
                        }

                        if (!imageUrl.isEmpty()) {
                            p.setImageUrl(imageUrl);
                        }

                        productService.editProduct(p);

                        System.out.println(
                                "Product updated.");
                        break;
                    }

                    case 4: {
                        int id = InputUtil.readInt(
                                sc,
                                "Product id to delete: ");

                        Product p =
                                productService.getById(id);

                        if (p == null
                                || p.getSellerId()
                                != user.getId()) {

                            System.out.println(
                                    "Product not found in your catalogue.");
                            break;
                        }

                        productService.deleteProduct(id);

                        System.out.println(
                                "Product deleted.");
                        break;
                    }

                    case 5:
                        printOrders(
                                orderService.sellerOrders(
                                        user.getId()));
                        break;

                    case 0:
                        return;

                    default:
                        System.out.println(
                                "Invalid choice.");
                }

            } catch (AbinayaMartException e) {
                System.out.println(
                        "Error: " + e.getMessage());

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid number format.");

            } catch (Exception e) {
                System.out.println(
                        "Unexpected error: "
                                + e.getMessage());
            }
        }
    }

    // ---------- Admin ----------

    private void adminMenu(User user) {

        while (true) {
            System.out.println(
                    "\n---- AbinayaMart Admin Menu ----");

            System.out.println("1. View all products");
            System.out.println("2. Delete any product");
            System.out.println("3. View all orders");
            System.out.println("4. Update order status");
            System.out.println("5. View all users");
            System.out.println("0. Logout");

            int ch = InputUtil.readInt(
                    sc, "Choice: ");

            try {
                switch (ch) {

                    case 1:
                        printProducts(
                                productService.all());
                        break;

                    case 2: {
                        int id = InputUtil.readInt(
                                sc,
                                "Product id to delete: ");

                        productService.deleteProduct(id);

                        System.out.println(
                                "Product deleted.");
                        break;
                    }

                    case 3:
                        printOrders(
                                orderService.allOrders());
                        break;

                    case 4: {
                        int id = InputUtil.readInt(
                                sc, "Order id: ");

                        String st = InputUtil.readLine(
                                sc,
                                "New status "
                                        + "(PLACED/SHIPPED/DELIVERED/CANCELLED): ");

                        orderService.updateStatus(id, st);

                        System.out.println(
                                "Order status updated.");
                        break;
                    }

                    case 5: {
                        List<User> users =
                                userDAO.findAll();

                        System.out.println(
                                "--- Users ---");

                        for (User u : users) {
                            System.out.println(
                                    u.toString());
                        }
                        break;
                    }

                    case 0:
                        return;

                    default:
                        System.out.println(
                                "Invalid choice.");
                }

            } catch (AbinayaMartException e) {
                System.out.println(
                        "Error: " + e.getMessage());

            } catch (Exception e) {
                System.out.println(
                        "Unexpected error: "
                                + e.getMessage());
            }
        }
    }

    // ---------- Shared printers ----------

    private void printProducts(
            List<Product> list) {

        if (list.isEmpty()) {
            System.out.println(
                    "No products found.");
            return;
        }

        System.out.println(
                "--- Products (" + list.size() + ") ---");

        for (Product p : list) {
            System.out.println(
                    p.toString());
        }
    }

    private void printOrders(
            List<Order> orders)
            throws AbinayaMartException {

        if (orders.isEmpty()) {
            System.out.println(
                    "No orders found.");
            return;
        }

        for (Order o : orders) {

            System.out.println(
                    o.toString());

            List<OrderItem> items =
                    orderService.itemsOf(o.getId());

            for (OrderItem it : items) {

                System.out.printf(
                        "    - %s x %d @ Rs.%.2f = Rs.%.2f%n",
                        it.getProductName(),
                        it.getQty(),
                        it.getPrice(),
                        it.getSubtotal());
            }
        }
    }
}