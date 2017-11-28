package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.User;
import de.yannickmortier.orderservice.dto.ProductDto;
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
public class DefaultProductService implements ProductService {

    private final RestTemplate restTemplate;
    private final static String PRODUCTS_URL = "http://localhost:8081/products";

    @Override
    public List<ProductDto> getProductsForUser(User user) {
        MultiValueMap<String, String> headers = AuthUtil.createHeadersForUser(user);
        RequestEntity requestEntity = new RequestEntity(headers, HttpMethod.GET, URI.create(PRODUCTS_URL));
        ResponseEntity<List<ProductDto>> exchange = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<List<ProductDto>>() {});

        return exchange.getBody();
    }
}
