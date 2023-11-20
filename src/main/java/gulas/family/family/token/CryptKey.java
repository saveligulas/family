package gulas.family.family.token;

import gulas.family.family.home.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "crypt_key")
@Getter
@Setter
public class CryptKey {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cryptKey", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private byte[] privateKey;
    private byte[] publicKey;
}
