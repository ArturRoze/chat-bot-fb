package hello.domain.outcome.horizontalsequence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HorizontalMenu {
// need add field 07.05.2018
//    @JsonProperty("messaging_type")
//    private String messagingType;

    private Recipient recipient;

    private Message message;

    public HorizontalMenu(Recipient recipient, Message message) {
        this.recipient = recipient;
        this.message = message;
    }
}
