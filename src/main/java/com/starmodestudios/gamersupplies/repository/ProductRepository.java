package com.starmodestudios.gamersupplies.repository;

import com.starmodestudios.gamersupplies.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Paginated list of all products
    Page<Product> findAll(Pageable pageable);

    // Filter by category
    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category = :category)")
    Page<Product> findByCategory(
            @Param("category") String category,
            Pageable pageable
    );

    // Filter by category and search by name
    @Query("SELECT p FROM Product p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:keyword  IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> findByCategoryAndKeyword(
            @Param("category") String category,
            @Param("keyword")  String keyword,
            Pageable pageable
    );

    // Check for duplicate SKU before saving
    boolean existsBySku(String sku);
}
