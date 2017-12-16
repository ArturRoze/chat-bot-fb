package hello.service.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    private final Currency currency;

    @Autowired
    public CurrencyService(@Qualifier("currencyNbu") Currency currency) {
        this.currency = currency;
    }

    public String getCurrency(String request) {

        /**
         * business logic
         */

        List<hello.domain.currency.Currency> currency = this.currency.getCurrency();
        return null;
    }

}
