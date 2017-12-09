package hello.domain.income.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import hello.enums.WebhookIncomeMessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessage {

    private String object;

    private List<EntryObject> entry;


    public WebhookIncomeMessageType determineMessageType() {

        Message message = entry.get(0).getMessaging().get(0).getMessage();

        MessagingObject messagingObject = entry.get(0).getMessaging().get(0);

        if (message != null && message.isEcho()) {
            return WebhookIncomeMessageType.ECHO;
        } else if (message != null && !message.isEcho()) {
            return WebhookIncomeMessageType.MESSAGE;
        } else if (messagingObject.getDelivery() != null) {
            return WebhookIncomeMessageType.DELIVERY;
        } else if (messagingObject.getRead() != null) {
            return WebhookIncomeMessageType.READ;
        } else {
            return WebhookIncomeMessageType.UNKNOWN;
        }
    }

}
