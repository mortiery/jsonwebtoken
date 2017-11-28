package de.yannickmortier.cartservice.service;

import de.yannickmortier.cartservice.domain.CartProduct;
import de.yannickmortier.cartservice.domain.User;

import java.util.List;
import java.util.UUID;

public interface CartProductService {
    CartProduct addProductToCart(UUID userId, UUID productId);
    List<CartProduct> findProductsForUser(User user);
    void removeProductFromCart(UUID userId, UUID productId);
    void emptyCartForUser(User user);

    boolean productIsValidForUser(User user, UUID productId);

    class ProductAlreadyInCartException extends RuntimeException {

    }

    class ProductNotInCartException extends RuntimeException {

    }
}
