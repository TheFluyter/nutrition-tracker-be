package com.thefluyter.nutrtiontrackerbe.features.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Embedded
    private NutritionFacts nutritionFacts;
    
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NutritionFacts {
        @Column(name = "calories")
        private double calories;
        
        @Column(name = "protein")
        private double protein; // grams
        
        @Column(name = "carbohydrates")
        private double carbohydrates; // grams
        
        @Column(name = "fat")
        private double fat; // grams
        
        @Column(name = "fiber")
        private double fiber; // grams
        
        @Column(name = "sugar")
        private double sugar; // grams
        
        @Column(name = "sodium")
        private double sodium; // milligrams
        
        @Column(name = "vitamin_c")
        private double vitaminC; // milligrams
        
        @Column(name = "potassium")
        private double potassium; // milligrams
    }
}
