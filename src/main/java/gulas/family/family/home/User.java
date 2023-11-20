package gulas.family.family.home;

import gulas.family.family.token.CryptKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "family_users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "crypt_key_id", referencedColumnName = "id")
    private CryptKey cryptKey;
}
