package com.wahyubudii.demoapi.services;

import com.wahyubudii.demoapi.models.entities.Supplier;
import com.wahyubudii.demoapi.models.repositories.SupplierRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SupplierService {
    @Autowired
    private final SupplierRepository supplierRepository;

    public Supplier save(@RequestBody Supplier supplier) {
        log.info("Supplier saved!");
        return supplierRepository.save(supplier);
    }

    public Iterable<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    public Supplier getSingleSupplier(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if(!supplier.isPresent()) {
            return null;
        }

        return supplier.get();
    }

    public String deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
        log.info("Supplier {} is deleted", id);
        return "Delete Successfully";
    }
}
