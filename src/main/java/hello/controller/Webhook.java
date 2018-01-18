package hello.controller;

import hello.service.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Webhook {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MainService mainService;

    @Autowired
    public Webhook(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping("/webhook")
    public ResponseEntity<String> index(@RequestBody(required = false) String request,
                                        @RequestParam(name = "hub.verify_token", required = false) String name,
                                        @RequestParam(name = "hub.challenge", required = false) String challenge) throws IOException {

        System.out.println("request: " + request);

        if (name != null && name.equals("archy198518")) {

            LOGGER.info("validated webhook");

            return new ResponseEntity<>(challenge, HttpStatus.OK);

        } else {

            mainService.processIncomeMessage(request);

            return ResponseEntity.ok().build();
        }
    }
}
