package hello.domain.facebook.outcome.persistentmenu;

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
public class PersistentMenu {

    private String locale = "default";

    @JsonProperty("composer_input_disabled")
    private boolean composerInputDisabled;

    @JsonProperty("call_to_actions")
    private List<CallToActions> callToActions;

    public PersistentMenu(String locale, Boolean composerInputDisabled, List<CallToActions> callToActions) {
        this.locale = locale;
        this.composerInputDisabled = composerInputDisabled;
        if (composerInputDisabled) {
            this.callToActions = callToActions;
        }
    }
}
