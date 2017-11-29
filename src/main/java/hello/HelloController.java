package hello;

import hello.parser.parser_json_to_obj.*;
import hello.parser.JacksonParser;
import hello.parser.parser_obj_to_json.FacebookMessageAns;
import hello.parser.parser_obj_to_json.MessageAns;
import hello.parser.parser_obj_to_json.RecipientAns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class HelloController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping("/webhook")
    public ResponseEntity<String> index(@RequestBody(required = false) String request,
                                        @RequestParam(name = "hub.verify_token", required = false) String name,
                                        @RequestParam(name = "hub.challenge", required = false) String challenge) {

        System.out.println("request: " + request);
        System.out.println("name: " + name);

        if (name != null && name.equals("archy198518")) {

            LOGGER.info("validated webhook");

            return new ResponseEntity<>(challenge, HttpStatus.OK);

        } else {

            try {

                FacebookMessage facebookMessage = JacksonParser.parseObject(request, FacebookMessage.class);
                LOGGER.info("parsed msg to obj");
                System.out.println("our message: " + facebookMessage.toString());

                List<EntryObject> entry = facebookMessage.getEntry();
                List<MessagingObject> messaging = entry.get(0).getMessaging();
                String id = messaging.get(0).getRecipient().getId();
                System.out.println("recipient id = " + id);

                FacebookMessageAns facebookMessageAns = new FacebookMessageAns(new RecipientAns(id), new MessageAns("hello, from bot"));

                LOGGER.info("parsed obj to json");
                String json = JacksonParser.prepareObject(facebookMessageAns);
                System.out.println(json);

//                Recipient recipient = null;
//                for (EntryObject entryObject : facebookMessage.getEntry()) {
//                    for (MessagingObject messagingObject : entryObject.getMessaging()) {
//                        recipient = messagingObject.getRecipient();
//                    }
//                }
//
//                FacebookMessageAns facebookMessageAns = new FacebookMessageAns(new RecipientAns(recipient.getId()), new MessageAns("hello, from bot"));
//
//                LOGGER.info("parsed obj to json");
//                String json = JacksonParser.prepareObject(facebookMessageAns);
//                System.out.println(json);

            } catch (Exception e) {
                System.out.println("can not parse message");
                LOGGER.error(e.getMessage());
            }

            return ResponseEntity.ok().build();
        }
    }
}
