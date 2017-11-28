package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.BoughtProduct;
import de.yannickmortier.orderservice.domain.Order;
import de.yannickmortier.orderservice.dto.ProductDto;

import java.util.List;

public interface BoughtProductService {
    BoughtProduct save(BoughtProduct boughtProduct);
    void addProductsToOrder(Order order, List<ProductDto> products);
}
