package hello.service.currency;

import hello.domain.currency.nbu.CurrencyNbuObject;

import java.util.List;

public interface Currency {

    List<CurrencyNbuObject> getCurrencies();
}
