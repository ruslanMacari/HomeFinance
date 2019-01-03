package ruslan.macari.domain;

import java.util.Map;

public abstract class ConstraintEntity {
    
    protected Map<String, String> constraintsMap;

    public Map<String, String> getConstraintsMap() {
        return constraintsMap;
    }
}
