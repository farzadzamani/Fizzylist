package codepath.com.fizzylist.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import codepath.com.fizzylist.R;
import codepath.com.fizzylist.models.MyDatabase;
import codepath.com.fizzylist.models.Task;

/**
 * Created by FARZAD on 2/22/17.
 */

public class EditTaskActivity extends Activity {
    private Task task;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
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
        menu.add("Save").setIcon(R.mipmap.save_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        taskItemDataLoading();
                        if (task.Title!=null&&task.Title!=""){
                            MyDatabase database=new MyDatabase();
                            database.editTask(task);
                            finish();
                            return true;
                        }else{

                        Toast.makeText(context, "Please Enter Task Name", Toast.LENGTH_SHORT).show();
                        return false;

                        }

                    }
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("Cancel").setIcon(R.mipmap.close_icon)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        finish();
                        return true;
                    }
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);

    }


    public void loadItemDetailToForm(Task task){

        TextView tv_title = (TextView) findViewById(R.id.et_title_ed);
        TextView tv_detail = (TextView) findViewById(R.id.dt_Detail_ed);
        DatePicker dp_duedate_dt = (DatePicker) findViewById(R.id.dt_duedate_ed);
        Spinner sp_priority = (Spinner) findViewById(R.id.sp_priority_ed);

        tv_title.setText(task.Title);
        tv_detail.setText(task.Detail);
        dp_duedate_dt.updateDate(Integer.valueOf(String.copyValueOf(task.DueDate.toCharArray(),0,4))
                ,Integer.valueOf(String.copyValueOf(task.DueDate.toCharArray(),5,2))-1
                ,Integer.valueOf(String.copyValueOf(task.DueDate.toCharArray(),8,2)));


        sp_priority.setSelection(task.Priority);
    }

    public void taskItemDataLoading(){


        EditText et_title = (EditText) findViewById(R.id.et_title_ed);
        this.task.Title=et_title.getText().toString();

        DatePicker dt_newtask = (DatePicker) findViewById(R.id.dt_duedate_ed);
        final java.util.Calendar c= Calendar.getInstance();

        c.set(dt_newtask.getYear(),dt_newtask.getMonth(),dt_newtask.getDayOfMonth());

        SimpleDateFormat mdformat = new SimpleDateFormat(Task.DATE_FORMAT);
        String duedate =mdformat.format(c.getTime());
        this.task.DueDate=duedate;

        String et_Detail = ((EditText)findViewById(R.id.dt_Detail_ed)).getText().toString();
        this.task.Detail=et_Detail;

        int sp_priority = ((Spinner)findViewById(R.id.sp_priority_ed)).getSelectedItemPosition();
        this.task.Priority=sp_priority;
        this.task.Status=task.Status;
    }
}
