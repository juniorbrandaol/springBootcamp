package com.eblj.catalog.repositories;

import com.eblj.catalog.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eblj.catalog.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //COALESCE
    @Query("SELECT DISTINCT obj FROM Product obj "
            +" INNER JOIN obj.categories cats"
            +" WHERE ( COALESCE(:categories) IS NULL OR cats IN :categories )"
            +" AND "
            +"( :name = '' OR LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%')) )")
    Page<Product> findProducts(List<Category> categories,String name, Pageable pageable);

    /*join fetch busca já os produtos juntamente com o produto juntamente com os objetos das categorias*/
    @Query("Select obj FROM Product obj JOIN FETCH obj.categories WHERE obj IN :products")
    List<Product> findProductsWithCategories(List<Product> products);


/******** SQL NATIVE ****************/
    @Query(nativeQuery = true,value =
            " SELECT DISTINCT  prod.* FROM tb_product prod, tb_category cats, tb_product_category  pc"
                    +" INNER JOIN  tb_product_category ON prod.id = pc.product_id"
                    +" WHERE pc.category_id = :category "
                    +" AND cats.id =  pc.category_id"
    )
    Page<Product> findProductsSQL(Long category, Pageable pageable);

}
