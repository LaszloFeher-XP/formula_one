package org.demo.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.model.AuthenticationResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/auth")
public class AuthController {

    @GetMapping
    AuthenticationResponse baseauth() {
        return new AuthenticationResponse("User is authenticated.");
    }
}
