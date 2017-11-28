package de.yannickmortier.productservice.repository;

import de.yannickmortier.productservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByRequiresValidationIs(boolean requiresValidation);
}
