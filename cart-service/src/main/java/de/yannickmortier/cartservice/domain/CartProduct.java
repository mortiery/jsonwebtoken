package de.yannickmortier.cartservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class CartProduct {
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private UUID productId;

    public CartProduct(User user, UUID productId) {
        this.user = user;
        this.productId = productId;
    }
}
