package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.Order;
import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.CreditCardDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order placeOrderForUserWithCreditCard(User user, CreditCardDto creditCard);
    Order saveOrder(Order order);

    Order getOrderForUser(User user, UUID orderId);
    List<Order> getAllOrdersForUser(User user);

    // Thrown if there are no products in cart
    class NoProductsInCartException extends RuntimeException { }

    // Thrown if a product in the cart was not found
    class ProductInCartNotFoundException extends RuntimeException { }

    // Thrown if credit card could not be processed
    class CreditCardProcessException extends RuntimeException { }
}
