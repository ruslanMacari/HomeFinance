package homefinance.money.currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
public class CurrencyRateModel {

  @EqualsAndHashCode.Include
  private String numCode;
  private String charCode;
  private String currency;
  private BigDecimal rate;
  private LocalDate date;

  @Builder
  public CurrencyRateModel(String numCode, String charCode, double rate, LocalDate date) {
    this.numCode = numCode;
    this.charCode = charCode;
    this.rate = BigDecimal.valueOf(rate);
    this.date = date;
  }

}
