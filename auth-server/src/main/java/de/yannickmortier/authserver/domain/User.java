package de.yannickmortier.authserver.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column
    private String password;

    @Column
    @Setter(AccessLevel.NONE)
    private Instant created;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private Set<Role> roles = new HashSet<>();

    public User(String userName, String unencryptedPassword) {
        setUserName(userName);
        setPassword(unencryptedPassword);
    }

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public void setPassword(String unencryptedPassword) {
        this.password = passwordEncoder.encode(unencryptedPassword);
    }

    @PrePersist
    private void setCreationTime() {
        this.created = Instant.now();
    }
}
