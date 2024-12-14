package com.aegisultimateknologi.simple_ecommerce_service.mapper;

import com.aegisultimateknologi.simple_ecommerce_service.entity.Product;
import com.aegisultimateknologi.simple_ecommerce_service.entity.Sales;
import com.aegisultimateknologi.simple_ecommerce_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SalesMapper {

    SalesMapper INSTANCE = Mappers.getMapper(SalesMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Sales mapToSales(User user, Product product);

}
