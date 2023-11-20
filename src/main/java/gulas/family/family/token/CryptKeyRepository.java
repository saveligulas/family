package gulas.family.family.token;

import gulas.family.family.home.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptKeyRepository extends JpaRepository<CryptKey, Long> {
    @Query("SELECT c FROM CryptKey c WHERE c.user.id = ?1")
    Optional<CryptKey> findByUserId(Long userId);

    @Query("SELECT c FROM CryptKey c WHERE c.user = ?1")
    Optional<CryptKey> findByUser(User user);
}
