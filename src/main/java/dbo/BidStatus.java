package dbo;

import java.io.Serializable;

public class BidStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String status;
    private BidResponse bid;
    
    
    public BidStatus() {
    }

    

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


 


    public BidResponse getBid() {
        return bid;
    }



    public BidStatus(Long bidId, String status, Object object) {
        this.status = status;
    }



    public BidStatus(String status) {
        this.status = status;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    

    
}
