package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Cards;
import sen.bank.demospring6security.entity.Mandate;
import sen.bank.demospring6security.repository.CardsRepository;
import sen.bank.demospring6security.repository.MandateRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;
    private final MandateRepository mandateRepository;

    @GetMapping("/yourCard")
    public List<Cards> getCardDetails(@RequestParam String email) {
        Optional< Mandate > mandate = mandateRepository.findByEmail(email);
        if (mandate.isPresent()) {
            List<Cards> cards = cardsRepository.findByCustomerId(mandate.get().getId());
            if (cards != null ) {
                return cards;
            }else {
                return null;
            }

        }else {
            return null;
        }

    }



}
