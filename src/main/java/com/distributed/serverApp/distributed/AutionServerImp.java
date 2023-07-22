package com.distributed.serverApp.distributed;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.distributed.serverApp.JsfUtil;
import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Bid;
import com.distributed.serverApp.entity.Customer;
import com.distributed.serverApp.service.AuctionService;
import com.distributed.serverApp.service.BidService;
import com.distributed.serverApp.service.CustomerService;

import dbo.AuctionResponse;
import dbo.BidResponse;
import dbo.BidStatus;
import dbo.CustomerResponse;
import dbo.ResponseHeader;

    
@Controller
public class AutionServerImp  implements AutionServer {

    public static enum Opt{
        INVALID_AUCTION_ID,
        SUCCESS,
        FAILED,
        INVALID_AUCTION,
        EMPTY_LIST
    }
    
    @Autowired
    private AuctionService auctionService;

    @Autowired
    private BidService bidService;

    @Autowired
    private CustomerService customerService;

    @Override
    public List<AuctionResponse> findAll(Integer id) throws RemoteException {
        try {
            return JsfUtil.convertToAuctionResponse(auctionService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuctionResponse findById(Integer id) throws RemoteException {
        try {
            Optional<Auction> opp = auctionService.findById(id);
            if(!opp.isPresent()){
                return  null;
            }
            return  new AuctionResponse(opp.get());

        } catch (Exception e) {
           return  null;
        }
    }


    @Override
    public String createAuction(Integer id,String itemName,
                                String pic,
                                BigDecimal startPrice,
                                BigDecimal reservedPrice,
                                Date deadline,
                                String customerName,String customerEmail) throws RemoteException {
        try {
           
            Auction ac = auctionService.findById(id).orElse(new Auction());
            ac.setDeadline(JsfUtil.convertToLocaldate(deadline));
            ac.setReservedPrice(reservedPrice);
            ac.setStartPrice(startPrice);
            Customer seller = findCustomer(customerName,customerEmail);
            ac.setSeller(seller);
            ac.setItemName(itemName);
            ac.setPic(pic);
            ac.setTransactionId(JsfUtil.generateTransactionNumber());
            return  auctionService.save(ac) ? Opt.SUCCESS.name() : Opt.FAILED.name();
        } catch (Exception e) {
            e.printStackTrace();
            return  Opt.FAILED.name();
        }
    }


    @Override
    public List<AuctionResponse> findActiveAuction() throws RemoteException {
        List<Auction> list = auctionService.findActiveAuction();
        return JsfUtil.convertToRespone(list,bidService);
    }

    @Override
    public ResponseHeader findActiveAuction(Integer page,Integer max) throws RemoteException {
        Page<Auction> pages = auctionService.findByStatus("Active",page,max);
        return JsfUtil.convertToRespone(pages,max,bidService);
    }
    @Override
    public Integer findByAutionCount(Integer id) throws RemoteException {
        try {
            Optional<Auction> opp = auctionService.findById(id);
            if(!opp.isPresent()){
                return 0;
            }
            Auction auction = opp.get();
            return bidService.findByAuctionCount(auction);
        } catch (Exception e) {
            return 0;
        }
    }



    
   

    @Override
    public String sayHelloRmi(String msg) {
        System.out.println("================Server Side ========================");
        System.out.println("Inside Rmi IMPL - Incoming msg : " + msg);
        return "Hello " + msg + " :: Response time - > " + new Date();
    }


     

    @Override
    public String placeBid(BidResponse bid) throws RemoteException {
        Integer auctionId = bid.getAuction();
        if(auctionId == null){
            return  Opt.INVALID_AUCTION_ID.name();
        }
        
        Optional<Auction> oppc = auctionService.findById(auctionId);
        if(!oppc.isPresent()){
            return  Opt.INVALID_AUCTION_ID.name();
        }        
        Auction auction = oppc.get();
        Customer cux =findSeller(bid.getCustomer());
        
        Bid b = null;
        Optional<Bid> opp = bidService.findByCustomerAndAuction(cux,auction);
        if(opp.isPresent()){
            b = opp.get();
        }else{
            b = new Bid();
            b.setTransactionId(JsfUtil.generateTransactionNumber());
            b.setCustomer(cux);
            b.setAuction(auction);
        }
       
        b.setAmount(bid.getAmount());
        return bidService.save(b) ? Opt.SUCCESS.name() : Opt.FAILED.name();
 
    }



    @Override
    public String placeBid(Integer auctionId,
                            String customerName,
                            String customerEmail,
                            BigDecimal bidAmount) throws RemoteException {
        
         
        Optional<Auction> oppc = auctionService.findById(auctionId);
        if(!oppc.isPresent()){
            return  Opt.INVALID_AUCTION_ID.name();
        }        
        Auction auction = oppc.get();
        Customer cux = findCustomer(customerName,customerEmail);
 
        Bid b = null;
        Optional<Bid> opp = bidService.findByCustomerAndAuction(cux,auction);
        if(opp.isPresent()){
            b = opp.get();
        }else{
            b = new Bid();
            b.setTransactionId(JsfUtil.generateTransactionNumber());
            b.setCustomer(cux);
            b.setAuction(auction);
        }
       
        b.setAmount(bidAmount);
        return bidService.save(b) ? Opt.SUCCESS.name() : Opt.FAILED.name();
 
    }

   
 
    private Customer findCustomer(String name,String email) {
       
        if("".equals(email) || email == null){
            return null;
        }
        Optional<Customer> opp = customerService.findByEmail(email);
        if(opp.isPresent()){
            return opp.get();
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setTransactionId(JsfUtil.generateTransactionNumber());
        if(customerService.save(customer) ){
            return customer;
        }
        return null;
    }

    @Override
    public BidResponse findBidByTransactionId(String trx) throws RemoteException{
        Optional<Bid> opp = bidService.findByTransactionId(trx);
        if(!opp.isPresent()){
            return  null;
        }
        return new BidResponse(opp.get());
    }

    @Override
    public BidResponse findBidByCustomerAndAuction(String customerEmail, 
            Integer auctionId) throws RemoteException{
        Optional<Customer> oppc = customerService.findByEmail(customerEmail);
        if(!oppc.isPresent()){
            return null;
        }
        Optional<Auction> oppa = auctionService.findById(auctionId);
        if(!oppa.isPresent()){
            return null;
        }

        Customer customer = oppc.get();
        Auction auction = oppa.get();

        Optional<Bid> opp = bidService.findByCustomerAndAuction(customer, auction);
        if(!opp.isPresent()){
            return null;
        }
        Bid bid = opp.get();
        return  new BidResponse(bid);
    }

    @Override
    public List<BidResponse> findBidByAuction(Integer auctionId) throws RemoteException{
        Optional<Auction> oppa = auctionService.findById(auctionId);
        if(!oppa.isPresent()){
            return new ArrayList<>();
        }
        Auction auction = oppa.get();
        List<Bid> list = bidService.findByAuction(auction);
        return list.stream()
                .map(n -> new BidResponse(n))
                .collect(Collectors.toList());
    }

    @Override
    public Integer findBidCountByAuction(Integer auctionId) throws RemoteException{

        Optional<Auction> oppa = auctionService.findById(auctionId);
        if(!oppa.isPresent()){
            return 0;
        }
        Auction auction = oppa.get();
        return bidService.findByAuctionCount(auction);
    }

   @Override
   public BidStatus closeAuction(Integer id){
        Long bidId = new Date().getTime();
        Optional<Auction> opp = auctionService.findById(id);
        if(!opp.isPresent()){
            return new BidStatus(Opt.INVALID_AUCTION.name());
        }
        Auction auction = opp.get();
        List<Bid> list = bidService.findByAuction(auction);

        if(list.isEmpty()){
            return new BidStatus(bidId,Opt.EMPTY_LIST.name(),null);
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
            auctionService.save(auction);
            return new BidStatus(bidId, "Won", new BidResponse(hihestBid)) ;
        }
        
        auction.setStatus("Closed");
        auctionService.save(auction);
        return new BidStatus(bidId,"reserve price is not attained",null);


    }
    
    

    private Customer findSeller(CustomerResponse seller) {
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
 

}
