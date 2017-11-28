package de.yannickmortier.orderservice.repository;

import de.yannickmortier.orderservice.domain.Order;
import de.yannickmortier.orderservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order findByUserAndId(User user, UUID id);
    List<Order> findByUser(User user);
}
