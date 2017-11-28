package de.yannickmortier.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "order")
    private List<BoughtProduct> products = new ArrayList<>();

    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Instant orderTime;

    @Column(nullable = false)
    private BigDecimal orderTotal;

    @Column(nullable = false, length = 4)
    private String creditCardDigits;

    @PrePersist
    private void setCreationTime() {
        this.orderTime = Instant.now();
    }
}
