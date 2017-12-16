package hello.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HttpAnswer {

   private int responseCode;

   private String response;

    public HttpAnswer(int responseCode, String response) {
        this.responseCode = responseCode;
        this.response = response;
    }
}
