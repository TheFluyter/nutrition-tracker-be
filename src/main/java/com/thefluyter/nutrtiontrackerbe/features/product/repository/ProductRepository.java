package com.thefluyter.nutrtiontrackerbe.features.product.repository;

import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY p.name ASC")
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Product p WHERE p.nutritionFacts.calories BETWEEN :minCalories AND :maxCalories ORDER BY p.name ASC")
    List<Product> findByNutritionFactsCaloriesBetween(double minCalories, double maxCalories);
    
    @Query("SELECT p FROM Product p WHERE p.nutritionFacts.protein BETWEEN :minProtein AND :maxProtein ORDER BY p.name ASC")
    List<Product> findByNutritionFactsProteinBetween(double minProtein, double maxProtein);
    
    @Query("SELECT p FROM Product p ORDER BY p.name ASC")
    List<Product> findAllOrderedByName();
}
