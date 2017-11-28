package hello;

import hello.parser.FacebookMessage;
import hello.parser.JacksonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

            } catch (Exception e) {
                System.out.println("can not parse message");
                LOGGER.error(e.getMessage());
            }

            return ResponseEntity.ok().build();
        }
    }
}
