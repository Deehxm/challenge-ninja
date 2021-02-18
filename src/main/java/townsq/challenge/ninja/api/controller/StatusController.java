package townsq.challenge.ninja.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping(path = "/")
    public String isStarted() {
        return "Challenge Ninja Started";
    }

}
