package dbo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.distributed.serverApp.JsfUtil;
import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AuctionResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String itemName;
    private String pic;
    private BigDecimal startPrice;
    private BigDecimal reservedPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date deadline;
    private CustomerResponse  seller;  
    private String transactionId;
    private List<BidResponse> bid;
    private CustomerResponse winner;
    private String status;

    public AuctionResponse() {
    }

    
    public AuctionResponse(Auction ac, List<BidResponse> bid) {
        if (ac==null) {
            ac=new Auction();
        }
        this.bid = bid;
        this.id = ac.getId();
        this.itemName = ac.getItemName();
        this.pic = ac.getPic();
        this.startPrice = ac.getStartPrice();
        this.reservedPrice = ac.getReservedPrice();
        this.deadline = JsfUtil.convertToDate(ac.getDeadline()); 
        this.seller = new CustomerResponse(ac.getSeller());
        this.transactionId = ac.getTransactionId();
        this.status = ac.getStatus();
        this.winner = new CustomerResponse(ac.getWinner());
         
    }
    public AuctionResponse(Auction ac) {
        this.id = ac.getId();
        this.itemName = ac.getItemName();
        this.pic = ac.getPic();
        this.startPrice = ac.getStartPrice();
        this.reservedPrice = ac.getReservedPrice();
        this.deadline = JsfUtil.convertToDate(ac.getDeadline()); 
        this.seller = new CustomerResponse(ac.getSeller());
        this.transactionId = ac.getTransactionId();
        this.status = ac.getStatus();
        this.winner = new CustomerResponse(ac.getWinner());
         
    }


    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(BigDecimal reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public CustomerResponse getSeller() {
        return seller;
    }

    public void setSeller(CustomerResponse seller) {
        this.seller = seller;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuctionResponse other = (AuctionResponse) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AuctionResponse [itemName=" + itemName + "]";
    }

    public CustomerResponse getWinner() {
        return winner;
    }


    public void setWinner(CustomerResponse winner) {
        this.winner = winner;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public List<BidResponse> getBid() {
        return bid;
    }

    public void setBid(List<BidResponse> bid) {
        this.bid = bid;
    }

    
}
