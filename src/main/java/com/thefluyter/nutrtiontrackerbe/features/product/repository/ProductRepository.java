package com.thefluyter.nutrtiontrackerbe.features.product.repository;

import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByNutritionFactsCaloriesBetween(double minCalories, double maxCalories);
}
