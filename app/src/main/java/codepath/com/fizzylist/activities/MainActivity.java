package codepath.com.fizzylist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import codepath.com.fizzylist.R;
import codepath.com.fizzylist.adapters.TaskAdapter;
import codepath.com.fizzylist.models.MyDatabase;
import codepath.com.fizzylist.models.Task;

public class MainActivity extends Activity {

    //GENERAL VARIABLE
    MyDatabase database;
    TaskAdapter taskAdapter;
    public static final int NEW_TASK_REQUEST_CODE = 1;
    public static final Boolean TODAY_TASKS_FILTER=true;
    public static final Boolean ALL_TASKS_FILTER=false;
    ListView lv_listtasks;
    Context context;
    ArrayList<Task> arrayOFTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TASK_REQUEST_CODE) {
            populateTasksToListView(ALL_TASKS_FILTER);
            taskAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_newtask:
                callNewTaskActivity();
                return true;
            case R.id.actionbar_todayfilter:
                populateTasksToListView(TODAY_TASKS_FILTER);
                return true;
            case R.id.actionbar_allfilter:
                populateTasksToListView(ALL_TASKS_FILTER);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateTasksToListView(ALL_TASKS_FILTER);
    }
    

    //CUSTOM Method
    public void populateTasksToListView(Boolean today) {
        database = new MyDatabase();
        arrayOFTasks = new ArrayList<Task>();
        arrayOFTasks.clear();
        taskAdapter = new TaskAdapter(this, arrayOFTasks);
        if (today){
            arrayOFTasks.addAll(database.getAllTasksToday());
        }else
        {
            arrayOFTasks.addAll(database.getAllTasks());
        }

        lv_listtasks = (ListView) findViewById(R.id.lv_tasks);
        lv_listtasks.setAdapter(taskAdapter);
        taskAdapter.notifyDataSetChanged();
    }

    public void callNewTaskActivity() {
        Intent newTaskIntent = new Intent(this, NewTaskActivity.class);
        startActivityForResult(newTaskIntent, NEW_TASK_REQUEST_CODE);
        taskAdapter.notifyDataSetChanged();
    }






}