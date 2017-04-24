package mario.testimagesql.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mario.testimagesql.R;
import mario.testimagesql.model.ItemViewHolder;
import mario.testimagesql.model.ListProductAdapter;
import mario.testimagesql.model.Product;
import mario.testimagesql.model.ProductSingleton;

/**
 * Created by mariu on 09.01.2017.
 */

public class AddProductActivity extends AppCompatActivity {

    private TextView productName;
    private ImageView productIcon;
    private ListView productListAdapt;
    private String name;
    private EditText newProduct;
    private ImageView addnewProdct;
    private TextView goToList;
    private boolean isDatabaseCrated;

    //adapter
    private List<Product> products;
    private String Listname;
    //private ListOfProducts list;
    public ArrayList<Product>  productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Operacje na bazie
        //---------------------------------------------------------------//

        // Sprawadzamy czy baza istnieje jeśli nie to stwórz ją
        File database = getApplicationContext().getDatabasePath("Sugar.db");

        if(!database.exists()){
            // Singleton nie zadziała gdyż po zamknięciu programu wartość zmiennych zostaje utracona
            ProductSingleton product = ProductSingleton.getInstance();
            product.createListProducts(AddProductActivity.this);

        }

        name = this.getIntent().getExtras().getString("name");
        List<Product> listfromDatabase = Product.listAll(Product.class);


        // inicjalizacja UI elementow
        // --------------------------------------------------------------//

        productListAdapt = (ListView) findViewById(R.id.ListViewProducts);
        productListAdapt.setAdapter(new ProductAdapter(this, listfromDatabase, name));

        productName = (TextView) findViewById(R.id.tv_nazwa);
        productIcon = (ImageView) findViewById(R.id.iv_icon);

        newProduct = (EditText) findViewById(R.id.et_nameProducts);
        addnewProdct = (ImageView) findViewById(R.id.iv_chcekmark);

        // Po kliknieciu w button checkmark produkt zostaje dodany do Listy
        // W przypadku pustego stringa nic nie zostanie dodane do listy.
        addnewProdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameProduct = newProduct.getText().toString();
                if(nameProduct.length() > 0) {
                    Product product = new Product(nameProduct);
                    // Jeżeli produkt nie zostanie znaleziony na liście to go dodaj w przeciwnym wypadku wyświetl komunikat
                    if(chceckIfExist(product, productList) == false){
                        productList.add(product);
                        Toast.makeText(AddProductActivity.this, getString(R.string.toast_dodadno) + nameProduct + getString(R.string.toast_do_listy), Toast.LENGTH_SHORT).show();
                        newProduct.setText("");
                    }
                    else {
                        Toast.makeText(AddProductActivity.this, R.string.toast_komunikat_dodania, Toast.LENGTH_SHORT).show();
                        newProduct.setText("");
                    }

                }
            }
        });

        goToList = (TextView) findViewById(R.id.tv_go_to_list);

        // Po kliknięciu w TextView przechodzimy do ListView, która wyświetli stworzoną przez nas liste
        // aby móć przekazac liste produktów musimy ją przekonwertowac do arraylist
        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddProductActivity.this, ListController.class);
                intent.putExtra("extra", productList);
                startActivity(intent);
            }
        });
    }

    // metoda sprawdza czy dany produkt znajduje się na liście
    public boolean chceckIfExist(Product p, ArrayList<Product> list){

        boolean value = false;

        for(int i=0; i<list.size(); i++){
            if(list.get(i).getName().equalsIgnoreCase(p.getName())){
                value = true;
            }
        }
        return value;
    }

    // Wewnętrza klasa adaptera, ktora współdzieli zasoby do listy produktów
    public class ProductAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        private Context context;

        public ProductAdapter(Context context, List<Product> items, String name) {
            super();
            products = items;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            Listname = name;
            //list = new ListOfProducts(name, productList);
        }

        class ViewHolderItem{
            TextView nameProduct;
            ImageView add;
        }

        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int i) {
            return products.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolderItem holder = new ViewHolderItem();
            View customView = inflater.inflate(R.layout.custom_row, viewGroup, false);

            final Product singleProduct = (Product) getItem(i);
            holder.nameProduct = (TextView) customView.findViewById(R.id.tv_nazwa);
            final ImageView productIcon = (ImageView) customView.findViewById(R.id.iv_icon);
            holder.add = (ImageView) customView.findViewById(R.id.iv_add_button);

            holder.add.setOnClickListener(new View.OnClickListener() {

                public void displayToast(){
                    Toast.makeText(context, context.getString(R.string.Dodano) + " " + singleProduct.getName() + " " + context.getString(R.string.lista), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick(View view) {
                    // Jeżeli produkt nie zostanie znaleziony na liście to go dodaj w przeciwnym wypadku wyświetl komunikat
                    if(productList.contains(singleProduct) == false){
                        productList.add(singleProduct);
                        displayToast();
                    }
                    else {
                        Toast.makeText(context, R.string.toast_komunikat_dodania, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            String text = singleProduct.getName();
            holder.nameProduct.setText(text);
            productIcon.setImageResource(singleProduct.getidIcon());

            return customView;
        }
    }
}
