package sen.bank.demospring6security.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.MandateRepository;
import sen.bank.demospring6security.services.MandateService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final MandateRepository mandateRepository;
    private final PasswordEncoder passwordEncoder;
    private final MandateService mandateService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Mandate mandate){
        try {
            String hashPwd = passwordEncoder.encode(mandate.getPwd());
            mandate.setPwd(hashPwd);
            Mandate saveMandate = mandateRepository.save(mandate);
            if (saveMandate.getId()>0){
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("User details are succefully registered");

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occured:" + e.getMessage());
        }
    }

//    @RequestMapping("/user")
//    public Mandate getUserDetailsAfterLogin(Authentication authentication) {
//        Optional<Mandate> optionalMandate = MandateRepository.findByEmail(authentication.getName());
//        return optionalMandate.orElse(null);
//    }

//    @GetMapping("/user")
//    public ResponseEntity<?> getUserDetailsAfterLogin(Authentication authentication) {
//        return mandateRepository.findByEmail(authentication.getName())
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("User not found with email: " + authentication.getName()));
//    }

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
