package com.abinayamart.service;

import com.abinayamart.dao.ProductDAO;
import com.abinayamart.exception.AbinayaMartException;
import com.abinayamart.model.Product;

import java.util.List;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public Product addProduct(
            int sellerId,
            String name,
            String desc,
            String category,
            double price,
            int stock,
            String imageUrl)
            throws AbinayaMartException {

        validate(name, category, price, stock);

        Product product = new Product(
                sellerId,
                name.trim(),
                desc == null ? "" : desc.trim(),
                category.trim(),
                price,
                stock
        );

        product.setImageUrl(
                imageUrl == null ? "" : imageUrl.trim()
        );

        return productDAO.create(product);
    }

    public void editProduct(Product p)
            throws AbinayaMartException {

        validate(
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getStock()
        );

        productDAO.update(p);
    }

    private void validate(
            String name,
            String category,
            double price,
            int stock)
            throws AbinayaMartException {

        if (name == null || name.isBlank()) {
            throw new AbinayaMartException(
                    "Product name is required."
            );
        }

        if (category == null || category.isBlank()) {
            throw new AbinayaMartException(
                    "Category is required."
            );
        }

        if (price < 0) {
            throw new AbinayaMartException(
                    "Price cannot be negative."
            );
        }

        if (stock < 0) {
            throw new AbinayaMartException(
                    "Stock cannot be negative."
            );
        }
    }

    public void deleteProduct(int id)
            throws AbinayaMartException {
        productDAO.delete(id);
    }

    public Product getById(int id)
            throws AbinayaMartException {
        return productDAO.findById(id);
    }

    public List<Product> all()
            throws AbinayaMartException {
        return productDAO.findAll();
    }

    public List<Product> bySeller(int sellerId)
            throws AbinayaMartException {
        return productDAO.findBySeller(sellerId);
    }

    public List<Product> search(
            String keyword,
            String category)
            throws AbinayaMartException {
        return productDAO.search(keyword, category);
    }

    public List<String> categories()
            throws AbinayaMartException {
        return productDAO.findCategories();
    }
}