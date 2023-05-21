package com.wahyubudii.demoapi.controllers;

import com.wahyubudii.demoapi.dto.ProductDto;
import com.wahyubudii.demoapi.dto.ResponseData;
import com.wahyubudii.demoapi.dto.SearchDto;
import com.wahyubudii.demoapi.dto.SupplierDto;
import com.wahyubudii.demoapi.models.entities.Category;
import com.wahyubudii.demoapi.models.entities.Product;
import com.wahyubudii.demoapi.models.entities.Supplier;
import com.wahyubudii.demoapi.services.ProductService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getSingleProduct(@PathVariable("id") Long id) {
        return productService.getSingleProduct(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Product>> createProduct(@Valid @RequestBody ProductDto productDto, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error: errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.getMessage().add("Successfully add new Product");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Product product = modelMapper.map(productDto, Product.class);

        responseData.setStatus(true);
        responseData.getMessage().add("Successfully add new product");
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseData<Product>> updateProduct(@Valid @RequestBody ProductDto productDto, Errors errors) {
        ResponseData<Product> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError error: errors.getAllErrors()) {
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Product product = modelMapper.map(productDto, Product.class);

        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteSingleProduct(id);
    }

    @PostMapping(value = "/{id}")
    public void addSupplier(@RequestBody Supplier supplier, @PathVariable("id") Long productId) {
        productService.addSupplier(supplier, productId);
    }

    @PostMapping("/search/name")
    public Product getProductByName(@RequestBody SearchDto searchDto) {
        return productService.findByProductName(searchDto.getSearchKey());
    }

    @PostMapping("/search/namelike")
    public List<Product> getProductByNameLike(@RequestBody SearchDto searchDto) {
        return productService.findByProductNameLike(searchDto.getSearchKey());
    }

    @GetMapping("/search/category/{categoryId}")
    public List<Product> getProductByCategory(@PathVariable("categoryId") Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/search/supplier/{supplierId}")
    public List<Product> getProductBySupplier(@PathVariable("supplierId") Long supplierId) {
        return productService.findBySupplier(supplierId);
    }
}
