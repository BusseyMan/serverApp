package com.distributed.serverApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.distributed.serverApp.distributed.AutionServer;
import com.distributed.serverApp.distributed.AutionServerImp;


@Configuration
public class Config {

    @Autowired
    UserDetailsService userDetailsService;
    
    /**
     * Then we'll define a bean that implements the interface. 
     * This is the bean that will actually execute the business logic on the server:
     * @return
     */
    @Bean 
    @Primary
    AutionServer autionServer() {
        return new AutionServerImp();
    }
    
    /*
     * Let's continue declaring the Exporter that makes the service available to 
     * clients. In this case, we'll use the RmiServiceExporter:
     * 
     * Through setServiceInterface() we provide a reference to the interface that will be 
     * made remotely callable.
     * 
     * We should also provide a reference to the object actually executing the
     * method with setService().
     * 
     * We should also set a service name, that allows identifying the exposed service 
     * in the RMI registry. 
     * 
     * With the given configuration the client will be able to contact the AutionServer 
     * at the following URL: rmi://HOST:1199/AutionServer.
     */
    @Bean 
    RmiServiceExporter exporter(AutionServer implementation) {
        Class<AutionServer> serviceInterface = AutionServer.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(2020); 
        return exporter;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.csrf((csrf) -> csrf.disable())
                .authorizeRequests(auth -> {
                    auth.antMatchers("/","/login", "/logout", "/setup").permitAll();
                    auth.anyRequest().authenticated();
                })
                .httpBasic((basic) -> {
                    
                })
                .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/logoutSuccessful");
                })
                .exceptionHandling(exc -> {
                    exc.accessDeniedPage("/access-denied") ;
                })
               .build();
    }
    
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    
}
