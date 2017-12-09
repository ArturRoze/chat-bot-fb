package hello.domain.outcome.persistentmenu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CallToActions> getCallToActions() {
        return callToActions;
    }

    public void setCallToActions(List<CallToActions> callToActions) {
        this.callToActions = callToActions;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebViewHeightRatio() {
        return webViewHeightRatio;
    }

    public void setWebViewHeightRatio(String webViewHeightRatio) {
        this.webViewHeightRatio = webViewHeightRatio;
    }

    @Override
    public String toString() {
        return "CallToActions{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", callToActions=" + callToActions +
                ", payload='" + payload + '\'' +
                ", url='" + url + '\'' +
                ", webViewHeightRatio='" + webViewHeightRatio + '\'' +
                '}';
    }
}
