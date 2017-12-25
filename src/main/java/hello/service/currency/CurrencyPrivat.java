package hello.service.currency;

import hello.domain.currency.nbu.CurrencyNbuObject;
import hello.utils.HttpAnswer;
import hello.utils.HttpSender;
import hello.utils.JacksonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.util.Collections;
import java.util.List;

@Component
public class CurrencyPrivat implements Currency{

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public List<CurrencyNbuObject> getCurrencies() {
        HttpSender httpSender = new HttpSender();
        String url = "https://api.privatbank.ua/p24api/exchange_rates?json&date=10.12.2017";

        List<CurrencyNbuObject> currencies = Collections.EMPTY_LIST;

        try {

            HttpAnswer httpAnswer = httpSender.sendGet(url);

            if (httpAnswer.getResponseCode() == HttpsURLConnection.HTTP_OK){
                String response = httpAnswer.getResponse();
                currencies = JacksonParser.parseObject(response, List.class);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return currencies;
    }
}
