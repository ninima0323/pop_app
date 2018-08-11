package com.example.kimnahyeon.pop;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.Callback.makeMovementFlags;

/**
 * Created by kimnahyeon on 2017. 11. 9..
 */

public class TaskItemTouchHelperCallback extends ItemTouchHelper.Callback {
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags= ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        int swipeFlags=ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    public interface OnItemMoveListener{
        void onItemMove(int fromPosition, int toPosition);
    }
    private final OnItemMoveListener mItemMoveListener;
    public TaskItemTouchHelperCallback(OnItemMoveListener listener){
        mItemMoveListener=listener;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mItemMoveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
