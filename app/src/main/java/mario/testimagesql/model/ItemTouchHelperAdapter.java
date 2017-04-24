package mario.testimagesql.model;

import android.support.v7.widget.RecyclerView;

/**
 * Created by mariu on 22.01.2017.
 */

// Nasłuchuję "move" i "swipe" events

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);

}
