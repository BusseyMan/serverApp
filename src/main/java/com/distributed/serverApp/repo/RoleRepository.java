package com.distributed.serverApp.repo;

  
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
 import com.distributed.serverApp.entity.Role;

/**
 *
 * @author Hussein Alhassan
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
    public Optional<Role> findByName(String name);
}

