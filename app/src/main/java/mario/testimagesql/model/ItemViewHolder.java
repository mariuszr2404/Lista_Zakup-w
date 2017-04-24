package mario.testimagesql.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import mario.testimagesql.R;

/**
 * Created by mariu on 22.01.2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nameOfProducts;
    public CheckBox isBuy;
    public CardView root;
    public EditText price_single_product;

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    public ItemViewHolder(View itemView) {
        super(itemView);

        root = (CardView) itemView.findViewById(R.id.card_view_product);
        nameOfProducts = (TextView) itemView.findViewById(R.id.tv_name_of_products);
        isBuy = (CheckBox) itemView.findViewById(R.id.checkbox_if_buy);
        price_single_product = (EditText) itemView.findViewById(R.id.et_price);
        root.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Log.i("Nr pozycji", " " + getAdapterPosition());
    }
}
