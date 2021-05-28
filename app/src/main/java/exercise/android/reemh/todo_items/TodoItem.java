package exercise.android.reemh.todo_items;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TodoItem implements Serializable {

    final int DONE = 1;
    final int INPROGRESS = 2;
    //    static int timeStamp = 1; // todo: probably unnecessary
    int status;
    String description;
    //    int myTimeStamp;
    LocalDateTime createdTime;
    LocalDateTime lastModified;
    String id;


    public TodoItem() {
        status = INPROGRESS;
        description = "New task";
//        createdTime = timeStamp;
//        timeStamp++;
    }

    public TodoItem(int stat, String text) {
        status = stat;
        description = text;
//        createdTime = timeStamp;
//        timeStamp++;
    }

    public TodoItem(int stat, String text, LocalDateTime createdTime, LocalDateTime lastModified, String id) {
        // todo 2: save the timeStamp as real time stamp!
        this.status = stat;
        this.description = text;
//        myTimeStamp = timeStamp;
        this.id = id;
        this.createdTime = createdTime;
        this.lastModified = lastModified;

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

    public String getDescription(){
        return this.description;
    }

    public LocalDateTime getlastModifiedTime() {
        return this.lastModified;
    }

    public LocalDateTime getCreatedTime() {
        return this.createdTime;
    }

    public void setLastModified(LocalDateTime time){
        this.lastModified = time;
    }
    public String getId() {
        return this.id;
    }

    public String serializable() {
        int day_created = this.createdTime.getDayOfMonth();
        int month_created = this.createdTime.getMonthValue();
        int year_created = this.createdTime.getYear();
        int hour_created = this.createdTime.getHour();
        int minute_created = this.createdTime.getMinute();
        int sec_created = this.createdTime.getSecond();

        int day_lastM = this.createdTime.getDayOfMonth();
        int month_lastM = this.lastModified.getMonthValue();
        int year_lastM = this.lastModified.getYear();
        int hour_lastM = this.lastModified.getHour();
        int minute_lastM = this.lastModified.getMinute();
        int sec_lastM = this.lastModified.getSecond();

        String createdTimeAsString = String.valueOf(day_created) + " " + String.valueOf(month_created) + " " + String.valueOf(year_created) + " " + String.valueOf(hour_created) + " " + String.valueOf(minute_created) + " " + String.valueOf(sec_created);
        String lastModifiedTimeAsString = String.valueOf(day_lastM) + " " + String.valueOf(month_lastM) + " " + String.valueOf(year_lastM) + " " + String.valueOf(hour_lastM) + " " + String.valueOf(minute_lastM) + " " + String.valueOf(sec_lastM);
        return String.valueOf(this.status) + "#" + this.description + "#" + createdTimeAsString + "#" + lastModifiedTimeAsString + "#" + this.id;
    }

    public static TodoItem string_to_Item(String item) {
        if (item == null) {
            return null;
        }
        try {
            String[] split = item.split("#");
            int status = Integer.parseInt(split[0]);
            String description = split[1];
//            int myTimeStamp = Integer.parseInt(split[2]);

            String[] createdTimeAsString = split[2].split(" ");

            int dayCreated = Integer.parseInt(createdTimeAsString[0]);
            int monthCreated = Integer.parseInt(createdTimeAsString[1]);
            int yearCreated = Integer.parseInt(createdTimeAsString[2]);
            int hourCreated = Integer.parseInt(createdTimeAsString[3]);
            int minuteCreated = Integer.parseInt(createdTimeAsString[4]);
            int secCreated = Integer.parseInt(createdTimeAsString[5]);

            String[] lastModifiedTimeAsString = split[3].split(" ");

            int dayLastM = Integer.parseInt(lastModifiedTimeAsString[0]);
            int monthLastM = Integer.parseInt(lastModifiedTimeAsString[1]);
            int yearLastM = Integer.parseInt(lastModifiedTimeAsString[2]);
            int hourLastM = Integer.parseInt(lastModifiedTimeAsString[3]);
            int minuteLastM = Integer.parseInt(lastModifiedTimeAsString[4]);
            int secLastM = Integer.parseInt(lastModifiedTimeAsString[5]);

            LocalDateTime created = LocalDateTime.of(yearCreated,monthCreated,dayCreated,hourCreated,minuteCreated,secCreated);
            LocalDateTime lastModified = LocalDateTime.of(yearLastM,monthLastM,dayLastM,hourLastM,minuteLastM,secLastM);

            String id = split[4];

            // todo: parse string of time back to LocalDataTime with the method LocalDateTime.of(...)
            return new TodoItem(status, description, created, lastModified, id);
        } catch (Exception e) {
            System.out.println("exception: input " + item + "output: " + e.getMessage());
            return null;
        }
    }

}

