package com.abinayamart.web;

import com.abinayamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String name = request.getParameter("name");

            String description =
                    request.getParameter("description");

            String category =
                    request.getParameter("category");

            String imageUrl =
                    request.getParameter("imageUrl");

            double price = Double.parseDouble(
                    request.getParameter("price")
            );

            int stock = Integer.parseInt(
                    request.getParameter("stock")
            );

            // Demo seller ID
            int sellerId = 2;

            productService.addProduct(
                    sellerId,
                    name,
                    description,
                    category,
                    price,
                    stock,
                    imageUrl
            );

            response.sendRedirect("seller");

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to add product: "
                    + e.getMessage(),
                    e
            );
        }
    }
}