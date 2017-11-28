package de.yannickmortier.orderservice.service;

import de.yannickmortier.orderservice.domain.BoughtProduct;
import de.yannickmortier.orderservice.domain.Order;
import de.yannickmortier.orderservice.dto.ProductDto;
import de.yannickmortier.orderservice.repository.BoughtProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DefaultBoughtProductService implements BoughtProductService{

    private final BoughtProductRepository boughtProductRepository;

    @Override
    public BoughtProduct save(BoughtProduct boughtProduct) {
        return boughtProductRepository.saveAndFlush(boughtProduct);
    }

    @Override
    @Transactional
    public void addProductsToOrder(Order order, List<ProductDto> products) {
        products.forEach(product -> {
            BoughtProduct boughtProduct = new BoughtProduct();
            boughtProduct.setOrder(order);
            boughtProduct.setProductId(product.getId());
            boughtProduct.setTitle(product.getTitle());
            boughtProduct.setPrice(product.getPrice());
            save(boughtProduct);
            order.getProducts().add(boughtProduct);
        });
    }
}
