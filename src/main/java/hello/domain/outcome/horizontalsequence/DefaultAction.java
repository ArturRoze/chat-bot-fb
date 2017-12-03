package hello.domain.outcome.horizontalsequence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.objects.annotations.Property;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessengerExtensions() {
        return messengerExtensions;
    }

    public void setMessengerExtensions(String messengerExtensions) {
        this.messengerExtensions = messengerExtensions;
    }

    public String getWebViewHeightRatio() {
        return webViewHeightRatio;
    }

    public void setWebViewHeightRatio(String webViewHeightRatio) {
        this.webViewHeightRatio = webViewHeightRatio;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }
}
