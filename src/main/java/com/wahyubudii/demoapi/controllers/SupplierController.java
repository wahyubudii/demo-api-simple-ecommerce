package com.wahyubudii.demoapi.controllers;

import com.wahyubudii.demoapi.dto.ResponseData;
import com.wahyubudii.demoapi.dto.SupplierDto;
import com.wahyubudii.demoapi.models.entities.Supplier;
import com.wahyubudii.demoapi.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Supplier> getAllSupplier() {
        return supplierService.getAllSupplier();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Supplier getSingleSupplier(@PathVariable("id") Long id) {
        return supplierService.getSingleSupplier(id);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Supplier>> createSupplier(@Valid @RequestBody SupplierDto supplierDto, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();
        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.getMessage().add("Successfully add new supplier");
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierDto, Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping
    public ResponseEntity<ResponseData<Supplier>> updateSupplier(@Valid @RequestBody SupplierDto supplierDto, Errors errors) {
        ResponseData<Supplier> responseData = new ResponseData<>();
        if(errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()){
                responseData.getMessage().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Supplier supplier = modelMapper.map(supplierDto, Supplier.class);

        responseData.setStatus(true);
        responseData.setPayload(supplierService.save(supplier));
        return ResponseEntity.ok(responseData);
    }
}
