package com.abinayamart.web;

import com.abinayamart.model.Product;
import com.abinayamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/edit-product")
public class EditProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            Product product =
                    productService.getById(id);

            if (product == null) {
                response.sendRedirect(
                        request.getContextPath() + "/seller"
                );
                return;
            }

            request.setAttribute(
                    "product",
                    product
            );

            request.getRequestDispatcher(
                    "/edit-product.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to load product for editing: "
                    + e.getMessage(),
                    e
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            String name =
                    request.getParameter("name");

            String description =
                    request.getParameter("description");

            String category =
                    request.getParameter("category");

            double price = Double.parseDouble(
                    request.getParameter("price")
            );

            int stock = Integer.parseInt(
                    request.getParameter("stock")
            );

            String imageUrl =
                    request.getParameter("imageUrl");

            Product product =
                    productService.getById(id);

            if (product == null) {
                response.sendRedirect(
                        request.getContextPath() + "/seller"
                );
                return;
            }

            product.setName(name);
            product.setDescription(description);
            product.setCategory(category);
            product.setPrice(price);
            product.setStock(stock);
            product.setImageUrl(
                    imageUrl == null ? "" : imageUrl.trim()
            );

            productService.editProduct(product);

            response.sendRedirect(
                    request.getContextPath() + "/seller"
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to update product: "
                    + e.getMessage(),
                    e
            );
        }
    }
}