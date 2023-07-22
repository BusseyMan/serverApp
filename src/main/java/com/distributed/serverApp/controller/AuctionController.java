package com.distributed.serverApp.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import dbo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.distributed.serverApp.JsfUtil;
import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Bid;
import com.distributed.serverApp.entity.Customer;
import com.distributed.serverApp.service.AuctionService;
import com.distributed.serverApp.service.BidService;
import com.distributed.serverApp.service.CustomerService;

@RestController
@RequestMapping(value = {"/server/auction"})
public class AuctionController {

    @Autowired
    private AuctionService service;

    @Autowired
    private BidService bidService;

    @Autowired
    private CustomerService customerService;
    
    @GetMapping("/list")
    public List<dbo.AuctionResponse> findAll(Principal p){
        List<Auction> list = service.findAll();
        return convertToRespone(list);
    }


    @GetMapping("/findBySeller/{email}")
    public List<dbo.AuctionResponse> findBySeller(Principal p,@PathVariable String email){
        Optional<Customer> opp = customerService.findByEmail(email);
        if(!opp.isPresent()){
            return new ArrayList<>();
        }
        Customer customer = opp.get();
        List<Auction> list = service.findBySeller(customer);
        return convertToRespone(list);
    }


    @GetMapping("/find-bids/{id}")
    public List<dbo.BidResponse> findBid(@PathVariable Integer id){
        Optional<Auction> opp = service.findById(id);
        if(!opp.isPresent()){
            return new ArrayList<>();
        }
        Auction ac = opp.get();
        List<Bid> list = bidService.findByAuction(ac);
        return list.stream().map(n -> new BidResponse(n))
            .collect(Collectors.toList());
        
    }
    public List<BidResponse> findBid(Auction ac){
        List<Bid> list = bidService.findByAuction(ac);
        return list.stream().map(n -> new BidResponse(n))
            .collect(Collectors.toList());
        
    }
    private List<dbo.AuctionResponse> convertToRespone(List<Auction> list) {
        return list.stream().map(n -> new dbo.AuctionResponse(
            n,
            findBid(n)
        ))
        .collect(Collectors.toList());
    }

    @GetMapping("/list/{id}")
    public Auction findOne(Principal p,@PathVariable Integer id){
        Optional<Auction> opp = service.findById(id);
        if(opp.isPresent()){
            return opp.get();
        }
        return null;
    }

    @GetMapping("/bid/{id}")
    public List<Bid> findByAution(Principal p, @PathVariable Integer id){

        Optional<Auction> opp = service.findById(id);
        if(!opp.isPresent()){
            return new ArrayList<>();
        }
        Auction auction = opp.get();
        return bidService.findByAuction(auction);
    }

    @GetMapping("/bid-count/{id}")
    public Integer findByAutionCount(Principal p, @PathVariable Integer id){

        Optional<Auction> opp = service.findById(id);
        if(!opp.isPresent()){
            return 0;
        }
        Auction auction = opp.get();
        return bidService.findByAuctionCount(auction);
    }

    

    @GetMapping("/active-auctions")
    public List<AuctionResponse> findActiveAuction(Principal p){
        List<Auction> list = service.findActiveAuction();
        return convertToRespone(list);
    }


    @PostMapping("/create-auction/post")
    public ResponseEntity<String> createAuction(@RequestBody AuctionResponse auction){
        try {
            Auction ac = service.findById(auction.getId()).orElse(new Auction());
            ac.setDeadline(JsfUtil.convertToDate(auction.getDeadline()));
            ac.setReservedPrice(auction.getReservedPrice());
            ac.setStartPrice(auction.getStartPrice());
            
            Customer cux = findSeller(auction.getSeller());
             
            ac.setSeller(cux);
            ac.setItemName(auction.getItemName());
            ac.setPic(auction.getPic());
            ac.setTransactionId(JsfUtil.generateTransactionNumber());
             
            String status = service.save(ac) ? "SUCCESS" : "FAILED";
            return new ResponseEntity<>(status,HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Customer findSeller(CustomerResponse seller) {
        System.out.println("seller.."+seller);
        if(seller == null){
            return null;
        }
        String email = seller.getEmail();
        if("".equals(email) || email == null){
            return null;
        }
        Optional<Customer> opp = customerService.findByEmail(email);
        if(opp.isPresent()){
            return opp.get();
        }
        Customer customer = new Customer();
        customer.setName(seller.getName());
        customer.setEmail(email);
        customer.setTransactionId(JsfUtil.generateTransactionNumber());
        if(customerService.save(customer) ){
            return customer;
        }
        return null;
    }
 

    @PostMapping("/place-bid")
    public ResponseEntity<String> placeBid(@RequestBody BidResponse bid){
       
        Integer acutionId = bid.getAuction();
        Customer customer = findSeller(bid.getCustomer());


        Optional<Auction> oppc = service.findById(acutionId);
        if(!oppc.isPresent()){
            return ResponseEntity.badRequest().body("Invalid auction");
        }        
        Auction auction = oppc.get();

        Bid b = null;
        Optional<Bid> opp = bidService.findByCustomerAndAuction(customer,auction);
        if(opp.isPresent()){
            b = opp.get();
        }else{
            b = new Bid();
            b.setTransactionId(JsfUtil.generateTransactionNumber());
            b.setCustomer(customer);
            b.setAuction(auction);
        }
       
        b.setAmount(bid.getAmount());
        String status = bidService.save(b) ? "SUCCESS" : "FAILED";
        return new ResponseEntity<>(status,HttpStatus.OK);

    }



    @GetMapping("/close-auction/{id}")
    public BidStatus closeAuction(Principal p,@PathVariable Integer id){
        Long bidId = new Date().getTime();
        Optional<Auction> opp = service.findById(id);
        if(!opp.isPresent()){
            return new BidStatus("INVALID_AUCTION");
        }
        Auction auction = opp.get();
        List<Bid> list = bidService.findByAuction(auction);

        if(list.isEmpty()){
            return new BidStatus(bidId,"EMPTY_LIST",null);
        }
        
        
        //Highest Bids
        Bid hihestBid = list
                .stream()
                .max(Comparator.comparing(Bid::getAmount))
                .orElseThrow(NoSuchElementException::new);

        BigDecimal reservePrice = auction.getReservedPrice();

        Boolean won = (hihestBid.getAmount().compareTo(reservePrice) == 1);
        if(won){
            auction.setWinner(hihestBid.getCustomer());
            auction.setStatus("Closed");
            service.save(auction);
            return new BidStatus(bidId, "Won", new BidResponse(hihestBid)) ;
        }
        
        auction.setStatus("Closed");
        service.save(auction);
        return new BidStatus(bidId,"reserve price is not attained",null);


    }
    



}
