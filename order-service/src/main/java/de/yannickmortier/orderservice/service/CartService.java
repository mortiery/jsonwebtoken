package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.ProductIdDto;

import java.util.List;

public interface CartService {
    List<ProductIdDto> getProductsInCartForUser(User user);
    void emptyCartForUser(User user);
}
