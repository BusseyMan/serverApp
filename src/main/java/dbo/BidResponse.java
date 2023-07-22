package dbo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.distributed.serverApp.entity.Auction;
import com.distributed.serverApp.entity.Bid;


public class BidResponse implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private BigDecimal amount;
    private String transactionId;
    private CustomerResponse customer ; 
    private Integer auction;

    
    public BidResponse() {
    }

    public BidResponse(Bid n) {
        if (n==null) {
            n=new Bid();
        }
        this.id = n.getId();
        this.amount = n.getAmount();
        this.transactionId = n.getTransactionId();
        this.customer = new CustomerResponse(n.getCustomer());
        Auction ax = n.getAuction();
        if(ax != null){
            this.auction = ax.getId();
        }
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    

    public Integer getAuction() {
        return auction;
    }

    public void setAuction(Integer auction) {
        this.auction = auction;
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
        BidResponse other = (BidResponse) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BidResponse [transactionId=" + transactionId + "]";
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    
    
     

    
}
