package homefinance.money.currency.dto;

public class CurrencyDto {

  public int id;
  public String name;
  public String code;
  public String charCode;

  public int getId() {
    return this.id;
  }

  public CurrencyDto setId(int id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public CurrencyDto setName(String name) {
    this.name = name;
    return this;
  }

  public String getCode() {
    return this.code;
  }

  public CurrencyDto setCode(String code) {
    this.code = code;
    return this;
  }

  public String getCharCode() {
    return this.charCode;
  }

  public CurrencyDto setCharCode(String charCode) {
    this.charCode = charCode;
    return this;
  }

  @Override
  public String toString() {
    return "CurrencyDto{" +
        "id=" + this.id +
        ", name='" + this.name + '\'' +
        ", code='" + this.code + '\'' +
        ", charCode='" + this.charCode + '\'' +
        '}';
  }
}
