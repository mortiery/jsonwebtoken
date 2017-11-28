package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.ProductIdDto;
import de.yannickmortier.orderservice.service.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultCartService implements CartService {

    private final RestTemplate restTemplate;
    private final static String CART_URL = "http://localhost:8082/cart";

    @Override
    public List<ProductIdDto> getProductsInCartForUser(User user) {
        MultiValueMap<String, String> headers = AuthUtil.createHeadersForUser(user);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create(CART_URL));
        ResponseEntity<List<ProductIdDto>> exchange = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ProductIdDto>>() {});

        return exchange.getBody();
    }

    @Override
    public void emptyCartForUser(User user) {
        MultiValueMap<String, String> headers = AuthUtil.createHeadersForUser(user);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.DELETE, URI.create(CART_URL));
        restTemplate.exchange(requestEntity, Void.class);
    }


}
