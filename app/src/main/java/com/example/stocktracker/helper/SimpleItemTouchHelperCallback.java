package com.example.stocktracker.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.stocktracker.adapter.CompanyRecyclerAdapter;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {


    //private final ItemTouchHelperAdapter mAdapter;
    private final CompanyRecyclerAdapter mAdapter;


//    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
//        mAdapter = adapter;
//    }
    public SimpleItemTouchHelperCallback(CompanyRecyclerAdapter adapter) {
        mAdapter = adapter;
    }

    //return true here to enable long press on the RecyclerView rows for drag and drop.
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    //This is used to enable or disable swipes. In this tutorial, we’ll disable this
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    //Here we pass the flags for the directions of drag and swipe. Since swipe is disable we pass 0 for it. example from journaldev
    @Override
    public int getMovementFlags( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder) {
        Log.d("onStartDrag", "getMovementFlags");
        // Set movement flags based on the layout manager
     //   if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    //| ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
//        } else {
//            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//            return makeMovementFlags(dragFlags, swipeFlags);
//        }
    }

    //Here we set the code for the drag and drop.
    //onSwipe – Here we implement the code for swiping. We’ll keep this empty in the current tutorial.
    @Override
    public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder source,  RecyclerView.ViewHolder target) {
//        if (source.getItemViewType() != target.getItemViewType()) {
//            return false;
//        }
        Log.d("onStartDrag", "onMove");
        // Notify the adapter of the move
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.d("onStartDrag", "onSwiped");
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    //Based on the current state of the RecyclerView and whether it’s pressed or swiped, this method gets triggered.
    // Here we can customize the RecyclerView row. For example, changing the background color.
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        Log.d("onStartDrag", "onSelectedChanged");
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    //This method gets triggered when the user interaction stops with the RecyclerView row.
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        Log.d("onStartDrag", "clearView");
        viewHolder.itemView.setAlpha(1.0f);

        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            // Tell the view holder it's time to restore the idle state
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }
}
