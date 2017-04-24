package mario.testimagesql.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariu on 10.01.2017.
 */

public class ListOfProducts extends ArrayList<Product> implements Serializable{

    String name;
    List<Product> productList;

    public ListOfProducts(){

    }

    public ListOfProducts(String name) {
        this.name = name;
    }

    public ListOfProducts(String name, List<Product> productList) {
        this.name = name;
        this.productList = productList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}


