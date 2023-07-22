package com.distributed.serverApp.service;

 

import java.util.Optional;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import com.distributed.serverApp.entity.Users;
import com.distributed.serverApp.repo.UserRepository;
/**
 *
 * @author Hussein Alhassan
 */

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;
    
    

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByUsername(userName);
        if(user.isPresent()){
            Users currentUser = user.get();
            setAttribute(currentUser);
        }
        user.orElseThrow(()->new UsernameNotFoundException("Not found "+ userName));
        
        MyUserDetails details = user.map(MyUserDetails::new).get();
        
        return details;
               
    }

    //@Override
    public UserDetails loadUserByUsername2(String userName) throws UsernameNotFoundException {
        //String ip = getClientIP();
        
        Optional<Users> user = userRepository.findByUsername(userName);
        if(user.isPresent()){
            setAttribute(user.get());
        }
       
        user.orElseThrow(()->new UsernameNotFoundException("Not found "+ userName));
        MyUserDetails details = user.map(MyUserDetails::new).get();
        
        return details;
    
                
    }
    
   
    
    private void setAttribute(Users user ) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if(ra !=null){ 
            ra.setAttribute("current_user", user, RequestAttributes.SCOPE_SESSION);
        }
    }

  
}

