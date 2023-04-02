package com.eblj.catalog.repositories;

import com.eblj.catalog.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(" select DISTINCT(obj) from Order obj left join fetch obj.items where obj.id = :id ")
    List<Order> findByIdFetchOrder(@Param("id") Long id);

}
