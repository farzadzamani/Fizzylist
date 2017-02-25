package codepath.com.fizzylist.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import codepath.com.fizzylist.R;
import codepath.com.fizzylist.models.MyDatabase;
import codepath.com.fizzylist.models.Task;

/**
 * Created by FARZAD on 2/22/17.
 */

public class DetailTaskActivity extends Activity {

    private Task task;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_detail_item);

        Bundle TaskBundle = this.getIntent().getExtras();
        try {
            this.task = (Task) TaskBundle.getSerializable(Task.SE_KEY);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        loadItemDetailToForm(task);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_task_menu, menu);
        menu.add("Edit").setIcon(R.mipmap.edit_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        callEditTaskForm(task);
                        finish();
                        return true;
                    }
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("Delete").setIcon(R.mipmap.delete_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteTaskItem(task);
                        return true;
                    }
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

   private void deleteTaskItem(final Task task){
       DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               switch (which){
                   case DialogInterface.BUTTON_POSITIVE:
                        MyDatabase database=new MyDatabase();
                       database.deleteTask(task.id);
                       finish();
                       break;

                   case DialogInterface.BUTTON_NEGATIVE:
                       finish();
                       break;
               }
           }
       };

       AlertDialog.Builder builder = new AlertDialog.Builder(context);
       builder.setMessage("Are you sure to want to delete this task?").setPositiveButton("Yes", dialogClickListener)
               .setNegativeButton("No", dialogClickListener).show();

   }

    public void loadItemDetailToForm(Task task){

            TextView tv_title = (TextView) findViewById(R.id.tv_title_dt);
            TextView tv_detail = (TextView) findViewById(R.id.tv_Detail_dt);
            TextView tv_duedate_dt = (TextView) findViewById(R.id.tv_duedate_dt);
            Spinner sp_priority = (Spinner) findViewById(R.id.tv_priority_dt);

            tv_title.setText(task.Title);
            tv_detail.setText(task.Detail);
            tv_duedate_dt.setText(task.DueDate);

            sp_priority.setSelection(task.Priority);

        }



    public void callEditTaskForm(Task existingTask){
        Intent EditTaskFormIntent = new Intent(this, EditTaskActivity.class);
        Bundle EditTaskBundle = new Bundle();
        EditTaskBundle.putSerializable(Task.SE_KEY, existingTask);
        EditTaskFormIntent.putExtras(EditTaskBundle);
        startActivity(EditTaskFormIntent);
    }

    public void callDeleteTaskForm(Task existingTask){
        Intent EditTaskFormIntent = new Intent(this, EditTaskActivity.class);
        Bundle EditTaskBundle = new Bundle();
        EditTaskBundle.putSerializable(Task.SE_KEY, existingTask);
        EditTaskFormIntent.putExtras(EditTaskBundle);
        startActivity(EditTaskFormIntent);
    }

}
