package com.distributed.serverApp.repo;

 
 
  
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Bid;
import com.distributed.serverApp.entity.Customer;

/**
 *
 * @author Hussein Alhassan
 */
public interface BidRepo extends JpaRepository<Bid, Integer>{
    public Optional<Bid> findByTransactionId(String name);
    public Optional<Bid> findByCustomerAndAuction(Customer cus, Auction a);
    public List<Bid> findByAuction(Auction auction);

    @Query("SELECT COUNT(c.id) from Bid c where c.auction =  :auction ")
    public Integer findByAuctionCount(@Param("auction")Auction auction);
}

