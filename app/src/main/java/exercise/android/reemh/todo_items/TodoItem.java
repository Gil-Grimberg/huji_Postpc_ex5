package exercise.android.reemh.todo_items;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;


public class TodoItem implements Serializable {
    // TODO: edit this class as you want
    final int DONE = 1;
    final int INPROGRESS = 2;
    static int timeStamp = 1;
    int status;
    String description;
    int myTimeStamp;

    public TodoItem() {
        status = INPROGRESS;
        description = "New task";
        myTimeStamp = timeStamp;
        timeStamp++;
    }

    public TodoItem(int stat, String text) {
        status = stat;
        description = text;
        myTimeStamp = timeStamp;
        timeStamp++;
    }

    public TodoItem(int stat, String text, int timeStamp) {
        status = stat;
        description = text;
        myTimeStamp = timeStamp;
    }

    public void setStatus(int stat) {
        status = stat;
    }

    public boolean isDone() {
        return (status == DONE);
    }

    public void setDescription(String text) {
        description = text;
    }

    public int getTimeStamp() {
        return myTimeStamp;
    }

    public String serializable() {
        return String.valueOf(status) + "$" + description + "$" + String.valueOf(myTimeStamp);
    }

    public TodoItem string_to_Item(String item) {
        try {
            String[] split = item.split("$");
            int status = Integer.parseInt(split[0]);
            String description = split[1];
            int myTimeStamp = Integer.parseInt(split[2]);
            return new TodoItem(status, description, myTimeStamp);
        } catch (Exception e) {
            System.out.println("exception: input " + item + "output: "+ e.getMessage());
            return null;
        }
    }

}

