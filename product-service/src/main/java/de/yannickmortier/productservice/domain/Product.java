package de.yannickmortier.productservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    @Column( nullable = false, unique = true)
    private String imageName;

    @Column( nullable = false )
    private String title;

    @Column( nullable = false, length = 1024)
    private String description;

    @Column( nullable = false )
    private BigDecimal price;

    @JsonIgnore
    @Column( nullable = false )
    private boolean requiresValidation = false;

}
