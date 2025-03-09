package homefinance.money.currency.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CurrencyDto {
  private int id;
  @Size(min = 3, max = 45, message = "{size.error}")
  private String name;
  @Size(min = 3, max = 5, message = "{size.error}")
  private String code;
  @Size(min = 3, max = 5, message = "{size.error}")
  private String charCode;
}
