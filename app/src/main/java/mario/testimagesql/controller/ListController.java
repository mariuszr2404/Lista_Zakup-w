package mario.testimagesql.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import mario.testimagesql.R;
import mario.testimagesql.model.ListProductAdapter;
import mario.testimagesql.model.Product;
import mario.testimagesql.model.SimpleItemTouchHelperCallback;

/**
 * Created by mariu on 17.01.2017.
 */

public class ListController extends AppCompatActivity {

    private TextView nr_of_buy;
    private ItemTouchHelper mItemTouchHelper;
    private int counter = 0;
    private ArrayList<Product> objects;
    private int mTotalSize = 0;
    private float mTotalPrice = 0;
    private ListProductAdapter adapter = null;
    private TextView total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail);

        nr_of_buy = (TextView) findViewById(R.id.tv_nr_of_buy);
        total_price = (TextView) findViewById(R.id.tv_all_price);

        //---------------------------------------------------------------------------------//
        // RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //w celach optymalizacji
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ustawiamy animatora, który odpowiada za animację dodania/usunięcia elementów listy
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //---------------------------------------------------------------------------------//
        //Odebranie danych z poprzedniego activy, które posłużą jako źródłó danych
        Intent intent = getIntent();
        objects = (ArrayList<Product>) intent.getSerializableExtra("extra");
        mTotalSize = objects.size();

        adapter = new ListProductAdapter(ListController.this, objects, recyclerView);

        //Tworzymy adapter oraz łączymy go z RecyclerView
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Po wcisnieciu chcekboxa lista produktów zakupoionych zostanie zwiekszona o 1
    public void IncreseNrOfBuy(){
        counter++;
        nr_of_buy.setText(counter + " / " + mTotalSize);
    }

    // Po wcisnieciu chcekboxa lista produktów zostanie zmniejszona o 1
    public void DecreaseNrOfBuy(){
        counter--;
        nr_of_buy.setText(counter + " / " + mTotalSize);
    }

    // Po usunięciu produktu z listy jej rozmiar zostaje zaktualizowany
    public void rearangeNrOfProdiuct(int size, int option){
        mTotalSize = size;

        if(option == -1){
            counter--;
            nr_of_buy.setText( counter  + " / " + mTotalSize);
        }
        else {
            nr_of_buy.setText( counter + " / " + mTotalSize);
        }
    }

    // Metoda zlicza całkowity koszt zakupów
    public void countPrice(float price, int variaton){


        if (variaton == 1) {
            mTotalPrice += price;
        }
        else {
            mTotalPrice -= price;
//            total_price.setText(String.format("%.2f", (mTotalPrice -= price)));
        }
        total_price.setText(String.format("%.2f", mTotalPrice));
    }
}





