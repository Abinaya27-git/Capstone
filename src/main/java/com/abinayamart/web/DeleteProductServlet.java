package com.abinayamart.web;

import com.abinayamart.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {

    private final ProductService productService =
            new ProductService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            productService.deleteProduct(id);

            response.sendRedirect(
                    request.getContextPath() + "/seller"
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Unable to delete product: "
                    + e.getMessage(),
                    e
            );
        }
    }
}