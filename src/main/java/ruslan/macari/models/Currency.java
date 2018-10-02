package ruslan.macari.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Currency")
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
    
}
