package de.yannickmortier.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class BoughtProduct {
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Order order;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;
}
