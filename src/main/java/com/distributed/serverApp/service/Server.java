package com.distributed.serverApp.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Server extends UnicastRemoteObject   {

    public static String DIRECTORY = "C:\\dsystem";
    private static final long serialVersionUID = 5186776461749320975L;
    //private static String HOST = "localhost";
    //private static Integer PORT = 8106;

   
    


    protected Server(int port) throws IOException {
        //super(port, 
            //new SslRMIClientSocketFactory(), 
            //new SslRMIServerSocketFactory(null, null, true)); 
        super(port); 
        
        
         
    }
    


    public static List<Path> findByFileName(Path path, String fileName)
            throws IOException {

        List<Path> result;
        try (Stream<Path> pathStream = Files.find(path,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->
                        p.getFileName().toString().equalsIgnoreCase(fileName))
        ) {
            result = pathStream.collect(Collectors.toList());
        }
        return result;

    }
    
    
    public File downloadFileFromServer2(String fileName) throws RemoteException, FileNotFoundException,IOException {
        Path path = Paths.get(DIRECTORY);
        List<Path> result = findByFileName(path, fileName);
        Path p = result.get(result.size()-1);
        return p.toFile();
    } 
    
    // @Override
    // public byte[] downloadFileFromServer(String serverpath) throws RemoteException {
					
	// 	byte [] mydata;	
		
	// 		File serverpathfile = new File(serverpath);			
	// 		mydata=new byte[(int) serverpathfile.length()];
	// 		FileInputStream in;
	// 		try {
	// 			in = new FileInputStream(serverpathfile);
	// 			try {
	// 				in.read(mydata, 0, mydata.length);
	// 			} catch (IOException e) {
					
	// 				e.printStackTrace();
	// 			}						
	// 			try {
	// 				in.close();
	// 			} catch (IOException e) {
				
	// 				e.printStackTrace();
	// 			}
				
	// 		} catch (FileNotFoundException e) {
				
	// 			e.printStackTrace();
	// 		}		
			
	// 		return mydata;
				 
	// }

    

    // @Override
    // public List<Auction> findAll(Integer id) throws RemoteException{
    //     AuctionService service = new AuctionService();
    //     return service.findAll();
    // }

    // @Override
    // public List<Bid> findByAution(Integer id) throws RemoteException{
    //     AuctionService service = new AuctionService();
    //     BidService bidService = new BidService();
    //     Optional<Auction> opp = service.findById(id);
    //     if(!opp.isPresent()){
    //         return new ArrayList<>();
    //     }
    //     Auction auction = opp.get();
    //     return bidService.findByAuction(auction);
    // }

    // @Override
    // public Integer findByAutionCount(Integer id) throws RemoteException{
    //     AuctionService service = new AuctionService();
    //     BidService bidService = new BidService();
        
    //     Optional<Auction> opp = service.findById(id);
    //     if(!opp.isPresent()){
    //         return 0;
    //     }
    //     Auction auction = opp.get();
    //     return bidService.findByAuctionCount(auction);
    // }

    

    // @Override
    // public List<Auction> findActiveAuction() throws RemoteException{
    //     return new ArrayList<>();
    // }


    // @Override
    // public String createAuction(Auction auction) throws RemoteException{
    //     try {
    //          AuctionService service = new AuctionService();
  
    //         Auction ac = service.findById(auction.getId()).orElse(new Auction());
    //         ac.setDeadline(auction.getDeadline());
    //         ac.setReservedPrice(auction.getReservedPrice());
    //         ac.setStartPrice(auction.getStartPrice());
    //         ac.setSeller(auction.getSeller());
    //         ac.setItemName(auction.getItemName());
    //         ac.setPic(auction.getPic());
    //         ac.setTransactionId(JsfUtil.generateTransactionNumber());
             
    //         return service.save(ac) ? "SUCCESS" : "FAILED";

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return "FAILED";
    //     }
    // }

    // @Override
    // public String placeBid(BidResponse bid) throws RemoteException{
    //     BidService bidService = new BidService();
    //     Integer customerId = bid.getCustomer();
    //     Integer acutionId = bid.getAuction();

    //     CustomerService customerService = new CustomerService();
    //     AuctionService service = new AuctionService();

    //     Optional<Customer> oppc = customerService.findById(customerId);
    //     if(!oppc.isPresent()){
    //         return "FAILED";
    //     }

    //     Optional<Auction> oppAc = service.findById(acutionId);
    //     if(!oppc.isPresent()){
    //         return "FAILED";
    //     }

    //     Customer customer = oppc.get();
    //     Auction auction = oppAc.get();
    //     Bid b = null;
    //     Optional<Bid> opp = bidService.findByCustomerAndAuction(customer,auction);
    //     if(opp.isPresent()){
    //         b = opp.get();
    //     }else{
    //         b = new Bid();
    //         b.setTransactionId(JsfUtil.generateTransactionNumber());
    //         b.setCustomer(customer);
    //         b.setAuction(auction);
    //     }
       
    //     b.setAmount(bid.getAmount());
    //     return bidService.save(b) ? "SUCCESS" : "FAILED";

    // }


    
}

