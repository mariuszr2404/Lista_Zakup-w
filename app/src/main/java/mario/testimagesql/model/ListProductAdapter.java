package mario.testimagesql.model;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import mario.testimagesql.R;
import mario.testimagesql.controller.ListController;

/**
 * Created by mariu on 21.01.2017.
 */

public class ListProductAdapter extends RecyclerView.Adapter<ItemViewHolder> implements ItemTouchHelperAdapter{

    private ArrayList<Product> mProducts = new ArrayList<>();
    private int mActualSize = 0;

    // obiekt listy produktów
    private RecyclerView mRecyclerView;
    private Context mContext;
    private CheckBox box;
    private ItemViewHolder mViewHolder;

    private float mBeforeChangePrice;
    private float mAfterChangePrice;
    private float mSubstractCost;

    public ListProductAdapter(Context context, ArrayList<Product> productArrayList, RecyclerView RecyclerView){
        this.mContext = context;
        this.mProducts = productArrayList;
        this.mRecyclerView = RecyclerView;
    }

    // tworzymy obiekt layoutu elementu listy oraz na jego podstawie tworzymy ViewHolder
    //RecyclerView.ViewHolder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_row, parent, false);

        /*
        // dla pojedynczego elmentu na liście ustawiamy OnClickListenera
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // zwracamy indeks klikniętego elementu
                int positionToDelete = mRecyclerView.getChildAdapterPosition(view);
                mProducts.remove(positionToDelete);
                // usunięcie elementu w sposób anonimowy
                notifyItemRemoved(positionToDelete);
            }
        });
        */
        return new ItemViewHolder(view);
    }

    // uzupełniamy elementy listy odpowiednimi danymi
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        Product product = mProducts.get(position);
        mViewHolder = holder;
        holder.nameOfProducts.setText(product.getName());
        holder.isBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //zwracamy chcebox, któty kliknęlismy
                box = (CheckBox) view;

                if(box.isChecked()){
                    if(mContext instanceof ListController){
                        ((ListController)mContext).IncreseNrOfBuy();
                        float value = Float.valueOf(holder.price_single_product.getText().toString());
                        ((ListController)mContext).countPrice(value, 1);
                    }
                }
                else {
                    if(mContext instanceof ListController){
                        ((ListController)mContext).DecreaseNrOfBuy();
                        float value = Float.valueOf(holder.price_single_product.getText().toString());
                        ((ListController)mContext).countPrice(value, 2);
                    }
                }
                // zaktualizowania adaptera po zmianie
                notifyDataSetChanged();
            }
        });


        // W przypadku klikniecia i edycji zmienic koszt zakupów
        holder.price_single_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.price_single_product.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        mBeforeChangePrice = Float.valueOf(holder.price_single_product.getText().toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        mAfterChangePrice = Float.valueOf(holder.price_single_product.getText().toString());
                        if(mAfterChangePrice > mBeforeChangePrice){
                            mSubstractCost = mAfterChangePrice - mBeforeChangePrice;
                            ((ListController)mContext).countPrice(mSubstractCost, 1);
                        }
                        else {
                            mSubstractCost = mBeforeChangePrice - mAfterChangePrice;
                            ((ListController)mContext).countPrice(mSubstractCost, 2);
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for(int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mProducts, i, i + 1);
            }
        } else {
            for(int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mProducts, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mViewHolder = (ItemViewHolder)mRecyclerView.findViewHolderForAdapterPosition(position);
        mProducts.remove(position);
        mActualSize = getItemCount();
        notifyItemRemoved(position);
        //To do Nie działa za kazddym razem zwraca false spróbować dodać onClickListenera
        if(mViewHolder.isBuy.isChecked()){
//        if(productState){
            ((ListController)mContext).rearangeNrOfProdiuct(mActualSize, -1);
        }
        else{
            int nr = mViewHolder.getAdapterPosition();
             // Toast.makeText(mContext, "Czy zaznaczony " + mViewHolder.isBuy.isChecked(), Toast.LENGTH_SHORT).show();
            ((ListController)mContext).rearangeNrOfProdiuct(mActualSize, 0);
        }



        // Przy usunięciu produktu zmniejszamy całkowity koszt zakupów
        float value = Float.valueOf(mViewHolder.price_single_product.getText().toString());
        ((ListController)mContext).countPrice(value, 2);
    }
}
