package msuitd110;

import java.io.Serializable;

public class Student implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String id;
    String name;
    void setId(String id) {
        this.id = id;
    }
    String getId() {
        return id;
    }
    void setName(String name) {
        this.name = name;
    }
    String getName() {
        return name;
    }
    
    
}