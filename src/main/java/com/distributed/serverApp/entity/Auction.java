package com.distributed.serverApp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "auction")
public class Auction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemName;
    private String pic;
    private BigDecimal startPrice;
    private BigDecimal reservedPrice;
    private LocalDate deadline;

    
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Customer seller; 
    @Column(unique = true)
    private String transactionId;

    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    private Customer winner;
    @Column(columnDefinition = "varchar(10) default 'Active'")
    private String status;

    

    public Auction(Integer id, String itemName, String pic, BigDecimal startPrice, BigDecimal reservedPrice,
            LocalDate deadline, Customer seller, String transactionId, Customer winner, String status) {
        this.id = id;
        this.itemName = itemName;
        this.pic = pic;
        this.startPrice = startPrice;
        this.reservedPrice = reservedPrice;
        this.deadline = deadline;
        this.seller = seller;
        this.transactionId = transactionId;
        this.winner = winner;
        this.status = status;
    }

    public Auction() {
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
     
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public Customer getSeller() {
        return seller;
    }
    public void setSeller(Customer seller) {
        this.seller = seller;
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
        Auction other = (Auction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Auction [itemName=" + itemName + "]";
    }
    public Customer getWinner() {
        return winner;
    }
    public void setWinner(Customer winner) {
        this.winner = winner;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    

    


}
