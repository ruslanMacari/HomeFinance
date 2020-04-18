package homefinance.money.currency.dto;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;

public class CurrencyDtoTest {

  private CurrencyDto currencyDto;
  private Validator validator;

  @Before
  public void setUp() {
    this.currencyDto = new CurrencyDto();
    this.validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  public void validateName_givenAmountNotInRange_thenShouldHaveViolation() {
    this.currencyDto.setName("na");
    this.checkNotValid("check that has violation due to a value less then 3");
    this.currencyDto.setName("1234567890123456789012345678901234567890123456");
    this.checkNotValid("check that has violation due to a value more then 45");
  }

  private void checkNotValid(String message) {
    Set<ConstraintViolation<CurrencyDto>> violations = this.getConstraintViolations();
    BDDAssertions.then(violations.isEmpty())
        .as(message)
        .isFalse();
  }

  private Set<ConstraintViolation<CurrencyDto>> getConstraintViolations() {
    return this.validator.validate(this.currencyDto);
  }

  @Test
  public void validateName_givenAmountInRange_thenShouldHaveNoViolation() {
    this.currencyDto.setName("name test");
    String message = "check that has no violation due to a value is between 3 and 45";
    this.checkValid(message);
    this.currencyDto.setName("name");
    this.checkValid(message);
    this.currencyDto.setName("123456789012345678901234567890123456789012345");
    this.checkValid(message);
  }

  private void checkValid(String message) {
    Set<ConstraintViolation<CurrencyDto>> violations = this.getConstraintViolations();
    BDDAssertions.then(violations.isEmpty())
        .as(message)
        .isTrue();
  }

  @Test
  public void validateCode_givenCodeNotInRange_thenShouldHaveViolation() {
    this.currencyDto.setCode("co");
    this.checkNotValid("check that has violation due to a value less then 3");
    this.currencyDto.setCode("123456");
    this.checkNotValid("check that has violation due to a value more then 5");
  }

  @Test
  public void validateCode_givenCodeIsInRange_thenShouldHaveNoViolation() {
    this.currencyDto.setCode("123");
    String message = "check that has no violation due to a value is between 3 and 5";
    this.checkValid(message);
    this.currencyDto.setCode("12345");
    this.checkValid(message);
  }

  @Test
  public void validateCharCode_givenCharCodeNotInRange_thenShouldHaveViolation() {
    this.currencyDto.setCharCode("co");
    this.checkNotValid("check that has violation due to a value less then 3");
    this.currencyDto.setCharCode("123456");
    this.checkNotValid("check that has violation due to a value more then 5");
  }

  @Test
  public void validateCharCode_givenCharCodeIsInRange_thenShouldHaveNoViolation() {
    this.currencyDto.setCharCode("123");
    String message = "check that has no violation due to a value is between 3 and 5";
    this.checkValid(message);
    this.currencyDto.setCharCode("12345");
    this.checkValid(message);
  }
}