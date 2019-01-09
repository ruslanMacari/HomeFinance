package ruslan.macari.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ruslan.macari.util.PathSelector;
import ruslan.macari.web.exceptions.PageNotFoundException;

@Component
public abstract class CommonController<T> {
    
    protected PathSelector pathSelector;

    @Autowired
    public void setPathSelector(PathSelector pathSelector) {
        this.pathSelector = pathSelector;
    }
    
    public void test(T t) {
        if(t == null) {
            throw new PageNotFoundException();
        }
    }
}
