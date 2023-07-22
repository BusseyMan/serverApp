package com.distributed.serverApp.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
 import com.distributed.serverApp.entity.Customer;

/**
 *
 * @author Hussein Alhassan
 */
public interface CustomerRepo extends JpaRepository<Customer, Integer>{
    public Optional<Customer> findByTransactionId(String name);

    public Optional<Customer> findByEmail(String email);
}

