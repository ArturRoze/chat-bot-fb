package hello.domain.currency.privat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyPrivatObject {

    private String date;

    private String bank;

    private int baseCurrency;

    private String baseCurrencyLit;

    private ExchangeRateObject exchangeRate;
}


