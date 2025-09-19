package com.thefluyter.nutrtiontrackerbe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private NutritionFacts nutritionFacts;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NutritionFacts {
        private double calories;
        private double protein; // grams
        private double carbohydrates; // grams
        private double fat; // grams
        private double fiber; // grams
        private double sugar; // grams
        private double sodium; // milligrams
        private double vitaminC; // milligrams
        private double potassium; // milligrams
    }
}
