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
public class DefaultAction {

    private String type;

    private String url;

    @JsonProperty("messenger_extensions")
    private String messengerExtensions;

    @JsonProperty("webview_height_ratio")
    private String webViewHeightRatio;

    @JsonProperty("fallback_url")
    private String fallbackUrl;

    public DefaultAction(String type, String url, String fallbackUrl) {
        this.type = type;
        this.url = url;
        this.fallbackUrl = fallbackUrl;
    }
}
