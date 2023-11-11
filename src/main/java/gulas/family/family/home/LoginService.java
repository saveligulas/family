package gulas.family.family.home;

import gulas.family.family.REST_data.RegisterInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Autowired
    private final UserRepository userRepo;

    public String registerNewUser(RegisterInfo request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        try {
            userRepo.save(user);
        } catch (Exception e) {
            return "ERROR";
        }
        return "OK";
    }
}
