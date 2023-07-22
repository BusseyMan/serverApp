package com.distributed.serverApp.distributed;
      
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dbo.AuctionResponse;
import dbo.BidResponse;
import dbo.BidStatus;
import dbo.ResponseHeader;


public interface AutionServer extends Remote {
    public String sayHelloRmi(String msg) throws RemoteException;
    public List<AuctionResponse> findAll(Integer id)throws RemoteException;
    public AuctionResponse findById(Integer id) throws RemoteException;
    public Integer findByAutionCount(Integer id) throws RemoteException;
    public List<AuctionResponse> findActiveAuction() throws RemoteException;
    public ResponseHeader findActiveAuction(Integer page,Integer max) throws RemoteException;
    public String placeBid(BidResponse bid) throws RemoteException;
    public BidStatus closeAuction(Integer id)throws RemoteException;
    //---Bid-------------------
    public BidResponse findBidByTransactionId(String name) throws RemoteException;
    public BidResponse findBidByCustomerAndAuction(String customerEmail, 
                    Integer auctionId) throws RemoteException;
    public List<BidResponse> findBidByAuction(Integer auctionId) throws RemoteException;
    public Integer findBidCountByAuction(Integer auctionId) throws RemoteException;
    //---Bid ends here----
    public String placeBid(Integer auctionId,
                            String customerName,
                            String customerEmail,
                            BigDecimal bidAmount) throws RemoteException;
    public String createAuction(Integer id,String itemName,
                                String pic,
                                BigDecimal startPrice,
                                BigDecimal reservedPrice,
                                Date deadline,
                                String customerName,String customerEmail) throws RemoteException ;

}