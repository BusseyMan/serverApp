package com.distributed.serverApp.service;

 
 
 


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.distributed.serverApp.repo.AuctionRepo;
import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Customer;


 

/**
 *
 * @author Hussein Alhassan
 */

@Service
public class AuctionService {
    
    @Autowired
    public AuctionRepo repository;
    
    public Page<Auction> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }
    

    public Page<Auction> findBySeller(Customer seller,Integer page, Integer max) {
        return repository.findBySeller(seller,PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }
  
    public List<Auction> findAll(){
        return repository.findAll();
    }

    

    public List<Auction> findActiveAuction( ){
        return repository.findActiveAuction();
    }

    
    public Page<Auction> findByStatus(String status,Integer page, Integer max) {
        return repository.findByStatus(status,PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }

     
    
    
    public boolean save(Auction t){
        try{
            repository.save(t);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean saveAll(List<Auction> t){
        try{
            repository.saveAll(t);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    
    public boolean delete(Auction t){
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

    public Optional<Auction> findById(Integer id) {
       return repository.findById(id);
    }

    public Optional<Auction> findByTransactionId(String accountNumber) {
        return repository.findByTransactionId(accountNumber);
    }

    public List<Auction> findBySeller(Customer seller) {
        return repository.findBySeller(seller);
    }


    public void deleteAll(List<Auction> findAuction) {
         repository.deleteAll(findAuction);
    }


}
