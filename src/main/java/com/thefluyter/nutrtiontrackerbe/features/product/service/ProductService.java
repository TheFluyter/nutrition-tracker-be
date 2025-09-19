package com.thefluyter.nutrtiontrackerbe.features.product.service;

import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final List<Product> mockProducts = initializeMockProducts();

    public List<Product> getAllProducts() {
        return new ArrayList<>(mockProducts);
    }

    public Product getProductById(Long id) {
        return mockProducts.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private List<Product> initializeMockProducts() {
        List<Product> products = new ArrayList<>();
        
        // Banana product
        Product banana = new Product();
        banana.setId(1L);
        banana.setName("Banana");
        banana.setDescription("A yellow tropical fruit rich in potassium and vitamin C");
        banana.setNutritionFacts(new Product.NutritionFacts(
            89.0,    // calories
            1.1,     // protein (g)
            22.8,    // carbohydrates (g)
            0.3,     // fat (g)
            2.6,     // fiber (g)
            12.2,    // sugar (g)
            1.0,     // sodium (mg)
            8.7,     // vitamin C (mg)
            358.0    // potassium (mg)
        ));
        products.add(banana);

        // Apple product
        Product apple = new Product();
        apple.setId(2L);
        apple.setName("Apple");
        apple.setDescription("A crisp and sweet fruit, great source of fiber and vitamin C");
        apple.setNutritionFacts(new Product.NutritionFacts(
            52.0,    // calories
            0.3,     // protein (g)
            13.8,    // carbohydrates (g)
            0.2,     // fat (g)
            2.4,     // fiber (g)
            10.4,    // sugar (g)
            1.0,     // sodium (mg)
            4.6,     // vitamin C (mg)
            107.0    // potassium (mg)
        ));
        products.add(apple);

        return products;
    }
}
