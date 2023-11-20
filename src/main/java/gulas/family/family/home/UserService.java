package gulas.family.family.home;

import gulas.family.family.REST_data.LoginInfo;
import gulas.family.family.REST_data.RegisterInfo;
import gulas.family.family.errorHandler.handler.ApiRequestException;
import gulas.family.family.token.CryptKey;
import gulas.family.family.token.CryptKeyRepository;
import gulas.family.family.token.CryptKeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final CryptKeyRepository cryptKeyRepo;
    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final CryptKeyService cryptKeyService;

    @Transactional
    public String registerNewUser(RegisterInfo request) {
        User user = new User();

        Optional<User> userWithUsernameExists = userRepo.findByUsername(request.getUsername());

        if (userWithUsernameExists.isPresent()) {
            throw new ApiRequestException("User with username exists");
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        userRepo.save(user);

        addCryptKeyToUser(user.getId());
        return "OK";
    }

    @Transactional
    public void addCryptKeyToUser(Long userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiRequestException("User with id does not exist");
        }

        User user = userOptional.get();

        CryptKey cryptKey = cryptKeyService.createCryptKeyForUser(user);
        user.setCryptKey(cryptKey);
        cryptKey.setUser(user);

        cryptKeyRepo.save(cryptKey);
    }

    public String loginUserGetPrivateKeyString(LoginInfo request) {
        Optional<User> userOptional = userRepo.findByUsername(request.getUsername());

        if (userOptional.isEmpty()) {
            throw new ApiRequestException("User with username does not exist");
        }

        User user = userOptional.get();
        //cryptKeyService.checkIfCryptKeyExistsForUser(user);

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ApiRequestException("Password does not match");
        }

        return cryptKeyService.getEncodedString(user.getCryptKey().getPrivateKey());
    }
}
