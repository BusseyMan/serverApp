package com.distributed.serverApp.repo;

 
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
 import com.distributed.serverApp.entity.Users;

/**
 *
 * @author Hussein Alhassan
 */

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String userName);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByResetToken(String resetToken);
    Optional<Users> findByActivationToken(String resetToken);
    @Query("SELECT u FROM Users u WHERE  u.username = :username")
    List<Users> findByUserEmail(@Param("username")String username);
    Optional<Users> findByTransactionId(String transactionId);
    public Optional<Users> findByUsernameAndSecreteCode(String userName, String password);
    Page<Users> findByUserType(String type, Pageable of);

}