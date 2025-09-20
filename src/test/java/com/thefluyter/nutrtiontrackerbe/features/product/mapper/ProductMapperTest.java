package com.thefluyter.nutrtiontrackerbe.features.product.mapper;

import com.thefluyter.nutrtiontrackerbe.features.product.dto.ProductDTO;
import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    void shouldMapProductToDTO() {
        // GIVEN: A Product entity with all fields populated including nested NutritionFacts
        Product product = createSampleProductBanana();

        // WHEN: We map the Product entity to a ProductDTO using the mapper
        ProductDTO result = productMapper.toDTO(product);

        // THEN: The DTO should contain all the same data as the original entity
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(product.getId());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getDescription()).isEqualTo(product.getDescription());
        assertThat(result.getNutritionFacts()).isNotNull();
        assertThat(result.getNutritionFacts().getCalories()).isEqualTo(product.getNutritionFacts().getCalories());
        assertThat(result.getNutritionFacts().getProtein()).isEqualTo(product.getNutritionFacts().getProtein());
        assertThat(result.getNutritionFacts().getCarbohydrates()).isEqualTo(product.getNutritionFacts().getCarbohydrates());
        assertThat(result.getNutritionFacts().getFat()).isEqualTo(product.getNutritionFacts().getFat());
        assertThat(result.getNutritionFacts().getFiber()).isEqualTo(product.getNutritionFacts().getFiber());
        assertThat(result.getNutritionFacts().getSugar()).isEqualTo(product.getNutritionFacts().getSugar());
        assertThat(result.getNutritionFacts().getSodium()).isEqualTo(product.getNutritionFacts().getSodium());
        assertThat(result.getNutritionFacts().getVitaminC()).isEqualTo(product.getNutritionFacts().getVitaminC());
        assertThat(result.getNutritionFacts().getPotassium()).isEqualTo(product.getNutritionFacts().getPotassium());
    }

    @Test
    void shouldMapDTOToProduct() {
        // GIVEN: A ProductDTO with all fields populated including nested NutritionFactsDTO
        ProductDTO productDTO = createSampleProductDTO();

        // WHEN: We map the ProductDTO to a Product entity using the mapper
        Product result = productMapper.toEntity(productDTO);

        // THEN: The entity should contain all the same data as the original DTO
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(productDTO.getId());
        assertThat(result.getName()).isEqualTo(productDTO.getName());
        assertThat(result.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(result.getNutritionFacts()).isNotNull();
        assertThat(result.getNutritionFacts().getCalories()).isEqualTo(productDTO.getNutritionFacts().getCalories());
        assertThat(result.getNutritionFacts().getProtein()).isEqualTo(productDTO.getNutritionFacts().getProtein());
        assertThat(result.getNutritionFacts().getCarbohydrates()).isEqualTo(productDTO.getNutritionFacts().getCarbohydrates());
        assertThat(result.getNutritionFacts().getFat()).isEqualTo(productDTO.getNutritionFacts().getFat());
        assertThat(result.getNutritionFacts().getFiber()).isEqualTo(productDTO.getNutritionFacts().getFiber());
        assertThat(result.getNutritionFacts().getSugar()).isEqualTo(productDTO.getNutritionFacts().getSugar());
        assertThat(result.getNutritionFacts().getSodium()).isEqualTo(productDTO.getNutritionFacts().getSodium());
        assertThat(result.getNutritionFacts().getVitaminC()).isEqualTo(productDTO.getNutritionFacts().getVitaminC());
        assertThat(result.getNutritionFacts().getPotassium()).isEqualTo(productDTO.getNutritionFacts().getPotassium());
    }

    @Test
    void shouldMapDTOToProductForCreate() {
        // GIVEN: A ProductDTO with an ID that should be ignored during creation
        ProductDTO productDTO = createSampleProductDTO();
        productDTO.setId(999L); // Set an ID that should be ignored

        // WHEN: We map the ProductDTO to a Product entity for creation (ignoring ID)
        Product result = productMapper.toEntityForCreate(productDTO);

        // THEN: The entity should contain all data except the ID should be null (ignored)
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getName()).isEqualTo(productDTO.getName());
        assertThat(result.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(result.getNutritionFacts()).isNotNull();
        assertThat(result.getNutritionFacts().getCalories()).isEqualTo(productDTO.getNutritionFacts().getCalories());
    }

    @Test
    void shouldMapProductListToDTOList() {
        // GIVEN: A list of Product entities with different data
        List<Product> products = Arrays.asList(
            createSampleProductBanana(),
            createSampleProductApple()
        );

        // WHEN: We map the list of Product entities to a list of ProductDTOs
        List<ProductDTO> result = productMapper.toDTOList(products);

        // THEN: The DTO list should contain the same number of items with matching data
        assertThat(result)
            .isNotNull()
            .hasSize(2);

        assertThat(result.get(0).getName()).isEqualTo(products.get(0).getName());
        assertThat(result.get(1).getName()).isEqualTo(products.get(1).getName());
    }

    @Test
    void shouldMapEmptyProductListToEmptyDTOList() {
        // GIVEN: An empty list of Product entities
        List<Product> products = List.of();

        // WHEN: We map the empty list of Product entities to DTOs
        List<ProductDTO> result = productMapper.toDTOList(products);

        // THEN: The result should be an empty list
        assertThat(result)
            .isNotNull()
            .isEmpty();
    }

    @Test
    void shouldMapNullProductToNullDTO() {
        // GIVEN: A null Product entity
        // WHEN: We attempt to map null to a ProductDTO
        ProductDTO result = productMapper.toDTO(null);

        // THEN: The result should be null
        assertThat(result).isNull();
    }

    @Test
    void shouldMapNullDTOToNullProduct() {
        // GIVEN: A null ProductDTO
        // WHEN: We attempt to map null to a Product entity
        Product result = productMapper.toEntity(null);

        // THEN: The result should be null
        assertThat(result).isNull();
    }

    @Test
    void shouldMapNullDTOToNullProductForCreate() {
        // GIVEN: A null ProductDTO
        // WHEN: We attempt to map null to a Product entity for creation
        Product result = productMapper.toEntityForCreate(null);

        // THEN: The result should be null
        assertThat(result).isNull();
    }

    @Test
    void shouldMapNullProductListToNullDTOList() {
        // GIVEN: A null list of Product entities
        // WHEN: We attempt to map null to a list of ProductDTOs
        List<ProductDTO> result = productMapper.toDTOList(null);

        // THEN: The result should be null
        assertThat(result).isNull();
    }

    @Test
    void shouldMapProductWithNullNutritionFacts() {
        // GIVEN: A Product entity with null NutritionFacts
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setNutritionFacts(null);

        // WHEN: We map the Product entity to a ProductDTO
        ProductDTO result = productMapper.toDTO(product);

        // THEN: The DTO should preserve the null NutritionFacts and other fields
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Product");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getNutritionFacts()).isNull();
    }

    @Test
    void shouldMapDTOWithNullNutritionFacts() {
        // GIVEN: A ProductDTO with null NutritionFactsDTO
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setNutritionFacts(null);

        // WHEN: We map the ProductDTO to a Product entity
        Product result = productMapper.toEntity(productDTO);

        // THEN: The entity should preserve the null NutritionFacts and other fields
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test Product");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getNutritionFacts()).isNull();
    }

    private Product createSampleProductBanana() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Banana");
        product.setDescription("A yellow tropical fruit rich in potassium and vitamin C");
        
        Product.NutritionFacts nutritionFacts = new Product.NutritionFacts();
        nutritionFacts.setCalories(89.0);
        nutritionFacts.setProtein(1.1);
        nutritionFacts.setCarbohydrates(22.8);
        nutritionFacts.setFat(0.3);
        nutritionFacts.setFiber(2.6);
        nutritionFacts.setSugar(12.2);
        nutritionFacts.setSodium(1.0);
        nutritionFacts.setVitaminC(8.7);
        nutritionFacts.setPotassium(358.0);
        
        product.setNutritionFacts(nutritionFacts);
        return product;
    }

    private Product createSampleProductApple() {
        Product product = new Product();
        product.setId(2L);
        product.setName("Apple");
        product.setDescription("A crisp and sweet fruit, great source of fiber and vitamin C");
        
        Product.NutritionFacts nutritionFacts = new Product.NutritionFacts();
        nutritionFacts.setCalories(52.0);
        nutritionFacts.setProtein(0.3);
        nutritionFacts.setCarbohydrates(13.8);
        nutritionFacts.setFat(0.2);
        nutritionFacts.setFiber(2.4);
        nutritionFacts.setSugar(10.4);
        nutritionFacts.setSodium(1.0);
        nutritionFacts.setVitaminC(4.6);
        nutritionFacts.setPotassium(107.0);
        
        product.setNutritionFacts(nutritionFacts);
        return product;
    }

    private ProductDTO createSampleProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Banana");
        productDTO.setDescription("A yellow tropical fruit rich in potassium and vitamin C");
        
        ProductDTO.NutritionFactsDTO nutritionFacts = new ProductDTO.NutritionFactsDTO();
        nutritionFacts.setCalories(89.0);
        nutritionFacts.setProtein(1.1);
        nutritionFacts.setCarbohydrates(22.8);
        nutritionFacts.setFat(0.3);
        nutritionFacts.setFiber(2.6);
        nutritionFacts.setSugar(12.2);
        nutritionFacts.setSodium(1.0);
        nutritionFacts.setVitaminC(8.7);
        nutritionFacts.setPotassium(358.0);
        
        productDTO.setNutritionFacts(nutritionFacts);
        return productDTO;
    }
}
