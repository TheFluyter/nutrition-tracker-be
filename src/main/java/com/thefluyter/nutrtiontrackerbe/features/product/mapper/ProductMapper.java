package com.thefluyter.nutrtiontrackerbe.features.product.mapper;

import com.thefluyter.nutrtiontrackerbe.features.product.dto.ProductDTO;
import com.thefluyter.nutrtiontrackerbe.features.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "nutritionFacts", source = "nutritionFacts")
    ProductDTO toDTO(Product product);

    @Mapping(target = "nutritionFacts", source = "nutritionFacts")
    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> toDTOList(List<Product> products);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nutritionFacts", source = "nutritionFacts")
    Product toEntityForCreate(ProductDTO productDTO);
}
