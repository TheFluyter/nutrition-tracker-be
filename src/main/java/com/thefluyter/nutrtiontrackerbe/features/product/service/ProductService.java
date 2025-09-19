package com.thefluyter.nutrtiontrackerbe.features.product.service;

import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import com.thefluyter.nutrtiontrackerbe.features.product.repository.ProductRepository;
import com.thefluyter.nutrtiontrackerbe.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByCalorieRange(double minCalories, double maxCalories) {
        return productRepository.findByNutritionFactsCaloriesBetween(minCalories, maxCalories);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setNutritionFacts(productDetails.getNutritionFacts());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
