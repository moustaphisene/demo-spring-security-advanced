package sen.bank.demospring6security.web;

import org.springframework.web.bind.annotation.RequestMapping;

public class CustomErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
