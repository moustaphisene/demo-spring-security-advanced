package sen.bank.demospring6security.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.MandateRepository;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final MandateRepository mandateRepository;
    private final PasswordEncoder passwordEncoder;


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
}
