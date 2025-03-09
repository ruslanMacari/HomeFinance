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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currencies", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name", name = Currency.UNIQUE_CONSTRAINT_NAME),
    @UniqueConstraint(columnNames = "code", name = Currency.UNIQUE_CONSTRAINT_CODE)})
@NoArgsConstructor
public class Currency extends ConstraintEntity implements Serializable {

  public static final String UNIQUE_CONSTRAINT_NAME = "unique_currencies_by_name";
  public static final String UNIQUE_CONSTRAINT_CODE = "unique_currencies_by_code";
  @Setter
  private int id;
  @Setter
  private String name;
  @Setter
  private String code;
  @Setter
  private String charCode;

  {
    constraintsMap = new HashMap<>();
    constraintsMap.put("name", UNIQUE_CONSTRAINT_NAME);
    constraintsMap.put("code", UNIQUE_CONSTRAINT_CODE);
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
  @Column(name = "id")
  public int getId() {
    return id;
  }

  @Column(name = "name", nullable = false, length = 45)
  @Size(min = 3, max = 45, message = "{size.error}")
  public String getName() {
    return name;
  }

  @Column(name = "code", nullable = false, length = 5)
  @Size(min = 3, max = 5, message = "{size.error}")
  public String getCode() {
    return code;
  }

  @Column(name = "char_code", nullable = false, length = 5)
  @Size(min = 3, max = 5, message = "{size.error}")
  public String getCharCode() {
    return charCode;
  }

  @Override
  @Generated
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + id;
    hash = 17 * hash + Objects.hashCode(name);
    hash = 17 * hash + Objects.hashCode(code);
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Currency other = (Currency) obj;
    if (id != other.id) {
      return false;
    }
    if (!Objects.equals(name, other.name)) {
      return false;
    }
    return Objects.equals(code, other.code);
  }

  @Override
  public String toString() {
    return "Currency{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", code='" + code + '\'' +
        ", charCode='" + charCode + '\'' +
        '}';
  }
}
