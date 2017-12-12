package hello.domain.outcome.horizontalsequence;

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
public class Elements {

    private String title;

    @JsonProperty("image_url")
    private String imageUrl;

    private String subtitle;

    @JsonProperty("default_action")
    private DefaultAction defaultAction;

    private List<Buttons> buttons;

    public Elements(String title) {
        this.title = title;
    }
}
