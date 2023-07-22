package com.distributed.serverApp.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.distributed.serverApp.entity.Customer;
import com.distributed.serverApp.JsfUtil;
import com.distributed.serverApp.entity.Role;
import com.distributed.serverApp.entity.Users;
import com.distributed.serverApp.repo.UserRepository;
import com.distributed.serverApp.service.CustomerService;



@RestController
public class MainController {
     
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String index(){
        return "Distributed Server started successfully";
    }

    //setup?un=admin&ps=start123&rl=ROLE_ADMIN
    @GetMapping("/setup")
    public boolean createAdmin(String un,String ps,String rl){
        try{
             
            Optional<Users> opp = userRepository.findByUsername("admin");
            if(opp.isPresent()){
                return true;
            }
            
            Users users = new Users();
            users.setFullName("Admin");
            users.setActive(false);
            users.setPassword(encoder.encode(ps));
            users.setUsername(un);
            users.setUserType("Staff");
            users.setActivationToken(JsfUtil.generateTransactionNumber());

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName(rl);
            roles.add(role);
            users.setRoles(roles);

           return save(users);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
     
    }

    public boolean save(Users user){
        try{
            userRepository.save(user);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @PostMapping("/sign-up")
    @Transactional
    public ResponseEntity<String> signUp(@RequestBody dbo.Profile profile){
        try {
            Optional<Users> opp = userRepository.findByUsername(profile.getUsername());
            if(opp.isPresent()){
                return new ResponseEntity<String>("Invalid Email",
                 HttpStatus.OK);
            }
            
            Users users = new Users();
            users.setFullName(profile.getFullName());
            users.setActive(true);
            users.setPassword(encoder.encode(profile.getPassword()));
            users.setUsername(profile.getUsername());
            users.setUserType(profile.getUserType());
            users.setActivationToken(JsfUtil.generateTransactionNumber());

            Customer customer = new Customer();
            customer.setEmail(profile.getUsername());
            customer.setName(profile.getFullName());
            customer.setTelephone(profile.getTelephone());

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName("ROLE_BUYER");
            roles.add(role);
            users.setRoles(roles);

            customerService.save(customer);
            String status = save(users) ? "SUCCESS" : "FAILED";

            return new ResponseEntity<String>(status, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
