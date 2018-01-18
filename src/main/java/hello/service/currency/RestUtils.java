package hello.service.currency;

import hello.utils.HttpAnswer;
import hello.utils.HttpSender;
import hello.utils.JacksonParser;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;

@Slf4j
public class RestUtils {

    public static <T> T requestObject(String url, Class<T> clazz) {
        HttpSender httpSender = new HttpSender();
        log.debug("Make a request. url {}", url);

        try {

            HttpAnswer httpAnswer = httpSender.sendGet(url);

            if (httpAnswer.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String response = httpAnswer.getResponse();
                log.info("Response {} {}", url, response);
                return JacksonParser.parseObject(response, clazz);
            } else {
                log.error("Error response {} {}", httpAnswer.getResponseCode(), httpAnswer.getResponse());
            }
        } catch (Exception e) {
            log.error("Failed to request {}", url, e);
        }

        return null;
    }
}
