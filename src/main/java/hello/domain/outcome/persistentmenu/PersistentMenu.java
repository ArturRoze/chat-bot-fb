package hello.domain.outcome.persistentmenu;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
        if (composerInputDisabled){
            this.callToActions = callToActions;
        }
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Boolean getComposerInputDisabled() {
        return composerInputDisabled;
    }

    public void setComposerInputDisabled(Boolean composerInputDisabled) {
        this.composerInputDisabled = composerInputDisabled;
    }

    public List<CallToActions> getCallToActions() {
        return callToActions;
    }

    public void setCallToActions(List<CallToActions> callToActions) {
        this.callToActions = callToActions;
    }

    @Override
    public String toString() {
        return "PersistentMenu{" +
                "locale='" + locale + '\'' +
                ", composerInputDisabled=" + composerInputDisabled +
                ", callToActions=" + callToActions +
                '}';
    }
}
