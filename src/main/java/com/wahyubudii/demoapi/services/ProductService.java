package com.wahyubudii.demoapi.services;

import com.wahyubudii.demoapi.dto.ResponseData;
import com.wahyubudii.demoapi.models.entities.Product;
import com.wahyubudii.demoapi.models.entities.Supplier;
import com.wahyubudii.demoapi.models.repositories.ProductRepository;
import jakarta.transaction.TransactionScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@TransactionScoped
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final SupplierService supplierService;

    public Product save(Product product) {
        log.info("Product saved!");
        return productRepository.save(product);
    }

    public Iterable<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getSingleProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent()) {
            return null;
        }
        return product.get();
    }

    public String deleteSingleProduct(Long id) {
        productRepository.deleteById(id);
        log.info("Product {} is deleted", id);
        return "Delete Successfully";
    }

    public void addSupplier(Supplier supplier, Long productId) {
        Product product = getSingleProduct(productId);
        if(product == null) {
            throw new RuntimeException("Product with ID: " + productId + " is not found");
        }
        product.getSuppliers().add(supplier);
        save(product);
    }

    public Product findByProductName(String name) {
        return productRepository.findProductByName(name);
    }

    public List<Product> findByProductNameLike(String name) {
        return productRepository.findProductByNameLike("%"+name+"%");
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findProductByCategory(categoryId);
    }

    public List<Product> findBySupplier(Long supplierId) {
        Supplier supplier = supplierService.getSingleSupplier(supplierId);
        if(supplier == null) {
            return new ArrayList<Product>();
        }

        return productRepository.findProductBySupplier(supplier);
    }
}
