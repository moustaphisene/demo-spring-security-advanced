package sen.bank.demospring6security.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.MandateRepository;
import sen.bank.demospring6security.services.MandateService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final MandateRepository mandateRepository;
    private final MandateService mandateService;


    @GetMapping("/user")
    public ResponseEntity<?> getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Mandate> optional = mandateRepository.findByEmail(authentication.getName());

        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with email: " + authentication.getName());
        }
    }



}
