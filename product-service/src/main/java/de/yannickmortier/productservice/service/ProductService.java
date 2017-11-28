package de.yannickmortier.productservice.service;

import de.yannickmortier.productservice.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(boolean userIsVerified);
}
