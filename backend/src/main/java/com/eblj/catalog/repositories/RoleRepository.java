package com.eblj.catalog.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.eblj.catalog.entities.Role;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
