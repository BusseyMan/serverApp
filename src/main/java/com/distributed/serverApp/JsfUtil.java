package com.distributed.serverApp;


import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Bid;
import com.distributed.serverApp.entity.Users;
import com.distributed.serverApp.service.BidService;
import com.distributed.serverApp.service.MyUserDetails;

import dbo.AuctionResponse;
import dbo.BidResponse;
import dbo.ResponseHeader;

public class JsfUtil {

    public static enum Roles{
        ROLE_BUYER,
        ROLE_SELLER,
        ADMIN
    }

    public static String generateTransactionNumber() {
        return UUID.randomUUID().toString().toUpperCase();
    }
    public static Date convertToDate(LocalDate local){
        if(local==null){
            return null;
        }
         return Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    public static LocalDate convertToDate(Date dateToConvert) {
        try{
        
            if(dateToConvert==null){
                return null;
            }
            return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static LocalDate convertToLocaldate(Date dateToConvert) {
        try{
        
            if(dateToConvert==null){
                return null;
            }
            return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
        }

    public static String generateResetPassword(){
        return (UUID.randomUUID().toString().substring(1, 7)).toUpperCase();
    }

    public static String convertToString(List<String> list){
        return  list.stream().map(Object::toString)
                        .collect(Collectors.joining(", "));
    }

    public static String convertToString(String[] list) {
        if(list == null ){
            return null;
        }
        return String.join(",", list);
    }

    public static Users currentUser(Principal principal){
       try{
            MyUserDetails loginedUser = (MyUserDetails) ((Authentication) principal).getPrincipal();
            Users user = loginedUser.getUser();
            return user;
       }catch(NullPointerException n){
           return new Users();
       }catch(Exception e){
           return new Users();
       }
    }
    public static List<AuctionResponse> convertToAuctionResponse(List<Auction> list) {
        return list.stream()
                .map(n -> new AuctionResponse(n))
                .collect(Collectors.toList());
    }

    public static AuctionResponse convertToAuctionResponse(Auction n) {
        return new AuctionResponse(n) ;
    }

    public static List<dbo.AuctionResponse> convertToRespone(List<Auction> list,BidService bidService) {
        return list.stream().map(n -> new dbo.AuctionResponse(
            n,
            findBid(n,bidService)
        ))
        .collect(Collectors.toList());
    }

   

 


    public static List<BidResponse> findBid(Auction ac, BidService bidService){
        List<Bid> list = bidService.findByAuction(ac);
        return list.stream().map(n -> new BidResponse(n))
            .collect(Collectors.toList());
        
    }
    public static ResponseHeader convertToRespone(Page<Auction> pages,Integer max, BidService bidService) {
        List<Auction> list = pages.getContent();
        int numberOfElements = pages.getNumberOfElements();
        int totalPages = pages.getTotalPages();
        Integer page = pages.getTotalPages();
        
        List<AuctionResponse> lines = list.stream().map(n -> new dbo.AuctionResponse(
            n,
            findBid(n,bidService)
        ))
        .collect(Collectors.toList());
        return new ResponseHeader(numberOfElements, totalPages, page, max, lines);
    }
    
}

