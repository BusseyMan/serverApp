package com.distributed.serverApp.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.distributed.serverApp.JsfUtil;

import dbo.CustomerResponse;


@Entity
@Table(name = "customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String telephone;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String transactionId;

    
    public Customer() {
    }


    public Customer(CustomerResponse customerResponse) {
        this.id = customerResponse.getId();
        this.name = customerResponse.getName();
        this.telephone = customerResponse.getTelephone();
        this.email = customerResponse.getEmail();
        this.transactionId = customerResponse.getTransactionId();
    }


    public Customer(Integer id, String name, String transactionId) {
        this.id = id;
        this.name = name;
        this.transactionId = transactionId;
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email =email;
        this.transactionId = JsfUtil.generateTransactionNumber();
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
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Customer [name=" + name + "]";
    }

     
}
