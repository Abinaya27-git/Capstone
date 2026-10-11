package com.abinayamart.web;

import com.abinayamart.model.Role;

import com.abinayamart.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/Register.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleText = request.getParameter("role");

        try {
            if (roleText == null ||
                (!roleText.equalsIgnoreCase("BUYER") &&
                 !roleText.equalsIgnoreCase("SELLER"))) {

                throw new IllegalArgumentException(
                    "Please select Buyer or Seller."
                );
            }

            Role role = Role.valueOf(roleText.toUpperCase());

            authService.register(name, email, password, role);

            response.sendRedirect(
                request.getContextPath() + "/Index.jsp?registered=success"
            );

        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/Register.jsp")
                   .forward(request, response);
        }
    }
}