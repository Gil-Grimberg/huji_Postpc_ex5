package exercise.android.reemh.todo_items;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class TodoItem implements Serializable {
  // TODO: edit this class as you want
    final int DONE = 1;
    final int INPROGRESS=2;
    static int timeStamp = 1;
    int status;
    String description;
    int myTimeStamp;

    public TodoItem(){
        status = INPROGRESS;
        description = "New task";
        myTimeStamp = timeStamp;
        timeStamp++;
    }

    public TodoItem(int stat,String text)
    {
        status = stat;
        description = text;
        myTimeStamp = timeStamp;
        timeStamp++;
    }

    public void setStatus(int stat)
    {
        status = stat;
    }
    public boolean isDone(){
        return (status==DONE);
    }

    public void setDescription(String text)
    {
        description = text;
    }

    public int getTimeStamp(){ return myTimeStamp;}

}
