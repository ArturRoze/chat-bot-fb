package hello.domain.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hello.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    private int r030;

    private String txt;

    private BigDecimal rate;

    private String cc;

    @JsonProperty("exchangedate")
    private String exchangeDate;

    public boolean isSearchedCurrency(String currency) {
        return currency.equalsIgnoreCase(cc);
    }
}
