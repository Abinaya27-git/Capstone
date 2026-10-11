package com.abinayamart.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/chat")
public class ChatbotServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder body = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }

        Matcher matcher = Pattern.compile(
                "\"message\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""
        ).matcher(body.toString());

        String message = "";

        if (matcher.find()) {
            message = matcher.group(1)
                    .replace("\\\"", "\"")
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\\\", "\\")
                    .trim();
        }

        if (message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                    "{\"reply\":\"Please enter a message.\"}"
            );
            return;
        }

        String text = message.toLowerCase().trim();
        String reply;

        if (text.matches(".*\\b(hi|hello|hey|vanakkam)\\b.*")) {

            reply = "Hello! Welcome to AbinayaMart. How can I help you?";

        } else if (text.contains("who are you")
                || text.contains("your name")) {

            reply = "I am the AbinayaMart shopping assistant. I can help you with shopping, products, orders and seller information.";

        } else if (text.contains("contact seller")
                || text.contains("contact the seller")
                || text.contains("seller contact")
                || text.contains("seller phone")
                || text.contains("seller email")
                || text.contains("seller details")) {

            reply = "Please check the relevant product page for seller contact details, if provided. If no contact information is shown, this demo may not have a direct seller contact feature.";

        } else if (text.contains("add a product")
                || text.contains("add product")
                || text.contains("upload product")
                || text.contains("create product")
                || text.contains("sell a product")) {

            reply = "To add a product as a seller, log in with your seller account and open the Seller Dashboard. Use the Add Product form to enter the product details and save it.";

        } else if (text.contains("place an order")
                || text.contains("how to order")
                || text.contains("how can i order")
                || text.contains("order a product")
                || text.contains("buy a product")) {

            reply = "To place an order, log in with your buyer account, open Products, add your chosen item to the Cart, then open Cart and click Place Order.";

        } else if (text.contains("check my order")
                || text.contains("check order")
                || text.contains("track my order")
                || text.contains("order history")
                || text.contains("my orders")
                || text.contains("order status")
                || text.contains("view my order")) {

            reply = "Log in with your buyer account and open My Orders or Order History to check your order details.";

        } else if (text.contains("cancel")) {

            reply = "Please check your order page for available options. This demo may not support order cancellation.";

        } else if (text.contains("checkout")
                || text.contains("payment")
                || text.contains("cash on delivery")
                || text.matches(".*\\bcod\\b.*")) {

            reply = "Open your Cart and click Place Order to continue with the demo checkout. Cash on delivery is available in this demo.";

        } else if (text.contains("cart")) {

            reply = "Open Products, add your chosen item to the Cart, and open Cart to review your items before placing an order.";

        } else if (text.contains("search")
                || text.contains("find")) {

            reply = "Open the Products page and use the search box to find the item you want.";

        } else if (text.contains("price")
                || text.contains("cost")) {

            reply = "Open the Products page to view each product's price and details.";

        } else if (text.contains("product")
                || text.contains("available")
                || text.contains("catalog")
                || text.contains("item")) {

            reply = "Open the Products page to browse available items and check their prices and details. To buy an item, add it to your Cart.";

        } else if (text.contains("buy")
                || text.contains("purchase")) {

            reply = "To buy a product, open Products, add the item to your Cart, then click Place Order.";

        } else if (text.contains("order")
                || text.contains("delivery")) {

            reply = "Log in with your buyer account and open My Orders or Order History to check your order details.";

        } else if (text.contains("review")
                || text.contains("rating")
                || text.contains("feedback")) {

            reply = "Open a product's details page to check its ratings and reviews. If review submission is available, you can share your feedback there.";

        } else if (text.contains("seller")
                || text.contains("sell")) {

            reply = "Sellers can log in with their seller account to add, edit or delete products and check incoming orders. For contact details, check the product page if such details are provided.";

        } else if (text.contains("admin")
                || text.contains("administrator")) {

            reply = "The admin account can access the Admin Dashboard to review store information and manage the demo store.";

        } else if (text.contains("register")
                || text.contains("sign up")
                || text.contains("create account")) {

            reply = "Use the Register link on the login page to create an account, if registration is enabled.";

        } else if (text.contains("login")
                || text.contains("log in")
                || text.contains("password")) {

            reply = "Open the login page and enter your account credentials. Use the appropriate buyer, seller or admin account.";

        } else if (text.contains("thank")) {

            reply = "You're welcome! Happy shopping at AbinayaMart.";

        } else if (text.contains("help")) {

            reply = "I can help with products, prices, search, cart, checkout, placing orders, order history, reviews, seller products, seller contact information, login and admin features.";

        } else if (text.matches(".*\\b(bye|goodbye)\\b.*")) {

            reply = "Goodbye! Thank you for visiting AbinayaMart. Have a nice day!";

        } else {

            reply = "Sorry, I don't know that yet. Ask me about products, adding products, seller contact, cart, checkout or orders.";
        }

        String jsonReply = reply
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        response.getWriter().write(
                "{\"reply\":\"" + jsonReply + "\"}"
        );
    }
}