package com.wahyubudii.demoapi.models.repositories;

import com.wahyubudii.demoapi.models.entities.Supplier;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
}
