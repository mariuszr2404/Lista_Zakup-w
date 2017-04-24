package mario.testimagesql.model;

import android.content.Context;
import android.content.res.Resources;

import mario.testimagesql.R;

/**
 * Created by mariu on 21.01.2017.
 */

public class ProductSingleton {

    private static ProductSingleton instance = new ProductSingleton();
    private ProductSingleton() {}

    public static ProductSingleton getInstance() {
        if(instance == null){
            instance = new ProductSingleton();
        }
        return instance;
    }

    public void createListProducts(Context context){

        Product product1 = new Product(R.drawable.chleb, context.getString(R.string.chleb));
        Product product2 = new Product(R.drawable.ciastka, context.getString(R.string.ciastka));
        Product product3 = new Product(R.drawable.jablko, context.getString(R.string.jablko));
        Product product4 = new Product(R.drawable.jajka, context.getString(R.string.jajka));
        Product product5 = new Product(R.drawable.ketchup, context.getString(R.string.ketchup));
        Product product6 = new Product(R.drawable.kielbasa, context.getString(R.string.kielbasa));
        Product product7 = new Product(R.drawable.mleko, context.getString(R.string.mleko));
        Product product8 = new Product(R.drawable.ogorek, context.getString(R.string.ogorek));
        Product product9 = new Product(R.drawable.piwo, context.getString(R.string.piwo));
        Product product10 = new Product(R.drawable.pomidor, context.getString(R.string.pomidor));
        Product product11 = new Product(R.drawable.ryba, context.getString(R.string.ryba));
        Product product12 = new Product(R.drawable.ser, context.getString(R.string.ser));
        Product product13 = new Product(R.drawable.woda, context.getString(R.string.woda));

        product1.save();
        product2.save();
        product3.save();
        product4.save();
        product5.save();
        product6.save();
        product7.save();
        product8.save();
        product9.save();
        product10.save();
        product11.save();
        product12.save();
        product13.save();

    }
}