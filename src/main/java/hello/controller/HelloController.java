package hello.controller;

import hello.service.MessageProcessor;
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
public class HelloController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final MessageProcessor messageProcessor;

    @Autowired
    public HelloController(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @RequestMapping("/webhook")
    public ResponseEntity<String> index(@RequestBody(required = false) String request,
                                        @RequestParam(name = "hub.verify_token", required = false) String name,
                                        @RequestParam(name = "hub.challenge", required = false) String challenge) throws IOException {

        System.out.println("request: " + request);
        System.out.println("name: " + name);

        if (name != null && name.equals("archy198518")) {

            LOGGER.info("validated webhook");

            return new ResponseEntity<>(challenge, HttpStatus.OK);

        } else {

//            messageProcessor.processIncomeMessageAndSendText(request);

//            messageProcessor.processIncomeMessageAndSendCarousel(request);

//            messageProcessor.processOutcomeMessageAndSendPersistenceMenu();

//            messageProcessor.processIncomeMessageAndSendToSenderAnswerWithTextMessageAndAmountSymbols(request);

            messageProcessor.parsePersonalDataFromIncomeMessage(request);

            return ResponseEntity.ok().build();
        }
    }
}
