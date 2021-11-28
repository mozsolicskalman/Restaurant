package bme.hw.auth_user.api;

import lombok.Getter;

@Getter
public class CreateUserRequestDTO {
    private String username;
    private String password;
}
