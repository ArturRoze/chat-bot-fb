package hello.service.currency;

import hello.domain.currency.privat.CurrencyPrivatObject;
import hello.domain.currency.privat.ExchangeRateObject;
import hello.enums.CurrencyType;
import hello.service.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CurrencyService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final MessageProcessor messageProcessor;

    private final String serviceUnavailable = "Service temporary unavailable";

    @Autowired
    public CurrencyService(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public String getCurrency(Set<CurrencyType> currencyTypes) {

        String urlNbu = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        String urlPrivat = "https://api.privatbank.ua/p24api/exchange_rates?json&date=16.01.2018";

        CurrencyPrivatObject currencyPrivatObject = RestUtils.requestObject(urlPrivat, CurrencyPrivatObject.class);
        System.out.println("currencies" + currencyPrivatObject);

        List<ExchangeRateObject> currencies = new ArrayList<>();

        if (currencyPrivatObject != null) {

            for (ExchangeRateObject exchangeRateObject : currencyPrivatObject.getExchangeRate()) {
                String currencyPrivatBank = exchangeRateObject.getCurrency().toUpperCase();
                for (CurrencyType currencyType : currencyTypes) {
                    if (currencyPrivatBank.equals(currencyType.name())) {
                        currencies.add(exchangeRateObject);
                    }
                }
            }

            String currentDate = getCurrentDate();

            StringBuilder sb = new StringBuilder();
            sb.append("Date: ");
            sb.append(currentDate + "\n");
            for (ExchangeRateObject currency : currencies) {
                String currency1 = currency.getCurrency();
                BigDecimal saleRateNB = currency.getSaleRateNB();
                sb.append(currency1);
                sb.append(" -> ");
                sb.append(saleRateNB.toString() + "\n");
            }
            return sb.toString();

        } else {
            LOGGER.info(serviceUnavailable);
            return serviceUnavailable;
        }
    }

    public String getCurrentDate() {
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return dateTime.format(LocalDateTime.now());
    }
}

