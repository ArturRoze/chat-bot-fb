package hello.parser.parser_text;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentsObject {

    private String type;
    @JsonProperty("payload")
    private PayLoadObject payLoad;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PayLoadObject getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(PayLoadObject payLoad) {
        this.payLoad = payLoad;
    }

    @Override
    public String toString() {
        return "AttachmentsObject{" +
                "type='" + type + '\'' +
                ", payLoad=" + payLoad +
                '}';
    }
}
