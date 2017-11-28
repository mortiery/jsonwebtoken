package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.Order;
import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.CreditCardDto;
import de.yannickmortier.orderservice.dto.ProductDto;
import de.yannickmortier.orderservice.dto.ProductIdDto;
import de.yannickmortier.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultOrderService implements OrderService {

    private final CartService cartService;
    private final ProductService productService;
    private final CreditCardService creditCardService;
    private final OrderRepository orderRepository;
    private final BoughtProductService boughtProductService;

    @Override
    @Transactional
    public Order placeOrderForUserWithCreditCard(User user, CreditCardDto creditCard) {
        List<ProductIdDto> productsInCartForUser = getProductsInCart(user);

        List<ProductDto> availableProducts = productService.getProductsForUser(user);
        checkAllProductsInCartAvailable(productsInCartForUser, availableProducts);

        List<ProductDto> productsInCartData = filterProductsInCart(productsInCartForUser, availableProducts);
        BigDecimal orderTotal = calculateOrderTotal(productsInCartData).orElse(new BigDecimal(0.0));

        boolean creditCardProcessed = creditCardService.processOrder(creditCard, orderTotal);
        if (!creditCardProcessed) {
            throw new CreditCardProcessException();
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderTotal(orderTotal);
        order.setCreditCardDigits(creditCard.getCreditCard().substring(12));
        saveOrder(order);

        boughtProductService.addProductsToOrder(order, productsInCartData);

        return order;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public Order getOrderForUser(User user, UUID orderId) {
        return orderRepository.findByUserAndId(user, orderId);
    }

    @Override
    public List<Order> getAllOrdersForUser(User user) {
        return orderRepository.findByUser(user);
    }

    private List<ProductIdDto> getProductsInCart(User user) {
        List<ProductIdDto> productsInCartForUser = cartService.getProductsInCartForUser(user);
        if (productsInCartForUser.isEmpty()) {
            throw new NoProductsInCartException();
        }
        return productsInCartForUser;
    }

    private void checkAllProductsInCartAvailable(List<ProductIdDto> productsInCartForUser, List<ProductDto> productDtos) {
        if (!productsInCartForUser.stream()
                .allMatch(productIdDto ->
                        productDtos.stream().anyMatch(productDto -> productDto.getId().equals(productIdDto.getId())))) {
            throw new ProductInCartNotFoundException();
        }
    }

    private List<ProductDto> filterProductsInCart(List<ProductIdDto> productsInCartForUser, List<ProductDto> availableProducts) {
        return availableProducts.stream()
                .filter(productDto -> productsInCartForUser.stream().anyMatch(productIdDto -> productIdDto.getId().equals(productDto.getId())))
                .collect(Collectors.toList());
    }

    private Optional<BigDecimal> calculateOrderTotal(List<ProductDto> productsInCartData) {
        return productsInCartData.stream()
                .map(ProductDto::getPrice)
                .reduce((sum, price) -> sum.add(price));
    }
}
