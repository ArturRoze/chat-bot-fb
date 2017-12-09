package hello.service;

import hello.domain.income.message.EntryObject;
import hello.domain.income.message.FacebookMessage;
import hello.domain.outcome.horizontalsequence.*;
import hello.domain.outcome.message.FacebookMessageAns;
import hello.domain.outcome.message.MessageAns;
import hello.domain.outcome.message.RecipientAns;
import hello.domain.outcome.persistentmenu.CallToActions;
import hello.domain.outcome.persistentmenu.PersistentMenu;
import hello.domain.outcome.persistentmenu.StartPersistenceMenu;
import hello.http.HttpSender;
import hello.utils.JacksonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageProcessor {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Optional<String> getIdFromIncomeMessage(String request) throws IOException {
        FacebookMessage facebookMessage = JacksonParser.parseObject(request, FacebookMessage.class);
        LOGGER.info("parsed msg to obj");
        System.out.println("our message: " + facebookMessage.toString());

        List<EntryObject> entry = facebookMessage.getEntry();

//                List<EntryObject> entry = facebookMessage.getEntry();
//                List<MessagingObject> messaging = entry.get(0).getMessaging();
//                String id = messaging.get(0).getSender().getId();
//                System.out.println("sender id = " + id);

//                Sender sender = null;
//                for (EntryObject entryObject : facebookMessage.getEntry()) {
//                    for (MessagingObject messagingObject : entryObject.getMessaging()) {
//                        sender = messagingObject.getSender();
//                    }
//                }
        return Optional.of(entry)
                .map((List<EntryObject> t) -> t.get(0).getMessaging())
                .map(messagingObjects -> messagingObjects.get(0)
                        .getSender()
                        .getId());
    }

    public void processIncomeMessageAndSendText(String request) {

        try {

            Optional<String> id = getIdFromIncomeMessage(request);

            FacebookMessageAns facebookMessageAns = new FacebookMessageAns(new RecipientAns(id.get()), new MessageAns("hello, from bot"));

            LOGGER.info("parsed obj to json");
            String json = JacksonParser.prepareObject(facebookMessageAns);
            System.out.println(json);

//                if (sender!= null){
//
//                    FacebookMessageAns facebookMessageAns = new FacebookMessageAns(new RecipientAns(sender.getId()), new MessageAns("hello, from bot"));
//
//                    LOGGER.info("parsed obj to json");
//                    String json = JacksonParser.prepareObject(facebookMessageAns);
//                    System.out.println(json);
//                }

            HttpSender httpSender = new HttpSender();
            String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
            httpSender.sendPost(url, json);

        } catch (Exception e) {
            System.out.println("can not parse message");
            LOGGER.error(e.getMessage());
        }
    }

    public void processIncomeMessageAndSendCarousel(String request){

        try {

            HorizontalMenu horizontalMenu = buildHorizontalMenu(request);

            if (horizontalMenu != null){

                String json = JacksonParser.prepareObject(horizontalMenu);

                System.out.println(json);

                HttpSender httpSender = new HttpSender();
                String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
                httpSender.sendPost(url, json);
            }

        } catch (Exception e) {
            System.out.println("can not parse message");
            LOGGER.error(e.getMessage());
        }
    }

    private HorizontalMenu buildHorizontalMenu(String request) {

        try {

            Optional<String> id = getIdFromIncomeMessage(request);

            List<Elements> elements = new ArrayList<>();
            Elements element = new Elements("MyFirstElement");
            element.setSubtitle("test");
            elements.add(element);

            Payload payload = new Payload(elements);

            Attachment attachment = new Attachment(payload);

            Message message = new Message(attachment);

            Recipient recipient = new Recipient(id.get(), message);

            return new HorizontalMenu(recipient, message);

        } catch (Exception e) {
            System.out.println("can not parse message");
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    public void processOutcomeMessageAndSendPersistenceMenu(){

        try {

            StartPersistenceMenu startPersistenceMenu = buildPersistenceMenu();

            String json = JacksonParser.prepareObject(startPersistenceMenu);

            System.out.println(json);

            HttpSender httpSender = new HttpSender();
            String url = "https://graph.facebook.com/v2.6/me/messenger_profile?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
            httpSender.sendPost(url, json);

        } catch (Exception e) {
            System.out.println("can not parse message");
            LOGGER.error(e.getMessage());
        }
    }

    private StartPersistenceMenu buildPersistenceMenu() {

        String webUrl = "https://www.facebook.com/profile.php?id=100010483650964";

        CallToActions firstCallToActionsWithPayload = new CallToActions("Pay Bill", "postback", "PAYBILL");
        CallToActions secondCallToActionsWithPayload = new CallToActions("History", "postback", "HISTORY");
        CallToActions thirdCallToActionsWithPayload = new CallToActions("Contact Info", "postback", "CONTACT_INFO");

        List<CallToActions> callToActionsWithPayload = new ArrayList<>();
        callToActionsWithPayload.add(firstCallToActionsWithPayload);
        callToActionsWithPayload.add(secondCallToActionsWithPayload);
        callToActionsWithPayload.add(thirdCallToActionsWithPayload);

        CallToActions callToActions = new CallToActions("My Account", "nested", callToActionsWithPayload);
        CallToActions callToActionsWithUrl = new CallToActions("Latest News", "web_url", webUrl, "full");

        List<CallToActions> generalCallToActions = new ArrayList<>();
        generalCallToActions.add(callToActions);
        generalCallToActions.add(callToActionsWithUrl);

        PersistentMenu firstPersistentMenu = new PersistentMenu("default", true, generalCallToActions);
        PersistentMenu secondPersistentMenu = new PersistentMenu("zh_CN", false, callToActionsWithPayload);

        List<PersistentMenu> persistentMenus = new ArrayList<>();
        persistentMenus.add(firstPersistentMenu);
        persistentMenus.add(secondPersistentMenu);

        return new StartPersistenceMenu(persistentMenus);
    }
}
