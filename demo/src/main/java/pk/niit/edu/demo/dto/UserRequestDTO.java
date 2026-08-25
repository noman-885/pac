package pk.niit.edu.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be blank!")
    private String name;
    @NotBlank(message = "Email cannot be blanked!")
    @Email(message = "Please provide a valid email!")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
             message = "Please provide a valid email")
    private String email;
    @NotBlank(message = "Password cannot be blank!")
    @Size(min=8, message = "Password must be at least 8 characters")
    private String password;
    private String phone;

}
