package mario.testimagesql.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

/**
 * Created by mariu on 07.01.2017.
 */

// Implementacja interfejsu Serializable lub Paracelable
// jest niezbędna by móć przesłać liste z jednej aktywnosci do drugiej
public class Product extends SugarRecord implements Serializable{

    private String name;
    private int idIcon;

    public  Product(){
    }

    public Product(int idIcon, String name) {
        this.idIcon = idIcon;
        this.name = name;
    }

    public Product(String name){
        this.name = name;
    }

    public void setidIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getidIcon() {
        return idIcon;
    }

    public String getName() {
        return name;
    }

}
