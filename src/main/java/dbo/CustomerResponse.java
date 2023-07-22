package dbo;

 
 
import java.io.Serializable;

import com.distributed.serverApp.entity.Customer;


public class CustomerResponse implements Serializable {
    private static final long serialVersionUID = 1L;
   
    private Integer id;
    private String name;
    private String telephone;
    private String email;
    private String transactionId;

    
    public CustomerResponse(Customer cux) {
        if (cux==null) {
            cux=new Customer();
        }
        this.id = cux.getId();
        this.name = cux.getName();
        this.telephone = cux.getTelephone();
        this.email = cux.getEmail();
        this.transactionId = cux.getTransactionId();
    }


    public CustomerResponse() {
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getTelephone() {
        return telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getEmail() {
        return email;
    }


    public String getTransactionId() {
        return transactionId;
    }


    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


    public void setEmail(String email) {
        this.email = email;
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
        CustomerResponse other = (CustomerResponse) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return   name  ;
    }

     
}
