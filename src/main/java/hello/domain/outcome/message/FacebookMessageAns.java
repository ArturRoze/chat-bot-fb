package hello.domain.outcome.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessageAns {

    private RecipientAns recipient;

    private MessageAns message;

    public FacebookMessageAns(RecipientAns recipient, MessageAns message) {
        this.recipient = recipient;
        this.message = message;
    }

    public RecipientAns getRecipient() {
        return recipient;
    }

    public void setRecipient(RecipientAns recipient) {
        this.recipient = recipient;
    }

    public MessageAns getMessage() {
        return message;
    }

    public void setMessage(MessageAns message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FacebookMessageAns{" +
                "recipient=" + recipient +
                ", message=" + message +
                '}';
    }
}
