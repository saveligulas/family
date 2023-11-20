package gulas.family.family.REST_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RegisterInfo {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
