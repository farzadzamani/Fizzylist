package codepath.com.fizzylist.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import codepath.com.fizzylist.R;
import codepath.com.fizzylist.models.MyDatabase;
import codepath.com.fizzylist.models.Task;

/**
 * Created by FARZAD on 2/20/17.
 */

public class NewTaskActivity extends Activity {
    private Task task=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_task_menu, menu);
        menu.add("Create").setIcon(R.mipmap.save_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                addNewTask();
               setResult(MainActivity.NEW_TASK_REQUEST_CODE);
                finish();
                return true;
            }
        })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("Cancel").setIcon(R.mipmap.close_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setResult(MainActivity.NEW_TASK_REQUEST_CODE);
               finish();
                return true;
            }
        })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);

    }

    public void taskItemDataLoading() {

        EditText et_title = (EditText) findViewById(R.id.et_title);
        this.task.Title = et_title.getText().toString();

        DatePicker dt_newtask = (DatePicker) findViewById(R.id.dt_newtask);
        final java.util.Calendar c = Calendar.getInstance();

        c.set(dt_newtask.getYear(), dt_newtask.getMonth(), dt_newtask.getDayOfMonth());

        SimpleDateFormat mdformat = new SimpleDateFormat(Task.DATE_FORMAT);
        String duedate = mdformat.format(c.getTime());
        this.task.DueDate = duedate;

        String et_Detail = ((EditText) findViewById(R.id.et_Detail)).getText().toString();
        this.task.Detail = et_Detail;

        int sp_priority = ((Spinner) findViewById(R.id.sp_priority)).getSelectedItemPosition();
        this.task.Priority = sp_priority;
        String s = String.valueOf(sp_priority);
        this.task.Status = false;
    }

    private void addNewTask(){

            MyDatabase database = new MyDatabase();
            task = new Task();
            taskItemDataLoading();
            long newTaskID = database.getNewTaskId(); // Get New Id
            task.id = newTaskID;
            database.addNewTask(task);

    }



}
