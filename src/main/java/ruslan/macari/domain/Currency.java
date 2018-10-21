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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        if (this.id != other.id) {
            return false;
        }
        return !((this.name == null) ? (other.name != null) : !this.name.equals(other.name));
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + id + ", name=" + name + '}';
    }
    
}
