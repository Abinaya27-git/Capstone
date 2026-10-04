package com.abinayamart.web;

import com.abinayamart.dao.ProductDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Product;
import com.abinayamart.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final ProductDAO productDAO = new ProductDAO();

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
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        CartService cart = getCart(session);

        request.setAttribute("cartItems", cart.items());
        request.setAttribute("cartTotal", cart.total());

        request.getRequestDispatcher("/cart.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        HttpSession session = request.getSession(true);

        CartService cart = getCart(session);

        try {

            if ("add".equals(action)) {

                int productId =
                        Integer.parseInt(
                                request.getParameter("productId"));

                int quantity = 1;

                String quantityValue =
                        request.getParameter("quantity");

                if (quantityValue != null
                        && !quantityValue.isBlank()) {

                    quantity =
                            Integer.parseInt(quantityValue);
                }

                Product product =
                        productDAO.findById(productId);

                cart.add(product, quantity);

            } else if ("remove".equals(action)) {

                int productId =
                        Integer.parseInt(
                                request.getParameter("productId"));

                cart.remove(productId);

            } else if ("clear".equals(action)) {

                cart.clear();
            }

            response.sendRedirect(
                    request.getContextPath() + "/cart");

        } catch (NumberFormatException e) {

            throw new ServletException(
                    "Invalid cart request.", e);

        } catch (AbinayaMartException e) {

            throw new ServletException(
                    "Cart operation failed: "
                            + e.getMessage(), e);
        }
    }
}