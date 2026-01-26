package hexlet.code.dto.user;

import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    //@NotBlank
    @Email
    private JsonNullable<String> email;
    private JsonNullable<String> firstName;
    private JsonNullable<String> lastName;
    //@NotBlank
    @Size(min = 3)
    private JsonNullable<String> password;
}
