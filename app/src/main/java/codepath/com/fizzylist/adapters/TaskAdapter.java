package codepath.com.fizzylist.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import codepath.com.fizzylist.R;
import codepath.com.fizzylist.activities.DetailTaskActivity;
import codepath.com.fizzylist.models.MyDatabase;
import codepath.com.fizzylist.models.Task;

/**
 * Created by FARZAD on 2/19/17.
 */

public class TaskAdapter extends ArrayAdapter<Task>{

    View fl_task_row;
    CheckBox chk_status;
    TextView tv_date;


    public TaskAdapter(Context context, ArrayList<Task> objects) {
        super(context, 0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         final Task task = getItem(position);
         long id = task.id;


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item_layout, parent,false);
        }
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        chk_status = (CheckBox) convertView.findViewById(R.id.chk_status);
         fl_task_row =convertView.findViewById(R.id.fl_task_row);

        tv_title.setText(task.Title.toString());

        chk_status.setTag(id);
        chk_status.setChecked(task.Status);
        assignPriorityColor(task.Priority);
        duDateAssign(task.DueDate);

        chk_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.Status=isChecked;
                MyDatabase database=new MyDatabase();
                database.editTask(task);
                notifyDataSetChanged();
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calldetailTaskForm(task);
                return true;

            }
        });
        notifyDataSetChanged();
        return convertView;
    }

    private void assignPriorityColor(int Priority){

        if (Priority == Task.HIGH_PRIORITY) {
            fl_task_row.setBackgroundColor(getContext().getResources().getColor(R.color.colorhighpriority));


        } else if (Priority == Task.NORMAL_PRIORITY) {

            fl_task_row.setBackgroundColor(getContext().getResources().getColor(R.color.colormediumpriority));


        } else if (Priority == Task.LOW_PRIORITY) {

            fl_task_row.setBackgroundColor(getContext().getResources().getColor(R.color.colorlowpriority));

        }
    }

    private void duDateAssign(String taskDueDate){
         Calendar todaycalnder = Calendar.getInstance();

        SimpleDateFormat mdformat = new SimpleDateFormat(Task.DATE_FORMAT);
        String today =mdformat.format(todaycalnder.getTime());
        if (taskDueDate.equalsIgnoreCase(today)) {
            tv_date.setText("TODAY");
        }else {
            Calendar duedate = Calendar.getInstance();
            duedate.set(Integer.valueOf(String.copyValueOf(taskDueDate.toCharArray(), 0, 4))
                    , Integer.valueOf(String.copyValueOf(taskDueDate.toCharArray(), 5, 2)) - 1
                    , Integer.valueOf(String.copyValueOf(taskDueDate.toCharArray(), 8, 2)));


            tv_date.setText(String.valueOf(daysBetween(todaycalnder.getTimeInMillis()
                    ,duedate.getTimeInMillis())) + " +Day");
        }



    }
    private int daysBetween(long t1, long t2) {
        return (int) ((t2 - t1) / (1000 * 60 * 60 * 24));
    }

    private void calldetailTaskForm(Task task){
        Intent detailTaskFormIntent = new Intent(getContext(),DetailTaskActivity.class);
        Bundle viewTaskDetailBundle = new Bundle();
        viewTaskDetailBundle.putSerializable(Task.SE_KEY, task);
        detailTaskFormIntent.putExtras(viewTaskDetailBundle);
        getContext().startActivity(detailTaskFormIntent);
    }


}
