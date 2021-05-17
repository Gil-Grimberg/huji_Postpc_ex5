package exercise.android.reemh.todo_items;

import java.util.Comparator;

public class ToDoItemsCompartor implements Comparator<TodoItem> {
    final int DONE = 1;
    final int INPROGRESS=2;
    @Override
    public int compare(TodoItem item1,TodoItem item2) {
        if (item1.status == INPROGRESS) {
            if (item2.status==DONE) return 1;
            else {
                if(item1.getTimeStamp()<item2.getTimeStamp()) return 1;
            }
        }
        else {
            if (item2.status==INPROGRESS) return -1;
            else
                return 0;
        }
        return 0;
    }
}
