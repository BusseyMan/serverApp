package com.distributed.serverApp.service;

 
 


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.distributed.serverApp.repo.CustomerRepo;
import com.distributed.serverApp.entity.Customer;


 

/**
 *
 * @author Hussein Alhassan
 */

@Service
public class CustomerService {
    
    @Autowired
    public CustomerRepo repository;
    
    public Page<Customer> findAll(Integer page, Integer max) {
        return repository.findAll(PageRequest.of(page - 1, max, Sort.by(Sort.Direction.DESC, "id")));
    }
    
  
    public List<Customer> findAll(){
        return repository.findAll();
    }

     
    
    
    public boolean save(Customer t){
        try{
            repository.save(t);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean saveAll(List<Customer> t){
        try{
            repository.saveAll(t);
            return true;
        }catch(Exception e){
            return false;
        }
        
    }
    
    public boolean delete(Customer t){
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

    public Optional<Customer> findById(Integer id) {
       return repository.findById(id);
    }

    public Optional<Customer> findByTransactionId(String accountNumber) {
        return repository.findByTransactionId(accountNumber);
    }


    public void deleteAll(List<Customer> findCustomer) {
         repository.deleteAll(findCustomer);
    }


    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }


}
