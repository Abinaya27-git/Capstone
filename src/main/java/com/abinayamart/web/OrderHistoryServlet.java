package com.abinayamart.web;

import com.abinayamart.dao.OrderDAO;
import com.abinayamart.model.Order;
import com.abinayamart.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            List<Order> orders =
                    orderDAO.findByBuyer(user.getId());

            request.setAttribute("orders", orders);

            request.getRequestDispatcher("/order-history.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load order history.",
                    e
            );
        }
    }
}