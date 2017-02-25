package codepath.com.fizzylist.models;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static codepath.com.fizzylist.models.Task_Table.DueDate;
import static codepath.com.fizzylist.models.Task_Table.id;

/**
 * Created by FARZAD on 2/18/17.
 */
@Database(name=MyDatabase.NAME,version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME="fizzylistdb";
    public static final int VERSION=1;

    public MyDatabase() {
    }

    public ArrayList<Task> getAllTasks(String taskdate){

        ArrayList<Task> tasks =(ArrayList<Task>) SQLite.select().from(Task.class).where(DueDate.eq(taskdate)).orderBy(id,false).queryList();
        return tasks;
    }
    public ArrayList<Task> getAllTasksToday(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(Task.DATE_FORMAT);
        String today =mdformat.format(c.getTime());

        ArrayList<Task> tasks =(ArrayList<Task>) SQLite.select().from(Task.class).where(DueDate.eq(today)).orderBy(id,false).queryList();
        return tasks;
    }
    public ArrayList<Task> getAllTasks(){


        ArrayList<Task> tasks =(ArrayList<Task>) SQLite.select().from(Task.class).orderBy(id,false).queryList();
        return tasks;
    }

    public List<Task> getAllTasksList(){
        List<Task> tasks =(ArrayList<Task>) SQLite.select().from(Task.class).queryList();
        return tasks;
    }

    public List<Task> getTasksDates(){
        List<Task> dates= SQLite.select(DueDate).from(Task.class).groupBy(DueDate).queryList();
        return dates;
    }

    public Task getTaskDetail(long id){
        Task task=SQLite.select().from(Task.class).where(Task_Table.id.eq(id)).querySingle();
        return task;
    }

    public Boolean deleteTask(long id){
        SQLite.delete().from(Task.class).where(Task_Table.id.eq(id)).async().execute();
        return true;
    }

    public long getNewTaskId(){
        long lasttaskid=0;
        if (SQLite.select().from(Task.class).count()>0) {
             lasttaskid = SQLite.select(Task_Table.id).from(Task.class).orderBy(Task_Table.id, false).offset(0).querySingle().id;
        }
        return lasttaskid +1;
    }
    public final void addNewTask(Task task){
        task.save();
    }

    public final void editTask(Task task){
        task.save();
    }






}

