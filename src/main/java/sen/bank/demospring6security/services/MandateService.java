package sen.bank.demospring6security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sen.bank.demospring6security.repository.MandateRepository;

@Service
public class MandateService {
    private final MandateRepository mandateRepository;

    @Autowired
    public MandateService(MandateRepository mandateRepository) {
        this.mandateRepository = mandateRepository;
    }

//    public Mandate getMandateByEmail(String email) {
//        return MandateRepository.findByEmail(email)
//                .orElseThrow(() -> new EntityNotFoundException("Mandate not found for email: " + email));
//    }
}
