package hello.domain.facebook.outcome.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessageAns {

    // need add field 07.05.2018
//    @JsonProperty("messaging_type")
//    private String messagingType;

    private RecipientAns recipient;

    private MessageAns message;

    public FacebookMessageAns(RecipientAns recipient, MessageAns message) {
        this.recipient = recipient;
        this.message = message;
    }
}
