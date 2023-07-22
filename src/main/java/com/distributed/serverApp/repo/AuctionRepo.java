package com.distributed.serverApp.repo;

 
  
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Customer;

/**
 *
 * @author Hussein Alhassan
 */
public interface AuctionRepo extends JpaRepository<Auction, Integer>{
    public Optional<Auction> findByTransactionId(String name);
    
    public Page<Auction> findBySeller(Customer seller, Pageable of);
    public Page<Auction> findByStatus(String status, Pageable of);

    public List<Auction> findBySeller(Customer seller);
    @Query("SELECT c from Auction c where c.deadline < CURRENT_DATE and c.status = 'Active'")
    public List<Auction> findActiveAuction();
}

