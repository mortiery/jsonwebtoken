package de.yannickmortier.cartservice.service;

import de.yannickmortier.cartservice.domain.CartProduct;
import de.yannickmortier.cartservice.domain.User;
import de.yannickmortier.cartservice.dto.ProductDto;
import de.yannickmortier.cartservice.repository.CartProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultCartProductService implements CartProductService {

    private final UserService userService;
    private final  CartProductRepository cartProductRepository;

    @Override
    @Transactional
    public CartProduct addProductToCart(UUID userId, UUID productId) {
        if (cartProductRepository.findByUserIdAndProductId(userId, productId) != null) {
            throw new ProductAlreadyInCartException();
        }

        User user = userService.findOne(userId);
        CartProduct cartProduct = new CartProduct(user, productId);
        cartProductRepository.saveAndFlush(cartProduct);
        return cartProduct;
    }

    @Override
    public List<CartProduct> findProductsForUser(User user) {
        return cartProductRepository.findByUser(user);
    }

    @Override
    public void removeProductFromCart(UUID userId, UUID productId) {
        CartProduct product = cartProductRepository.findByUserIdAndProductId(userId, productId);
        if (product == null) {
            throw new ProductNotInCartException();
        }
        else cartProductRepository.delete(product);
    }

    @Override
    @Transactional
    public void emptyCartForUser(User user) {
        cartProductRepository.emptyCartForUser(user);
    }

    @Override
    public boolean productIsValidForUser(User user, UUID productId) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + user.getCurrentToken());
        headers.add("Content-Type", "application/json");
        String uri = "http://localhost:8081/products";
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create(uri));
        ResponseEntity<List<ProductDto>> exchange = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ProductDto>>() {});

        return exchange.getBody().stream().anyMatch(productDto -> productDto.getId().equals(productId));
    }
}
