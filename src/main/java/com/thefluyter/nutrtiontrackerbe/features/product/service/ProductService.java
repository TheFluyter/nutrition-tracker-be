package com.thefluyter.nutrtiontrackerbe.features.product.service;

import com.thefluyter.nutrtiontrackerbe.features.product.dto.ProductDTO;
import com.thefluyter.nutrtiontrackerbe.features.product.mapper.ProductMapper;
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
    private final ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAllOrderedByName();
        return productMapper.toDTOList(products);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByCalorieRange(double minCalories, double maxCalories) {
        List<Product> products = productRepository.findByNutritionFactsCaloriesBetween(minCalories, maxCalories);
        return productMapper.toDTOList(products);
    }

    public List<ProductDTO> getProductsByProteinRange(double minProtein, double maxProtein) {
        List<Product> products = productRepository.findByNutritionFactsProteinBetween(minProtein, maxProtein);
        return productMapper.toDTOList(products);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntityForCreate(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setNutritionFacts(productMapper.toEntity(productDTO).getNutritionFacts());
        
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        productRepository.delete(product);
    }
}
