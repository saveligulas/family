package gulas.family.family.token;

import gulas.family.family.errorHandler.handler.ApiRequestException;
import gulas.family.family.home.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptKeyService {
    @Autowired
    private final CryptKeyRepository cryptKeyRepo;

    @SneakyThrows
    public CryptKey createCryptKeyForUser(User user) {
        Optional<CryptKey> keyExistsForUser = cryptKeyRepo.findByUser(user);
        if (keyExistsForUser.isPresent()) {
            throw new ApiRequestException("Crypt key already exists for user " + user.getUsername());
        }

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair key = generator.generateKeyPair();
        PublicKey publicKey = key.getPublic();
        PrivateKey privateKey = key.getPrivate();
        CryptKey cryptKey = new CryptKey();
        cryptKey.setPublicKey(publicKey.getEncoded());
        cryptKey.setPrivateKey(privateKey.getEncoded());

        return cryptKey;
    }

    public void saveCryptKey(CryptKey key) {
        cryptKeyRepo.save(key);
    }

    public void checkIfCryptKeyExistsForUser(User user) {
        Optional<CryptKey> keyExistsForUser = cryptKeyRepo.findByUser(user);

        if (keyExistsForUser.isEmpty()) {
            throw new ApiRequestException("Key does not exist for user");
        }
    }

    @SneakyThrows
    public String getEncodedString(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    public byte[] getDecodedBytes(String key) {
        return Base64.getDecoder().decode(key);
    }

    public PrivateKey getPrivateKey(byte[] key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    public PublicKey getPublicKey(byte[] keyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get public key from byte array", e);
        }
    }


    @SneakyThrows
    public byte[] getEncryptedMessage(byte[] privateKeyBytes, byte[] publicKeyBytes, String message) {
        PublicKey publicKey = getPublicKey(publicKeyBytes);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }
}
