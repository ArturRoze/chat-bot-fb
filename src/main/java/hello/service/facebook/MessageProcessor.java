package hello.service.facebook;

import hello.domain.facebook.income.message.EntryObject;
import hello.domain.facebook.income.message.Message;
import hello.domain.facebook.income.message.FacebookMessage;
import hello.domain.facebook.income.userprofile.UserProfile;
import hello.domain.facebook.outcome.horizontalsequence.*;
import hello.domain.facebook.outcome.message.*;
import hello.domain.facebook.outcome.persistentmenu.CallToActions;
import hello.domain.facebook.outcome.persistentmenu.PersistentMenu;
import hello.domain.facebook.outcome.persistentmenu.StartPersistenceMenu;
import hello.enums.WebhookIncomeMessageType;
import hello.utils.HttpAnswer;
import hello.utils.HttpSender;
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

    public Optional<String> getSenderIdFromIncomeMessage(String request) throws IOException {

        FacebookMessage facebookMessage = getIncomeFacebookMessage(request);

        List<EntryObject> entry = facebookMessage.getEntry();

        return Optional.of(entry)
                .map((List<EntryObject> t) -> t.get(0).getMessaging())
                .map(messagingObjects -> messagingObjects.get(0)
                        .getSender()
                        .getId());
    }

    public void processIncomeMessageAndSendText(String request) {

        try {

            Optional<String> id = getSenderIdFromIncomeMessage(request);

            FacebookMessageAns facebookMessageAns = new FacebookMessageAns(new RecipientAns(id.get()), new MessageAns("hello, from bot"));

            LOGGER.info("parsed obj to json");
            String json = JacksonParser.prepareObject(facebookMessageAns);
            System.out.println(json);

            HttpSender httpSender = new HttpSender();
            String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
            httpSender.sendPost(url, json);

        } catch (Exception e) {
            System.out.println("can not parse message");
            LOGGER.error(e.getMessage());
        }
    }

    public void processIncomeMessageAndSendImage(String request) {

        FacebookMessage incomeFacebookMessage = getIncomeFacebookMessage(request);

        if (incomeFacebookMessage != null) {
            WebhookIncomeMessageType incomeMessageType = incomeFacebookMessage.determineMessageType();
            System.out.println(incomeFacebookMessage);
            if (incomeMessageType == WebhookIncomeMessageType.IMAGE) {
                Optional<Message> incomeMessage = getIncomeMessage(request);

                String urlPayload = "";

                if (incomeMessage.isPresent()) {
                    urlPayload = incomeMessage.get().getAttachments().get(0).getPayLoad().getUrl();
                }

                Optional<String> idFromIncomeMessage = Optional.empty();
                try {
                    idFromIncomeMessage = getSenderIdFromIncomeMessage(request);
                } catch (IOException e) {
                    LOGGER.error(request, e.getMessage(), e);
                }

                RecipientAns recipient = null;
                if (idFromIncomeMessage.isPresent()) {
                    recipient = new RecipientAns(idFromIncomeMessage.get());
                }

                PayloadAns payload = new PayloadAns(urlPayload);

                AttachmentAns attachment = new AttachmentAns("image", payload);

                MessageAns messageAns = new MessageAns(attachment);

                try {

                    FacebookMessageAns facebookMessageAns = new FacebookMessageAns(recipient, messageAns);
                    System.out.println(facebookMessageAns);
                    LOGGER.info("parsed obj to json");
                    String json = JacksonParser.prepareObject(facebookMessageAns);
                    System.out.println(json);

                    HttpSender httpSender = new HttpSender();
                    String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
                    httpSender.sendPost(url, json);

                } catch (Exception e) {
                    System.out.println("can not parse message");
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                System.out.println("not valid data");
            }
        }
    }

    public void processIncomeMessageAndSendCarousel(String request) {

        try {

            HorizontalMenu horizontalMenu = buildHorizontalMenu(request);

            if (horizontalMenu != null) {

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

            Optional<String> id = getSenderIdFromIncomeMessage(request);

            List<Elements> elements = new ArrayList<>();
            Elements element = new Elements("MyFirstElement");
            element.setSubtitle("test");
            elements.add(element);

            Payload payload = new Payload(elements);

            Attachment attachment = new Attachment(payload);

            hello.domain.facebook.outcome.horizontalsequence.Message message = new hello.domain.facebook.outcome.horizontalsequence.Message(attachment);

            Recipient recipient = new Recipient(id.get(), message);

            return new HorizontalMenu(recipient, message);

        } catch (Exception e) {
            System.out.println("can not parse message");
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    public void processOutcomeMessageAndSendPersistenceMenu() {

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

    public Optional<Message> getIncomeMessage(String request) {

        try {
            FacebookMessage facebookMessage = getIncomeFacebookMessage(request);

            List<EntryObject> entry = facebookMessage.getEntry();
            return Optional.of(entry)
                    .map((List<EntryObject> t) -> t.get(0).getMessaging())
                    .map(messagingObjects -> messagingObjects.get(0)
                            .getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.info(e.getMessage(), e);
        }

        return Optional.empty();
    }

    public FacebookMessage getIncomeFacebookMessage(String request) {

        FacebookMessage facebookMessage = null;
        try {
            facebookMessage = JacksonParser.parseObject(request, FacebookMessage.class);

        } catch (Exception e) {

            LOGGER.error(String.format(
                    "error parse request: %s, with exception %s",
                    request, e.getMessage()), e);
        }
        return facebookMessage;
    }

    public UserProfile getIncomeUserProfile(String request) {

        UserProfile userProfile = null;
        try {

            userProfile = JacksonParser.parseObject(request, UserProfile.class);

        } catch (Exception e) {

            LOGGER.error(String.format(
                    "error parse request: %s, with exception %s",
                    request, e.getMessage()), e);
        }
        return userProfile;
    }

    public void processIncomeMessageAndSendToSenderAnswerWithTextMessageAndAmountSymbols(String request) throws IOException {

        FacebookMessage incomeFacebookMessage = getIncomeFacebookMessage(request);

        if (incomeFacebookMessage != null) {
            WebhookIncomeMessageType incomeMessageType = incomeFacebookMessage.determineMessageType();
            if (incomeMessageType == WebhookIncomeMessageType.MESSAGE) {

                Optional<Message> incomeMessage = getIncomeMessage(request);

                String text = "";
                RecipientAns recipient = null;

                if (incomeMessage.isPresent()) {
                    text = incomeMessage.get().getText();
                }

                Optional<String> idFromIncomeMessage = getSenderIdFromIncomeMessage(request);

                if (idFromIncomeMessage.isPresent()){
                    recipient = new RecipientAns(idFromIncomeMessage.get());
                }

                MessageAns messageAns = new MessageAns("Your msg: " + text + " it's length: " + text.length());

                try {
                    FacebookMessageAns facebookMessageAns = new FacebookMessageAns(recipient, messageAns);
                    String json = JacksonParser.prepareObject(facebookMessageAns);

                    HttpSender httpSender = new HttpSender();
                    String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
                    httpSender.sendPost(url, json);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    LOGGER.info(e.getMessage());
                }
            } else {
                LOGGER.info("incomeMessageType: " + incomeMessageType.name());
            }
        }
    }

    public void parsePersonalDataFromIncomeMessage(String request) throws IOException {

        FacebookMessage incomeFacebookMessage = getIncomeFacebookMessage(request);

        if (incomeFacebookMessage != null) {
            WebhookIncomeMessageType incomeMessageType = incomeFacebookMessage.determineMessageType();
            if (incomeMessageType == WebhookIncomeMessageType.MESSAGE) {

                Optional<String> senderIdFromIncomeMessage = getSenderIdFromIncomeMessage(request);

                Optional<Message> incomeMessage = getIncomeMessage(request);

                String text = "";

                if (incomeMessage.isPresent()) {

                    text = incomeMessage.get().getText();
                }

                HttpAnswer httpAnswer = null;
                String response = "";
                try{
                    HttpSender httpSender = new HttpSender();
                    String urlGetUserProfileData = "https://graph.facebook.com/v2.6/1784593201613679?fields=first_name,last_name,locale,gender&access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
                    httpAnswer = httpSender.sendGet(urlGetUserProfileData);
                    response = httpAnswer.getResponse();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    LOGGER.info(e.getMessage(), e);
                }

                UserProfile userProfile = getIncomeUserProfile(response);

                String firstName = userProfile.getFirstName();
                String lastName = userProfile.getLastName();
                String locale = userProfile.getLocale();

                RecipientAns recipient = new RecipientAns(senderIdFromIncomeMessage.get());

                MessageAns messageAns = new MessageAns("Hello " + firstName + " " + lastName + " you are from " + locale + " your msg: " + text + " it's length: " + text.length());

                try {
                    FacebookMessageAns facebookMessageAns = new FacebookMessageAns(recipient, messageAns);
                    String json = JacksonParser.prepareObject(facebookMessageAns);

                    HttpSender httpSender = new HttpSender();
                    String url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAETsQm66mUBAKB8NzyYqiTmk0u8PvzUZAUnu1sQExKZBu55LokEe3wZCKuHzqBRMNZCcTeUgtDIJ7WEpLGrpcbZAP5bftHAeRJ1FHjZCcWwMS0WAgOkqqr7QTazW1bUad9FVAGMm6GiQAqvgt4doZAjDGgdoKgpMVZAwk6VKaRI5liuZAAEm7FJS";
                    httpSender.sendPost(url, json);
                    System.out.println(url);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    LOGGER.info(e.getMessage(), e);
                }
            } else {
                LOGGER.info("incomeMessageType: " + incomeMessageType.name());
            }
        }
    }

}
