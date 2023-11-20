package gulas.family.family.home;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.cryptKey.privateKey = ?1")
    Optional<User> findByPrivateKey(byte[] value);

    @Query("SELECT u FROM User u WHERE u.cryptKey.publicKey = ?1")
    Optional<User> findByPublicKey(byte[] value);
}
