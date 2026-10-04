package com.abinayamart.web;

import com.abinayamart.dao.OrderDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.CartItem;
import com.abinayamart.model.User;
import com.abinayamart.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    private CartService getCart(HttpSession session) {
        CartService cart =
                (CartService) session.getAttribute("cart");

        if (cart == null) {
            cart = new CartService();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp");
            return;
        }

        CartService cart = getCart(session);

        if (cart.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/cart");
            return;
        }

        String paymentMode = request.getParameter("paymentMode");

        if (paymentMode == null || paymentMode.isBlank()) {
            paymentMode = "COD";
        }

        try {
            List<CartItem> items = cart.items();

            orderDAO.createOrder(
                    user.getId(),
                    items,
                    paymentMode
            );

            cart.clear();

            response.sendRedirect(
                    request.getContextPath()
                    + "/products.jsp?checkout=success");

        } catch (AbinayaMartException e) {
            throw new ServletException(
                    "Checkout failed: " + e.getMessage(),
                    e
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath() + "/cart");
    }
}