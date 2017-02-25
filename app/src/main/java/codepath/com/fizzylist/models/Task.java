package codepath.com.fizzylist.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by FARZAD on 2/18/17.
 */
@Table(database = MyDatabase.class)
public class Task extends BaseModel implements Serializable{

    public final static int HIGH_PRIORITY=2;
    public final static int NORMAL_PRIORITY=1;
    public final static int LOW_PRIORITY=0;
    public final static String SE_KEY="farzad";
    public  final static String DATE_FORMAT="yyyy/MM/dd";


    public Task(long id, String title, String detail, int priority, boolean status, String dueDate) {
        this.id = id;
        Title = title;
        Detail = detail;
        Priority = priority;
        Status = status;
        DueDate = dueDate;
    }

    public Task() {

    }

    @Column
    @PrimaryKey
    public long id;

    @Column
    public String Title;

    @Column
    public String Detail;

    @Column
    public int Priority;

    @Column
    public boolean Status;

    @Column
    public String DueDate;


    }

