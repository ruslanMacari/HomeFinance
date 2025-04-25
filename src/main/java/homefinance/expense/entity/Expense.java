package homefinance.expense.entity;

import homefinance.common.entity.ConstraintEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "expenses", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name", name = Expense.UNIQUE_CONSTRAINT_NAME)})
@NoArgsConstructor
@Accessors(chain = true)
public class Expense extends ConstraintEntity implements Serializable {

  public static final String UNIQUE_CONSTRAINT_NAME = "unique_expenses_by_name";
  
  @Setter
  private int id;
  
  @Setter
  private String name;

  {
    constraintsMap = new HashMap<>();
    constraintsMap.put("name", UNIQUE_CONSTRAINT_NAME);
  }

  public Expense(String name) {
    this.name = name;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public int getId() {
    return id;
  }

  @Column(name = "name", nullable = false, length = 45)
  @Size(min = 1, max = 45, message = "{size.error}")
  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 17 * hash + id;
    hash = 17 * hash + Objects.hashCode(name);
    return hash;
  }

  @Override
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
    final Expense other = (Expense) obj;
    if (id != other.id) {
      return false;
    }
    return Objects.equals(name, other.name);
  }

  @Override
  public String toString() {
    return "Expense{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
