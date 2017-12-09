package hello.domain.income.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Message {

    private String mid;

    private Integer seq;

    private String text;

    private List<AttachmentsObject> attachments;

    @JsonProperty("quick_reply")
    private QuickReply quickReply;

    @JsonProperty("is_echo")
    private boolean isEcho;

    @JsonProperty("app_id")
    private Long appId;

    private String metadata;
}
