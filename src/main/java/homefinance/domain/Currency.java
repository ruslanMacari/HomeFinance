// in working
package homefinance.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Generated;

@Entity
@Table(name = "currencies", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = Currency.UNIQUE_CONSTRAINT_NAME),
                                                 @UniqueConstraint(columnNames = "code", name = Currency.UNIQUE_CONSTRAINT_CODE)})
public class Currency extends ConstraintEntity implements Serializable {
    
    private int id;
    private String name;
    private String code;
    
    public static final String UNIQUE_CONSTRAINT_NAME = "duplicated_description";
    public static final String UNIQUE_CONSTRAINT_CODE = "duplicated_code";
    
    {
        constraintsMap = new HashMap<>();
        constraintsMap.put("name", UNIQUE_CONSTRAINT_NAME);
        constraintsMap.put("code", UNIQUE_CONSTRAINT_CODE);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code", nullable = false, length = 5)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Currency() {
    }
    
    public Currency(String name, String code) {
        this.name = name;
        this.code = code;
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
        return "Currency{" + "id=" + id + ", name=" + name + '}';
    }

}