package com.distributed.serverApp.service;

 
 


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.distributed.serverApp.repo.BidRepo;
import com.distributed.serverApp.entity.Bid;
import com.distributed.serverApp.entity.Customer;
import com.distributed.serverApp.entity.Auction;

 

/**
 *
 * @author Hussein Alhassan
 */

@Service
public class BidService {
    
    @Autowired
    public BidRepo repository;
    
    public Page<Bid> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }
    
  
    public List<Bid> findAll(){
        return repository.findAll();
    }

    public boolean save(Bid t){
        try{
            repository.save(t);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean saveAll(List<Bid> t){
        try{
            repository.saveAll(t);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    
    public boolean delete(Bid t){
        try{
            repository.delete(t);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
     public boolean deleteById(Integer id){
        try{
            repository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }        
    }

    public Optional<Bid> findById(Integer id) {
       return repository.findById(id);
    }

    public Optional<Bid> findByTransactionId(String accountNumber) {
        return repository.findByTransactionId(accountNumber);
    }

    public Optional<Bid> findByCustomerAndAuction(Customer cus,Auction a) {
        return repository.findByCustomerAndAuction(cus, a);
    }


    public void deleteAll(List<Bid> findBid) {
         repository.deleteAll(findBid);
    }


    public List<Bid> findByAuction(Auction auction) {
        return repository.findByAuction(auction);
    }

    public Integer findByAuctionCount(Auction auction) {
        return repository.findByAuctionCount(auction);
    }

    

}
