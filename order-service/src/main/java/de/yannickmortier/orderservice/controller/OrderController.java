package de.yannickmortier.orderservice.controller;

import de.yannickmortier.orderservice.domain.Order;
import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.CreditCardDto;
import de.yannickmortier.orderservice.dto.OrderDto;
import de.yannickmortier.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<OrderDto> placeOrder(@Valid @RequestBody CreditCardDto creditCard,
                                               BindingResult result,
                                               @AuthenticationPrincipal User user) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Order order = orderService.placeOrderForUserWithCreditCard(user, creditCard);
            return ResponseEntity.ok(mapToDto(order));
        } catch (OrderService.NoProductsInCartException e) {
            log.warn("No products in cart!");
            return ResponseEntity.badRequest().build();
        } catch (OrderService.ProductInCartNotFoundException e) {
            log.warn("One of the products in the cart is no longer in the store!");
            return ResponseEntity.badRequest().build();
        } catch (OrderService.CreditCardProcessException e) {
            log.warn("Credit card could not be processed!");
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<OrderDto>> getAllOrders(@AuthenticationPrincipal User user) {
        List<Order> ordersForUser = orderService.getAllOrdersForUser(user);
        List<OrderDto> orderDtos = ordersForUser.stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(orderDtos);
    }

    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getOrder(@PathVariable UUID orderId,
                                             @AuthenticationPrincipal User user) {
        Order orderForUser = orderService.getOrderForUser(user, orderId);
        if (orderForUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapToDto(orderForUser));
    }


    private OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        orderDto.setBoughtProducts(order.getProducts());
        return orderDto;
    }

}
