package sen.bank.demospring6security.web;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sen.bank.demospring6security.entity.Contact;
import sen.bank.demospring6security.repository.ContactRepository;

import java.util.Date;
import java.util.Random;

@RestController
@RequiredArgsConstructor

public class SendMessageController {

    private final ContactRepository contactRepository;

//    @PostMapping("/contact")
//// Post-filter pour exclure les contacts dont le nom est "Test"
//    @PreFilter("filterObject.contactName != 'Test'")
//    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
//
//        List<Contact> returnContacts = new ArrayList<>();
//
//        // Vérification que la liste n'est pas vide avant d'accéder à l'indice 0
//        if (!contacts.isEmpty()) {
//            Contact contact = contacts.get(0); // Utilisation correcte en Java 17
//            contact.setContactId(getServiceReqNumber());
//            contact.setCreateDt(new Date(System.currentTimeMillis()));
//
//            Contact savedContact = contactRepository.save(contact);
//            returnContacts.add(savedContact);
//        }
//
//        return returnContacts;
//    }

    @PostMapping("/contact")
    public Contact saveContactInquiryDetails(@RequestBody Contact contact) {

        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        return contactRepository.save(contact);
    }



    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR" + ranNum;
    }

}
