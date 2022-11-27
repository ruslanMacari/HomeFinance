package homefinance.money.currency.entity;

import homefinance.common.entity.ConstraintEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Generated;

@Entity
@Table(name = "currencies", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name", name = Currency.UNIQUE_CONSTRAINT_NAME),
    @UniqueConstraint(columnNames = "code", name = Currency.UNIQUE_CONSTRAINT_CODE)})
public class Currency extends ConstraintEntity implements Serializable {

  public static final String UNIQUE_CONSTRAINT_NAME = "duplicated_description";
  public static final String UNIQUE_CONSTRAINT_CODE = "duplicated_code";
  private int id;
  private String name;
  private String code;
  private String charCode;

  {
    this.constraintsMap = new HashMap<>();
    this.constraintsMap.put("name", UNIQUE_CONSTRAINT_NAME);
    this.constraintsMap.put("code", UNIQUE_CONSTRAINT_CODE);
  }

  public Currency() {
  }

  public Currency(String name, String code) {
    this.name = name;
    this.code = code;
  }
  @Builder
  public Currency(String name, String code, String charCode) {
    this.name = name;
    this.code = code;
    this.charCode = charCode;
  }

  @OneToMany(mappedBy="cart")
  private Set<CurrencyRate> rates;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "currency_id")
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Column(name = "name", nullable = false, length = 45)
  @Size(min = 3, max = 45, message = "{size.error}")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "code", nullable = false, length = 5)
  @Size(min = 3, max = 5, message = "{size.error}")
  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Column(name = "char_code", nullable = false, length = 5)
  @Size(min = 3, max = 5, message = "{size.error}")
  public String getCharCode() {
    return this.charCode;
  }

  public void setCharCode(String charCode) {
    this.charCode = charCode;
  }

  @Override
  @Generated
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + this.id;
    hash = 17 * hash + Objects.hashCode(this.name);
    hash = 17 * hash + Objects.hashCode(this.code);
    return hash;
  }

  @Override
  @Generated
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Currency other = (Currency) obj;
    if (this.id != other.id) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return Objects.equals(this.code, other.code);
  }

  @Override
  public String toString() {
    return "Currency{" +
        "id=" + this.id +
        ", name='" + this.name + '\'' +
        ", code='" + this.code + '\'' +
        ", charCode='" + this.charCode + '\'' +
        '}';
  }
}
