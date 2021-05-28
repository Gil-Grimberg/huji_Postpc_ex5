package exercise.android.reemh.todo_items;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;


public class TodoItem implements Serializable {

    final int DONE = 1;
    final int INPROGRESS = 2;
    static int timeStamp = 1; // todo: probably unnecessary
    int status;
    String description;
    int myTimeStamp;
    String id;

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

    public TodoItem(int stat, String text, int timeStamp, String id) {
        // todo 2: save the timeStamp as real time stamp!
        status = stat;
        description = text;
        myTimeStamp = timeStamp;
        this.id = id;
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
    public String getId(){return this.id;}

    public String serializable() {
        return String.valueOf(status) + "#" + description + "#" + String.valueOf(myTimeStamp) + "#" + id;
    }

    public static TodoItem string_to_Item(String item) {
        if (item == null) {
            return null;
        }
        try {
            String[] split = item.split("#");
            int status = Integer.parseInt(split[0]);
            String description = split[1];
            int myTimeStamp = Integer.parseInt(split[2]);
            String id = split[3];
            return new TodoItem(status, description, myTimeStamp,id);
        } catch (Exception e) {
            System.out.println("exception: input " + item + "output: " + e.getMessage());
            return null;
        }
    }

}

