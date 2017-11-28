package de.yannickmortier.cartservice.repository;

import de.yannickmortier.cartservice.domain.CartProduct;
import de.yannickmortier.cartservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, UUID> {
    CartProduct findByUserIdAndProductId(UUID userId, UUID productID);
    List<CartProduct> findByUser(User user);

    @Modifying
    @Query("DELETE FROM CartProduct cp WHERE cp.user = :user")
    int emptyCartForUser(@Param("user") User user);
}
