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
public class AttachmentAns {

    private String type;

    private PayloadAns payload;

    public AttachmentAns(String type, PayloadAns payload) {
        this.type = type;
        this.payload = payload;
    }
}
