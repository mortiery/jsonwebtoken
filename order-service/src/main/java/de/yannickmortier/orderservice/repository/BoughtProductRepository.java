package de.yannickmortier.orderservice.repository;

import de.yannickmortier.orderservice.domain.BoughtProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoughtProductRepository extends JpaRepository<BoughtProduct, UUID> {
}
