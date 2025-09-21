package com.thefluyter.nutrtiontrackerbe.features.product.controller;

import com.thefluyter.nutrtiontrackerbe.features.product.dto.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseUtil.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseUtil.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String name) {
        List<ProductDTO> products = productService.searchProductsByName(name);
        return ResponseUtil.ok(products);
    }

    @GetMapping("/calories")
    public ResponseEntity<List<ProductDTO>> getProductsByCalorieRange(
            @RequestParam double min, 
            @RequestParam double max) {
        List<ProductDTO> products = productService.getProductsByCalorieRange(min, max);
        return ResponseUtil.ok(products);
    }

    @GetMapping("/protein")
    public ResponseEntity<List<ProductDTO>> getProductsByProteinRange(
            @RequestParam double min, 
            @RequestParam double max) {
        List<ProductDTO> products = productService.getProductsByProteinRange(min, max);
        return ResponseUtil.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseUtil.created(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseUtil.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
