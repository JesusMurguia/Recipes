package recipes.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.business.User;
import recipes.persistence.UserRepository;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if(userRepo.existsByEmail(user.getEmail())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}