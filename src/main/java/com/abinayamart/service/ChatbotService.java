package com.abinayamart.service;

import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Product;

import java.util.List;

/**
 * Simple rule-based product chatbot for AbinayaMart.
 * Answers FAQs and searches the live product catalogue.
 */
public class ChatbotService {
    private final ProductService productService;

    public ChatbotService(ProductService productService) {
        this.productService = productService;
    }

    public String reply(String input) {
        if (input == null || input.isBlank()) {
            return "Ask me something, e.g. 'categories', 'search shoes', 'cheapest', 'top rated', 'delivery'.";
        }
        String q = input.trim().toLowerCase();
        try {
            if (q.matches(".*\\b(hi|hello|hey|vanakkam)\\b.*")) {
                return "Hello! Welcome to AbinayaMart. I can help you find products. Try 'categories' or 'search <keyword>'.";
            }
            if (q.contains("help") || q.contains("what can you")) {
                return "Try: 'categories' | 'search shoes' | 'filter electronics' | 'cheapest' | 'under 1000' | 'top rated' | 'delivery' | 'payment' | 'order'.";
            }
            if (q.contains("categor")) {
                List<String> cats = productService.categories();
                if (cats.isEmpty()) {
                    return "No categories yet. Sellers have not added products.";
                }
                return "Available categories: " + String.join(", ", cats);
            }
            if (q.contains("top rated") || q.contains("best rated") || q.contains("rating")) {
                List<Product> all = productService.all();
                all.sort((a, b) -> Double.compare(b.getAvgRating(), a.getAvgRating()));
                if (all.isEmpty()) {
                    return "No products yet.";
                }
                StringBuilder sb = new StringBuilder("Top rated products:\n");
                for (int i = 0; i < Math.min(3, all.size()); i++) {
                    sb.append(" - ").append(all.get(i).toString()).append("\n");
                }
                return sb.toString().trim();
            }
            if (q.contains("cheapest") || q.contains("lowest price")) {
                List<Product> all = productService.all();
                if (all.isEmpty()) {
                    return "No products yet.";
                }
                Product min = all.get(0);
                for (Product p : all) {
                    if (p.getPrice() < min.getPrice()) {
                        min = p;
                    }
                }
                return "Cheapest product: " + min.toString();
            }
            if (q.matches(".*under\\s+\\d+.*")) {
                String digits = q.replaceAll("[^0-9]", " ").trim().split("\\s+")[0];
                double budget = Double.parseDouble(digits);
                List<Product> all = productService.all();
                StringBuilder sb = new StringBuilder("Products under Rs." + budget + ":\n");
                int count = 0;
                for (Product p : all) {
                    if (p.getPrice() <= budget) {
                        sb.append(" - ").append(p.toString()).append("\n");
                        count++;
                    }
                }
                if (count == 0) {
                    return "No products under Rs." + budget + ".";
                }
                return sb.toString().trim();
            }
            if (q.startsWith("search ") || q.startsWith("find ")) {
                String keyword = q.replaceFirst("^(search|find)\\s+", "").trim();
                return formatSearch(keyword, null);
            }
            if (q.startsWith("filter ")) {
                String category = input.trim().substring(7).trim();
                return formatSearch(null, category);
            }
            if (q.contains("deliver")) {
                return "Delivery is 2-5 days across India. Order status flow: PLACED -> SHIPPED -> DELIVERED.";
            }
            if (q.contains("payment") || q.contains("pay") || q.contains("upi") || q.contains("cod")) {
                return "AbinayaMart mock payments: UPI (id like name@bank), CARD (12-19 digits), COD (pay on delivery). No real money is charged.";
            }
            if (q.contains("order")) {
                return "To order: browse products -> add to cart -> checkout -> choose UPI/CARD/COD. Track orders in 'My Orders'.";
            }
            if (q.contains("return") || q.contains("refund")) {
                return "Demo policy: cancel an order before it is SHIPPED by contacting support. Mock payments are auto-refunded in this demo.";
            }
            if (q.contains("offer") || q.contains("discount")) {
                return "Today's demo offer: 10% off Electronics with code ABINAYA10 (applied manually at checkout in this college demo).";
            }
            // Default: treat whole input as a product keyword search
            return formatSearch(input.trim(), null);
        } catch (AbinayaMartException e) {
            return "Chatbot database error: " + e.getMessage();
        } catch (Exception e) {
            return "Sorry, I could not understand. Try 'help'.";
        }
    }

    private String formatSearch(String keyword, String category) throws AbinayaMartException {
        List<Product> results = productService.search(keyword, category);
        if (results.isEmpty()) {
            return "No products found"
                    + (keyword != null ? " for '" + keyword + "'" : "")
                    + (category != null ? " in category '" + category + "'" : "")
                    + ". Try another keyword or 'categories'.";
        }
        StringBuilder sb = new StringBuilder("Found " + results.size() + " product(s):\n");
        for (int i = 0; i < Math.min(5, results.size()); i++) {
            sb.append(" - ").append(results.get(i).toString()).append("\n");
        }
        if (results.size() > 5) {
            sb.append("... and ").append(results.size() - 5).append(" more. Use search in the Buyer menu.");
        }
        return sb.toString().trim();
    }
}
