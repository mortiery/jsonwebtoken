package de.yannickmortier.authserver.domain;

import de.yannickmortier.authserver.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String role;

    @ManyToMany( mappedBy = "roles" )
    private Set<User> users = new HashSet<>();

    public Role(String role) {
        this.role = role;
    }
}
