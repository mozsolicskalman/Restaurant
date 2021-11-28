package bme.hw.auth_user.api;

import bme.hw.auth_user.AuthUser;
import bme.hw.auth_user.AuthUserRepository;
import bme.hw.auth_user.AuthUserService;
import bme.hw.base.auth.Role;
import bme.hw.base.auth.RoleSecured;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AuthUserController {

    private final AuthUserRepository authUserRepository;
    private final AuthUserService authUserService;

    public AuthUserController(AuthUserRepository authUserRepository, AuthUserService authUserService) {
        this.authUserRepository = authUserRepository;
        this.authUserService = authUserService;
    }

    @GetMapping("/getAuthenticatedUser")
    @RoleSecured({})
    public AuthenticatedUserResponseDTO getAuthenticatedUser(Authentication authentication) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser loggedInUser = authUserRepository.findByUsername(principal.getUsername()).orElseThrow(
                        () -> new RuntimeException("Logged user not present in database"));
        Set<Role> roles = authentication.getAuthorities().stream().map(ga -> Role.valueOf(ga.getAuthority())).collect(Collectors.toSet());
        return new AuthenticatedUserResponseDTO(loggedInUser, roles);
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody CreateUserRequestDTO requestDTO) {
        authUserService.create(requestDTO);
        return ResponseEntity.ok().build();
    }


}
