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
public class StartPersistenceMenu {

    @JsonProperty("persistent_menu")
    private List<PersistentMenu> persistentMenus;

    public StartPersistenceMenu(List<PersistentMenu> persistentMenus) {
        this.persistentMenus = persistentMenus;
    }
}
