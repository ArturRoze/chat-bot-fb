package hello.domain.outcome.persistentmenu;

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
public class CallToActions {

    private String title;

    private String type;

    @JsonProperty("call_to_actions")
    private List<CallToActions> callToActions;

    private String payload;

    private String url;

    @JsonProperty("webview_height_ratio")
    private String webViewHeightRatio;

    public CallToActions(String title, String type, List<CallToActions> callToActions) {
        this.title = title;
        this.type = type;
        this.callToActions = callToActions;
    }

    public CallToActions(String title, String type, String url, String webViewHeightRatio) {
        this.title = title;
        this.type = type;
        this.url = url;
        this.webViewHeightRatio = webViewHeightRatio;
    }

    public CallToActions(String title, String type, String payload) {
        this.title = title;
        this.type = type;
        this.payload = payload;
    }
}
