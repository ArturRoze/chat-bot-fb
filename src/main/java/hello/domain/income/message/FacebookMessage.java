package hello.domain.income.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessage {

    private String object;
    private List<EntryObject> entry;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<EntryObject> getEntry() {
        return entry;
    }

    public void setEntry(List<EntryObject> entry) {
        this.entry = entry;
    }

    @Override
    public String toString() {
        return "FacebookMessage{" +
                "object='" + object + '\'' +
                ", entry=" + entry +
                '}';
    }
}
