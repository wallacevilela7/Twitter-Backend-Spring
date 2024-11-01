package wvsdev.twitterapp.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import wvsdev.twitterapp.controller.dto.CreateuserDto;
import wvsdev.twitterapp.entities.Role;
import wvsdev.twitterapp.entities.User;
import wvsdev.twitterapp.repository.RoleRepository;
import wvsdev.twitterapp.repository.UserRepository;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository repository,
                          RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/users")
    @Transactional
    public ResponseEntity<Void> newUser(@RequestBody CreateuserDto dto){

        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if(userFromDb.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword((passwordEncoder.encode(dto.password())));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
