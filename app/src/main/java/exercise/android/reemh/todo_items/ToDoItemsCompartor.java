package exercise.android.reemh.todo_items;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Comparator;

public class ToDoItemsCompartor implements Comparator<TodoItem> {
    final int DONE = 1;
    final int INPROGRESS=2;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int compare(TodoItem item1,TodoItem item2) {
        if (item1.status == INPROGRESS) {
            if (item2.status==DONE) return -1;
            else {
                if(item2.getlastModifiedTime().compareTo(item1.getlastModifiedTime()) > 0) return 1;
                else return -1;
            }
        }
        else {
            if (item2.status==INPROGRESS) return 1;
            else
            {
                if (item2.getlastModifiedTime().compareTo(item1.getlastModifiedTime()) > 0) return 1;
                else return -1;
            }
        }
    }





//    @Override
//    public int compare(TodoItem item1,TodoItem item2) {
//        if (item1.status == INPROGRESS) {
//            if (item2.status==DONE) return -1;
//            else {
//                if(item1.getTimeStamp()<item2.getTimeStamp()) return 1;
//                else return -1;
//            }
//        }
//        else {
//            if (item2.status==INPROGRESS) return 1;
//            else
//                return 0;
//        }
//    }
}
