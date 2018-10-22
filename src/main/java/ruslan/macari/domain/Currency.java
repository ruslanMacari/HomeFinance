package ruslan.macari.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "currency")
public class Currency {
    
    @Id
    @GeneratedValue
    int id;
    
    @Size(min = 3, message = "Size must be minim 3")
    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Currency() {
    }
    
    public Currency(String name) {
        this.name = name;
    }
    
    public Currency(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final Currency other = (Currency) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + id + ", name=" + name + '}';
    }
    
}
