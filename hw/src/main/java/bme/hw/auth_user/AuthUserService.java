package bme.hw.auth_user;

import bme.hw.auth_user.api.CreateUserRequestDTO;
import bme.hw.base.auth.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthUserService {
    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;

    public AuthUserService(PasswordEncoder passwordEncoder, AuthUserRepository authUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authUserRepository = authUserRepository;
    }

    public void create(CreateUserRequestDTO requestDTO) {
        if(authUserRepository.findByUsername(requestDTO.getUsername()).isPresent()){
            throw new RuntimeException("Username is already in use!");
        }
        AuthUser authUser = new AuthUser();
        authUser.setRoles(Set.of(Role.ROLE_CUSTOMER));
        authUser.setUsername(requestDTO.getUsername());
        authUser.setPasswordHash(passwordEncoder.encode(requestDTO.getPassword()));

        authUserRepository.save(authUser);
    }
}
