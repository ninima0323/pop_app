package com.example.kimnahyeon.pop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kimnahyeon.pop.R;
import com.example.kimnahyeon.pop.TaskItemTouchHelperCallback;
import com.example.kimnahyeon.pop.data.TaskData;
import com.example.kimnahyeon.pop.for_sql.DBManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kimnahyeon on 2017. 7. 23..
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements TaskItemTouchHelperCallback.OnItemMoveListener {

    public interface OnStartDragListener{
        void onStartDrag(TaskViewHolder holder);
    }

    private ArrayList<TaskData> taskList = new ArrayList<>();
    private Context context;
    private DBManager dbManager;
    private final OnStartDragListener mStartDragListener;

    public TaskAdapter(Context context, ArrayList<TaskData> taskList,  OnStartDragListener startDragListener) {
        this.taskList = taskList;
        this.context = context;
        this.dbManager = new DBManager(context);
        mStartDragListener=startDragListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        TaskViewHolder viewHolder = new TaskViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        final TaskData task = taskList.get(position);

        if (task != null) {

            holder.content.setText(task.getContent());

            holder.content.setPaintFlags(holder.content.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            if(task.getDone()){
                holder.content.setPaintFlags(holder.content.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else{
                holder.content.setPaintFlags(holder.content.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(task.getDone()){
                        Log.e("asd", ""+position+"false");
                        task.setDone(false);
                        dbManager.updateTask(task);
                    }
                    else{
                        Log.e("asd", ""+position+"true");
                        task.setDone(true);
                        dbManager.updateTask(task);
                    }

                    notifyDataSetChanged();

                }
            });

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, DetailActivity.class);
//                    intent.putExtra("tid", task.getTid());
//                    context.startActivity(intent);
//                }
//            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                    dialog.setMessage("삭제하시겠습니까?");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbManager.deleteTask(task.getTid());

                            taskList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());

                            dialog.dismiss();
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    return false;
                }
            });


            holder.moveBtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(MotionEventCompat.getActionMasked(motionEvent)==MotionEvent.ACTION_DOWN){
                        mStartDragListener.onStartDrag(holder);
                    }
                    return false;
                }
            });


        }
    }


    public void setTaskList(ArrayList<TaskData> taskList){
        this.taskList=taskList;
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(taskList,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        ImageButton moveBtn;

        public TaskViewHolder(View itemView) {
            super(itemView);

            content = (TextView)itemView.findViewById(R.id.task_content);
            moveBtn = (ImageButton)itemView.findViewById(R.id.move_btn);

        }
    }
}
