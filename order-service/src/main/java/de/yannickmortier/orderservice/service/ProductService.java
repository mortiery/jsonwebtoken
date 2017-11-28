package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProductsForUser(User user);
}
