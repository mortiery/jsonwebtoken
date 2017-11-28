package de.yannickmortier.productservice.service;

import de.yannickmortier.productservice.domain.Product;
import de.yannickmortier.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts(boolean userIsVerified) {
        if (userIsVerified) {
            return productRepository.findAll();
        }

        else return productRepository.findByRequiresValidationIs(false);
    }
}
