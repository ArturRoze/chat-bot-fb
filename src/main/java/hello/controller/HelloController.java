package hello.controller;

import hello.domain.income.message.FacebookMessage;
import hello.domain.income.message.Message;
import hello.domain.income.message.Recipient;
import hello.domain.outcome.message.FacebookMessageAns;
import hello.domain.outcome.message.MessageAns;
import hello.domain.outcome.message.RecipientAns;
import hello.enums.WebhookIncomeMessageType;
import hello.http.HttpSender;
import hello.service.MessageProcessor;
import hello.utils.JacksonParser;
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
import java.util.BitSet;
import java.util.Optional;

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


            FacebookMessage incomeFacebookMessage = messageProcessor.getIncomeFacebookMessage(request);

            if (incomeFacebookMessage != null){
                WebhookIncomeMessageType incomeMessageType = incomeFacebookMessage.determineMessageType();
                if (incomeMessageType == WebhookIncomeMessageType.MESSAGE){

                    Optional<Message> incomeMessage = messageProcessor.getIncomeMessage(request);

                    String text = "";

                    if (incomeMessage != null){

                        text = incomeMessage.get().getText();
                    }

                    Optional<String> idFromIncomeMessage = messageProcessor.getIdFromIncomeMessage(request);

                    RecipientAns recipient = new RecipientAns(idFromIncomeMessage.get());

                    MessageAns messageAns = new MessageAns("Your text length is: " + text.length());

                    try{
                        FacebookMessageAns facebookMessageAns = new FacebookMessageAns(recipient, messageAns);
                        String json = JacksonParser.prepareObject(facebookMessageAns);

                        HttpSender httpSender = new HttpSender();
                        String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
                        httpSender.sendPost(url, json);

                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        LOGGER.info(e.getMessage());
                    }
                } else {
                    LOGGER.info("incomeMessageType: " + incomeMessageType.name());
                }
            }



//            messageProcessor.processIncomeMessageAndSendText(request);
//
//            messageProcessor.processIncomeMessageAndSendCarousel(request);

//            messageProcessor.processOutcomeMessageAndSendPersistenceMenu();

            return ResponseEntity.ok().build();
        }
    }
}
