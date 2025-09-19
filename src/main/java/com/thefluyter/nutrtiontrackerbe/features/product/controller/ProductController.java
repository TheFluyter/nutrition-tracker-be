package com.thefluyter.nutrtiontrackerbe.features.product.controller;

import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import com.thefluyter.nutrtiontrackerbe.features.product.service.ProductService;
import com.thefluyter.nutrtiontrackerbe.shared.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow CORS for frontend integration
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseUtil.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseUtil.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseUtil.ok(products);
    }

    @GetMapping("/calories")
    public ResponseEntity<List<Product>> getProductsByCalorieRange(
            @RequestParam double min, 
            @RequestParam double max) {
        List<Product> products = productService.getProductsByCalorieRange(min, max);
        return ResponseUtil.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseUtil.created(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseUtil.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
