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
public class MessageAns {

    private String text;

    private AttachmentAns attachment;

    public MessageAns(String text) {
        this.text = text;
    }

    public MessageAns(AttachmentAns attachment) {
        this.attachment = attachment;
    }
}
