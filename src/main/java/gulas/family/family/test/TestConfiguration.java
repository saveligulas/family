package gulas.family.family.test;

import gulas.family.family.REST_data.RegisterInfo;
import gulas.family.family.home.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestConfiguration {
    @Autowired
    private final UserService userService;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            /*userService.registerNewUser(RegisterInfo.builder()
                    .username("testuser_v9")
                    .password("testpassword")
                    .build());*/
        };
    }
}
