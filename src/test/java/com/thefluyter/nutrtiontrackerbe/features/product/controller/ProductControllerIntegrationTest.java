package com.thefluyter.nutrtiontrackerbe.features.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thefluyter.nutrtiontrackerbe.features.product.dto.ProductDTO;
import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import com.thefluyter.nutrtiontrackerbe.features.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class ProductControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        productRepository.deleteAll();
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        // GIVEN: Two products exist in the database with different nutrition facts
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        Product apple = createSampleProduct("Apple", "A crisp and sweet fruit", 52.0);
        productRepository.saveAll(List.of(banana, apple));

        // WHEN: We make a GET request to retrieve all products
        // THEN: We should receive a 200 OK response with both products as DTOs in alphabetical order
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[1].id").exists())
            .andExpect(jsonPath("$[0].name", is("Apple")))  // Apple should come first alphabetically
            .andExpect(jsonPath("$[1].name", is("Banana"))) // Banana should come second alphabetically
            .andExpect(content().json("""
                [
                  {
                    "name": "Apple",
                    "description": "A crisp and sweet fruit",
                    "nutritionFacts": {
                      "calories": 52.0,
                      "protein": 1.0,
                      "carbohydrates": 20.0,
                      "fat": 0.5,
                      "fiber": 2.0,
                      "sugar": 15.0,
                      "sodium": 1.0,
                      "vitaminC": 10.0,
                      "potassium": 200.0
                    }
                  },
                  {
                    "name": "Banana",
                    "description": "A yellow tropical fruit",
                    "nutritionFacts": {
                      "calories": 89.0,
                      "protein": 1.0,
                      "carbohydrates": 20.0,
                      "fat": 0.5,
                      "fiber": 2.0,
                      "sugar": 15.0,
                      "sodium": 1.0,
                      "vitaminC": 10.0,
                      "potassium": 200.0
                    }
                  }
                ]
                """));
    }

    @Test
    void shouldGetProductById() throws Exception {
        // GIVEN: A product exists in the database with known data
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        Product savedBanana = productRepository.save(banana);

        // WHEN: We make a GET request to retrieve the product by its ID
        // THEN: We should receive a 200 OK response with the product as a DTO
        mockMvc.perform(get("/api/products/{id}", savedBanana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("Banana")));
    }

    @Test
    void shouldReturn404WhenProductNotFound() throws Exception {
        // GIVEN: No product exists with ID 999
        // WHEN: We make a GET request to retrieve a non-existent product
        // THEN: We should receive a 404 Not Found response
        mockMvc.perform(get("/api/products/{id}", 999L))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldSearchProductsByName() throws Exception {
        // GIVEN: Three products exist in the database with names containing different letters
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        Product apple = createSampleProduct("Apple", "A crisp and sweet fruit", 52.0);
        Product orange = createSampleProduct("Orange", "A citrus fruit", 47.0);
        productRepository.saveAll(List.of(banana, apple, orange));

        // WHEN: We search for products with name containing "an"
        // THEN: We should receive products that match the search criteria in alphabetical order
        mockMvc.perform(get("/api/products/search")
                .param("name", "an"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name", is("Banana")))  // Banana comes before Orange alphabetically
            .andExpect(jsonPath("$[1].name", is("Orange")));
    }

    @Test
    void shouldGetProductsByCalorieRange() throws Exception {
        // GIVEN: Three products exist with different calorie values (47, 52, 89)
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        Product apple = createSampleProduct("Apple", "A crisp and sweet fruit", 52.0);
        Product orange = createSampleProduct("Orange", "A citrus fruit", 47.0);
        productRepository.saveAll(List.of(banana, apple, orange));

        // WHEN: We filter products by calorie range 50-100
        // THEN: We should receive products within that range in alphabetical order
        mockMvc.perform(get("/api/products/calories")
                .param("min", "50.0")
                .param("max", "100.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].name", is("Apple")))   // Apple comes before Banana alphabetically
            .andExpect(jsonPath("$[1].name", is("Banana")));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        // GIVEN: A ProductDTO with product data to be created
        ProductDTO bananaDTO = createSampleProductDTO("Banana", "A yellow tropical fruit", 89.0);

        // WHEN: We make a POST request to create a new product
        // THEN: We should receive a 201 Created response with the saved product as a DTO
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bananaDTO)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", is("Banana")))
            .andExpect(jsonPath("$.description", is("A yellow tropical fruit")))
            .andExpect(jsonPath("$.nutritionFacts.calories", is(89.0)))
            .andExpect(jsonPath("$.id").exists());

        // AND: The product should be persisted in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1);
        assertThat(products.getFirst().getName()).isEqualTo("Banana");
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        // GIVEN: A product exists in the database and we have updated data
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        Product savedBanana = productRepository.save(banana);

        ProductDTO bananaDTO = createSampleProductDTO("Updated Banana", "Updated description", 95.0);
        bananaDTO.setId(savedBanana.getId());

        // WHEN: We make a PUT request to update the existing product
        // THEN: We should receive a 200 OK response with the updated product as a DTO
        mockMvc.perform(put("/api/products/{id}", savedBanana.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bananaDTO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("""
                {
                    "name": "Updated Banana",
                    "description": "Updated description",
                    "nutritionFacts": {
                        "calories": 95.0,
                        "protein": 1.0,
                        "carbohydrates": 20.0,
                        "fat": 0.5,
                        "fiber": 2.0,
                        "sugar": 15.0,
                        "sodium": 1.0,
                        "vitaminC": 10.0,
                        "potassium": 200.0
                    }
                }
                """));

        // AND: The product should be updated in the database
        Product updatedProduct = productRepository.findById(savedBanana.getId()).orElseThrow();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Banana");
        assertThat(updatedProduct.getDescription()).isEqualTo("Updated description");
        assertThat(updatedProduct.getNutritionFacts().getCalories()).isEqualTo(95.0);
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistentProduct() throws Exception {
        // GIVEN: No product exists with ID 999 and we have update data
        ProductDTO bananaDTO = createSampleProductDTO("Banana", "A yellow tropical fruit", 89.0);

        // WHEN: We make a PUT request to update a non-existent product
        // THEN: We should receive a 404 Not Found response
        mockMvc.perform(put("/api/products/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bananaDTO)))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // GIVEN: A product exists in the database
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        Product savedBanana = productRepository.save(banana);

        // WHEN: We make a DELETE request to remove the product
        // THEN: We should receive a 204 No Content response
        mockMvc.perform(delete("/api/products/{id}", savedBanana.getId()))
            .andExpect(status().isNoContent());

        // AND: The product should be removed from the database
        assertThat(productRepository.findById(savedBanana.getId())).isEmpty();
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentProduct() throws Exception {
        // GIVEN: No product exists with ID 999
        // WHEN: We make a DELETE request to remove a non-existent product
        // THEN: We should receive a 404 Not Found response
        mockMvc.perform(delete("/api/products/{id}", 999L))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() throws Exception {
        // GIVEN: No products exist in the database
        // WHEN: We make a GET request to retrieve all products
        // THEN: We should receive an empty list
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsMatchSearch() throws Exception {
        // GIVEN: A product exists but with a name that won't match our search
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        productRepository.save(banana);

        // WHEN: We search for products with name containing "nonexistent"
        // THEN: We should receive an empty list
        mockMvc.perform(get("/api/products/search")
                .param("name", "nonexistent"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsInCalorieRange() throws Exception {
        // GIVEN: A product exists with 89 calories, but we search for a much higher range
        Product banana = createSampleProduct("Banana", "A yellow tropical fruit", 89.0);
        productRepository.save(banana);

        // WHEN: We filter products by calorie range 200-300
        // THEN: We should receive an empty list
        mockMvc.perform(get("/api/products/calories")
                .param("min", "200.0")
                .param("max", "300.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(0)));
    }

    private Product createSampleProduct(String name, String description, double calories) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);

        Product.NutritionFacts nutritionFacts = new Product.NutritionFacts();
        nutritionFacts.setCalories(calories);
        nutritionFacts.setProtein(1.0);
        nutritionFacts.setCarbohydrates(20.0);
        nutritionFacts.setFat(0.5);
        nutritionFacts.setFiber(2.0);
        nutritionFacts.setSugar(15.0);
        nutritionFacts.setSodium(1.0);
        nutritionFacts.setVitaminC(10.0);
        nutritionFacts.setPotassium(200.0);

        product.setNutritionFacts(nutritionFacts);
        return product;
    }

    private ProductDTO createSampleProductDTO(String name, String description, double calories) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription(description);

        ProductDTO.NutritionFactsDTO nutritionFacts = new ProductDTO.NutritionFactsDTO();
        nutritionFacts.setCalories(calories);
        nutritionFacts.setProtein(1.0);
        nutritionFacts.setCarbohydrates(20.0);
        nutritionFacts.setFat(0.5);
        nutritionFacts.setFiber(2.0);
        nutritionFacts.setSugar(15.0);
        nutritionFacts.setSodium(1.0);
        nutritionFacts.setVitaminC(10.0);
        nutritionFacts.setPotassium(200.0);

        productDTO.setNutritionFacts(nutritionFacts);
        return productDTO;
    }
}
