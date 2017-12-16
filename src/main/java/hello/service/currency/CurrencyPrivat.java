package hello.service.currency;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrencyPrivat implements Currency{
    @Override
    public List<hello.domain.currency.Currency> getCurrency() {
        return null;
    }
}
